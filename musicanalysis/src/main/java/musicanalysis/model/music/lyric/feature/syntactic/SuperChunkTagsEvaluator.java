package musicanalysis.model.music.lyric.feature.syntactic;

import edu.stanford.nlp.ling.CoreLabel;
import musicanalysis.model.data.ChunkTag;
import musicanalysis.model.data.SuperChunkTags;
import musicanalysis.model.music.lyric.feature.PreparedLyric;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * http://opennlp.sourceforge.net/models-1.5/
 * en-chunker.bin: chunker trained on conll2000 shared task data
 */
public class SuperChunkTagsEvaluator {

   private static final List<String> groupNounPhrases = Arrays.asList("B-NP");
   private static final List<String> groupVerbPhrases = Arrays.asList("B-VP");
   private static final List<String> groupPrepositionalPhrases = Arrays.asList("B-PP");
   private static final List<String> groupAdjectiveAndAdverbPhrases = Arrays.asList("B-ADJP", "B-ADVP");
   private static final List<String> groupSpecialPhrases = Arrays.asList("B-CONJP", "B-INTP", "B-LST", "B-PRT", "B-UCP");

   private final PreparedLyric lyric;
   private final ChunkerTool chunkerTool;

   public SuperChunkTagsEvaluator(final PreparedLyric lyric) {

      this.lyric = lyric;
      this.chunkerTool = ChunkerTool.getInstance();
   }

   public SuperChunkTags evaluate() {

      final SuperChunkTags chunkTags = new SuperChunkTags();

      final Map<String, Integer> chunkCounts = new HashMap<>();
      int nounPhrases = 0;
      int verbPhrases = 0;
      int prepositionalPhrases = 0;
      int adjectiveAndAdverbPhrases = 0;
      int specialPhrases = 0;

      final List<CoreLabel> tokens = lyric.getTokens();
      final int totalTokens = tokens.size();
      final String sent[] = new String[tokens.size()];
      final String pos[] = new String[tokens.size()];

      for (int i = 0; i < totalTokens; i++) {
         final CoreLabel tag = tokens.get(i);
         sent[i] = tag.word();
         pos[i] = tag.tag();
      }

      final List<String> tags = Arrays.stream(chunkerTool.chunk(sent, pos))
            .map(String::toUpperCase).collect(Collectors.toList());

      for (final String tag : tags) {
         if (groupNounPhrases.contains(tag)) {
            nounPhrases++;
         } else if (groupVerbPhrases.contains(tag)) {
            verbPhrases++;
         } else if (groupPrepositionalPhrases.contains(tag)) {
            prepositionalPhrases++;
         } else if (groupAdjectiveAndAdverbPhrases.contains(tag)) {
            adjectiveAndAdverbPhrases++;
         } else if (groupSpecialPhrases.contains(tag)) {
            specialPhrases++;
         }

         if (!tag.startsWith("I-")) {
            String t = tag.replaceAll("^B-", "");
            int count = chunkCounts.containsKey(t) ? chunkCounts.get(t) : 0;
            chunkCounts.put(t, count + 1);
         }
      }

      chunkTags.setLyricId(lyric.getId());
      chunkTags.setNounPhrasesRatio(nounPhrases / (float) totalTokens);
      chunkTags.setAdjectiveAndAdverbPhrasesRatio(adjectiveAndAdverbPhrases / (float) totalTokens);
      chunkTags.setPrepositionalPhrasesRatio(prepositionalPhrases / (float) totalTokens);
      chunkTags.setSpecialPhrasesRatio(specialPhrases / (float) totalTokens);
      chunkTags.setVerbPhrasesRatio(verbPhrases / (float) totalTokens);
      chunkCounts.forEach((k, v) -> {
         final ChunkTag chunkTag = new ChunkTag();
         chunkTag.setTag(k);
         chunkTag.setAmount(v);
         chunkTag.setAverageLength(tags.stream().filter(t -> (t.equals(k) || t.equals("B-" + k) || t.equals("I-" + k))).count() / (float) v);
         chunkTag.setRatio(v / (float) totalTokens);
         chunkTags.addTag(chunkTag);
      });

      return chunkTags;
   }
}
