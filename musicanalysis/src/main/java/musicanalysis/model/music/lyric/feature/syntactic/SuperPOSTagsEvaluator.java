package musicanalysis.model.music.lyric.feature.syntactic;

import edu.stanford.nlp.ling.CoreLabel;
import musicanalysis.model.data.POSTag;
import musicanalysis.model.data.SuperPOSTags;
import musicanalysis.model.music.lyric.feature.PreparedLyric;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperPOSTagsEvaluator {

   private static final List<String> groupVerbs = Arrays.asList("VB", "VBG", "VBP", "VBZ", "VBN", "VBD");
   private static final List<String> groupNouns = Arrays.asList("NN", "NNS", "NNP", "NNPS");
   private static final List<String> groupAdjectives = Arrays.asList("JJ", "JJR", "JJS");
   private static final List<String> groupAdverbs = Arrays.asList("RB", "RBR", "RBS");
   private static final List<String> groupWHQuestions = Arrays.asList("WDT", "WP", "WP$", "WRB");
   private static final List<String> groupSpecialCharacters = Arrays.asList("#", "$", ".", ",", ":", "-RRB-", "-LRB-", "``", "''");

   private final PreparedLyric lyric;

   public SuperPOSTagsEvaluator(final PreparedLyric lyric) {

      this.lyric = lyric;
   }

   /**
    * Count super POS tags.
    * Super POS tags are:
    * "VB", "VBG", "VBP", "VBZ", "VBN", "VBD"         ->   Verbs
    * "NN", "NNS", "NNP", "NNPS"                      ->   Nouns
    * "JJ", "JJR", "JJS"                              ->   Adjectives
    * "RB", "RBR", "RBS"                              ->   Adverbs
    * "WDT", "WP", "WP$", "WRB"                       ->   WH-Questions
    * "#", "$", ".", ",", ":", "-RRB-", "-LRB-", "\"" ->   Special Characters
    * See: https://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html
    */
   public SuperPOSTags evaluate() {

      final SuperPOSTags posTags = new SuperPOSTags();
      final float totalTokens = lyric.getTokens().size();

      final Map<String, Integer> tagCounts = new HashMap<>();
      int verbs = 0;
      int nouns = 0;
      int adjectives = 0;
      int adverbs = 0;
      int whQuestions = 0;
      int specialCharacters = 0;

      for (final CoreLabel token : lyric.getTokens()) {
         final String tag = token.tag().toUpperCase();
         if (groupVerbs.contains(tag)) {
            verbs++;
         } else if (groupNouns.contains(tag)) {
            nouns++;
         } else if (groupAdjectives.contains(tag)) {
            adjectives++;
         } else if (groupAdverbs.contains(tag)) {
            adverbs++;
         } else if (groupWHQuestions.contains(tag)) {
            whQuestions++;
         } else if (groupSpecialCharacters.contains(tag)) {
            specialCharacters++;
         }

         int count = tagCounts.containsKey(tag) ? tagCounts.get(tag) : 0;
         tagCounts.put(tag, count + 1);
      }

      posTags.setLyricId(lyric.getId());
      posTags.setVerbs(verbs / totalTokens);
      posTags.setNouns(nouns / totalTokens);
      posTags.setAdjectives(adjectives / totalTokens);
      posTags.setAdverbs(adverbs / totalTokens);
      posTags.setWhQuestions(whQuestions / totalTokens);
      posTags.setSpecialCharacters(specialCharacters / totalTokens);
      tagCounts.forEach((k, v) -> {
         final POSTag tag = new POSTag();
         tag.setTag(k);
         tag.setAmount(v);
         tag.setRatio(v / totalTokens);
         posTags.addTag(tag);
      });

      return posTags;
   }
}
