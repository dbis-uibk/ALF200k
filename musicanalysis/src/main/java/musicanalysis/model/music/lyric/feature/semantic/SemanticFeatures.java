package musicanalysis.model.music.lyric.feature.semantic;

import musicanalysis.model.data.*;
import musicanalysis.model.music.Config;
import musicanalysis.model.music.lyric.feature.FeatureLoader;
import musicanalysis.model.music.lyric.feature.PreparedLyric;

public class SemanticFeatures {

   private final PreparedLyric l;

   private AFINN afinn = null;
   private RegressiveImage regressiveImage = null;
   private DerivedRegressiveImage derivedRegressiveImage = null;
   private Opinion opinion = null;
   private SentiStrength sentiStrength = null;
   private VADERSentiment vaderSentiment = null;

   public SemanticFeatures(final PreparedLyric l) {

      this.l = l;
   }

   public static SemanticFeatures load(final FeatureLoader loader, final PreparedLyric l) {

      return load(loader, l, (new Config(true)).semanticConfig);
   }

   public static SemanticFeatures load(final FeatureLoader loader, final PreparedLyric l, final Config.SemanticConfig config) {

      final SemanticFeatures semanticFeatures = new SemanticFeatures(l);
      semanticFeatures.afinn = config.enableAFINNFeatures ? loader.load(AFINN.class, l.getId()) : null;
      semanticFeatures.opinion = config.enableOpinionFeatures ? loader.load(Opinion.class, l.getId()) : null;
      semanticFeatures.sentiStrength = config.enableSentiStrengthFeatures ? loader.load(SentiStrength.class, l.getId()) : null;
      semanticFeatures.vaderSentiment = config.enableVADERFeatures ? loader.load(VADERSentiment.class, l.getId()) : null;
      semanticFeatures.regressiveImage = null;
      semanticFeatures.derivedRegressiveImage = null;
      if (config.enableRegressiveImageFeatures) {
         semanticFeatures.regressiveImage = loader.load(RegressiveImage.class, l.getId());
         final DerivedRegressiveImageEvaluator derivedRegressiveImageEvaluator
               = new DerivedRegressiveImageEvaluator(semanticFeatures.regressiveImage);
         semanticFeatures.derivedRegressiveImage = derivedRegressiveImageEvaluator.compute();
      }
      return semanticFeatures;
   }

   public AFINN getAFINN() {
      return afinn;
   }

   public RegressiveImage getRegressiveImage() {

      return regressiveImage;
   }

   public DerivedRegressiveImage getDerivedRegressiveImage() {
      return derivedRegressiveImage;
   }

   public Opinion getOpinion() {
      return opinion;
   }

   public SentiStrength getSentiStrength() {
      return sentiStrength;
   }

   public VADERSentiment getVaderSentiment() {
      return vaderSentiment;
   }

   public void compute() {

      final AFINNEvaluator afinnEvaluator = new AFINNEvaluator(l);
      final OpinionEvaluator opinionEvaluator = new OpinionEvaluator(l);
      final RegressiveImageEvaluator regressiveImageEvaluator = new RegressiveImageEvaluator(l);
      final SentiStrengthEvaluator sentiStrengthEvaluator = new SentiStrengthEvaluator(l);
      final VADERSentimentEvaluator vaderSentimentEvaluator = new VADERSentimentEvaluator(l);

      afinn = afinnEvaluator.evaluate();
      opinion = opinionEvaluator.evaluate();
      regressiveImage = regressiveImageEvaluator.evaluate();
      sentiStrength = sentiStrengthEvaluator.evaluate();
      vaderSentiment = vaderSentimentEvaluator.evaluate();

      final DerivedRegressiveImageEvaluator derivedRegressiveImageEvaluator
            = new DerivedRegressiveImageEvaluator(regressiveImage);
      derivedRegressiveImage = derivedRegressiveImageEvaluator.compute();
   }
}
