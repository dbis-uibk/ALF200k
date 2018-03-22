package musicanalysis.model.music.lyric.feature;

import musicanalysis.model.Task;
import musicanalysis.model.data.Audio;
import musicanalysis.model.data.Lyric;
import musicanalysis.model.data.LyricFeature;
import musicanalysis.model.music.lyric.feature.PreparedLyric;
import musicanalysis.model.music.lyric.feature.lexical.LexicalFeatures;
import musicanalysis.model.music.lyric.feature.linguistic.LinguisticFeatures;
import musicanalysis.model.music.lyric.feature.linguistic.UrbanDictionary;
import musicanalysis.model.music.lyric.feature.linguistic.Wiktionary;
import musicanalysis.model.music.lyric.feature.semantic.SemanticFeatures;
import musicanalysis.model.music.lyric.feature.syntactic.SyntacticFeatures;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class FeatureComputer extends Task {

   private final Queue<CompletableFuture<Void>> futures;
   private volatile AtomicLong processedLyrics;
   private volatile AtomicLong totalLyrics;
   private volatile Config config;

   @Autowired
   public FeatureComputer(MongoTemplate mongoTemplate) {

      this.initialize(mongoTemplate);
      this.processedLyrics = new AtomicLong(0);
      this.totalLyrics = new AtomicLong(0);
      this.futures = new LinkedBlockingQueue<>();
   }

   @PostConstruct
   private void initialize() {

      this.config = new Config(true);

      UrbanDictionary.getInstance().initialize(getDatabaseConnection());
      Wiktionary.getInstance().initialize(getDatabaseConnection());
   }

   public synchronized void run(final Config config) {

      if (!isRunning()) {
         this.config = config;
      }
      this.run();
   }

   @Override
   protected void execute() {

      compute();
   }

   @Override
   protected String getTaskName() {

      return "compute-features";
   }

   @Override
   protected float calculateProgress() {

      float progress = 0.0f;

      if (totalLyrics.get() > 0) {
         progress = processedLyrics.get() * 100.0f / totalLyrics.get();
      }

      return progress;
   }

   private void compute() {

      final Map<ObjectId, Audio> audios = getIndexedAudios();
      final List<Lyric> lyrics = getLyrics().stream()
            .filter(l -> audios.containsKey(l.getTrackId())).collect(Collectors.toList());
      final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

      processedLyrics.set(0L);
      totalLyrics.set(lyrics.size());
      futures.clear();

      final Iterator<Lyric> iterator = lyrics.iterator();
      while (iterator.hasNext() && !isCancelled()) {
         final Lyric l = iterator.next();
         final float duration = audios.get(l.getTrackId()).getDuration();
         final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> this.compute(l, duration), executorService).thenRun(() -> {
            final long processed = processedLyrics.incrementAndGet();
            logInfo(String.format("%.2f%% - %d/%d features computed",
                  ((processed * 1.0) / totalLyrics.get()) * 100.0,
                  processed,
                  totalLyrics.get()));
         }).exceptionally(e -> {
            if (!(e.getCause() instanceof CancellationException)) {
               processedLyrics.incrementAndGet();
               e.printStackTrace();
               logError(e.toString());
            }
            return null;
         });
         futures.add(future);
      }

      CompletableFuture<Void> future = null;
      while ((future = futures.poll()) != null) {
         try {
            future.join();
         } catch (Exception ex) {
         }
      }

      executorService.shutdownNow();

      processedLyrics.set(0L);
   }

   private void compute(final Lyric l, final float duration) {

      final PreparedLyric preparedLyric = new PreparedLyric(l);
      if (config.isComputeLexicalFeatures()) computeLexicalFeatures(preparedLyric, duration);
      if (config.isComputeLinguisticFeatures()) computeLinguisticFeatures(preparedLyric);
      if (config.isComputeSemanticFeatures()) computeSemanticFeatures(preparedLyric);
      if (config.isComputeSyntacticFeatures()) computeSyntacticFeatures(preparedLyric);
   }

   private void computeSyntacticFeatures(final PreparedLyric preparedLyric) {

      final SyntacticFeatures features = new SyntacticFeatures(preparedLyric);
      features.compute();
      upsert(features.getPastTense());
      upsert(features.getPronouns());
      upsert(features.getSuperChunkTags());
      upsert(features.getSuperPOSTags());
   }

   private void computeSemanticFeatures(final PreparedLyric preparedLyric) {

      final SemanticFeatures features = new SemanticFeatures(preparedLyric);
      features.compute();
      upsert(features.getAFINN());
      upsert(features.getOpinion());
      upsert(features.getRegressiveImage());
      upsert(features.getSentiStrength());
      upsert(features.getVaderSentiment());
   }

   private void computeLinguisticFeatures(final PreparedLyric preparedLyric) {

      final LinguisticFeatures features = new LinguisticFeatures(preparedLyric);
      features.compute();
      upsert(features.getEchoism());
      upsert(features.getRepetitiveStructure());
      upsert(features.getSlangUse());
      upsert(features.getRhyme());
   }

   private void computeLexicalFeatures(final PreparedLyric preparedLyric, final float duration) {

      final LexicalFeatures features = new LexicalFeatures(preparedLyric);
      features.compute(duration);
      upsert(features.getBagOfWords());
      upsert(features.getTextStyle());
   }

   private <T extends LyricFeature> void upsert(T feature) {

      final Query query = new Query();
      query.addCriteria(Criteria.where(LyricFeature.Fields.LYRIC_ID).is(feature.getLyricId()));
      getDatabaseConnection().upsert(query, feature.toUpdate(), feature.getClass());
   }

   @Override
   public void onCancellation() {

      futures.forEach(f -> {
         try {
            if (!f.isDone()) {
               f.cancel(true);
            }
         } catch (Exception ex) {
         }
      });
   }

   private List<Lyric> getLyrics() {

      return getDatabaseConnection().findAll(Lyric.class);
   }

   private Map<ObjectId, Audio> getIndexedAudios() {

      final HashMap<ObjectId, Audio> audios = new HashMap<>();
      getDatabaseConnection().findAll(Audio.class).forEach(a -> audios.put(a.getTrackId(), a));
      return audios;
   }

   public class Config {

      private boolean computeLexicalFeatures = false;
      private boolean computeLinguisticFeatures = false;
      private boolean computeSyntacticFeatures = false;
      private boolean computeSemanticFeatures = false;

      public Config() {

      }

      public Config(boolean defaultValue) {

         this.computeLexicalFeatures = defaultValue;
         this.computeLinguisticFeatures = defaultValue;
         this.computeSemanticFeatures = defaultValue;
         this.computeSyntacticFeatures = defaultValue;
      }

      public boolean isComputeLexicalFeatures() {
         return computeLexicalFeatures;
      }

      public void setComputeLexicalFeatures(boolean computeLexicalFeatures) {
         this.computeLexicalFeatures = computeLexicalFeatures;
      }

      public boolean isComputeLinguisticFeatures() {
         return computeLinguisticFeatures;
      }

      public void setComputeLinguisticFeatures(boolean computeLinguisticFeatures) {
         this.computeLinguisticFeatures = computeLinguisticFeatures;
      }

      public boolean isComputeSyntacticFeatures() {
         return computeSyntacticFeatures;
      }

      public void setComputeSyntacticFeatures(boolean computeSyntacticFeatures) {
         this.computeSyntacticFeatures = computeSyntacticFeatures;
      }

      public boolean isComputeSemanticFeatures() {
         return computeSemanticFeatures;
      }

      public void setComputeSemanticFeatures(boolean computeSemanticFeatures) {
         this.computeSemanticFeatures = computeSemanticFeatures;
      }
   }
}
