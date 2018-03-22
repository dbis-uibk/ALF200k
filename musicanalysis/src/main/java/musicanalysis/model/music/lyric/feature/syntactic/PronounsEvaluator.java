package musicanalysis.model.music.lyric.feature.syntactic;

import edu.stanford.nlp.ling.CoreLabel;
import musicanalysis.model.data.Pronouns;
import musicanalysis.model.music.lyric.feature.PreparedLyric;

import java.util.Arrays;
import java.util.List;

public class PronounsEvaluator {

   private static final List<String> groupI = Arrays.asList("i", "me", "my", "mine", "myself");
   private static final List<String> groupYou = Arrays.asList("you", "your", "yours", "yourself", "yourselves");
   private static final List<String> groupIt = Arrays.asList("he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its", "itself");
   private static final List<String> groupWe = Arrays.asList("we", "us", "our", "ours", "ourselves");
   private static final List<String> groupThey = Arrays.asList("they", "them", "their", "theirs", "themselves");

   private final PreparedLyric lyric;

   public PronounsEvaluator(final PreparedLyric lyric) {

      this.lyric = lyric;
   }

   public Pronouns evaluate() {

      final Pronouns pronouns = new Pronouns();
      final float totalTokens = lyric.getTokens().size();

      int i = 0;
      int you = 0;
      int it = 0;
      int we = 0;
      int they = 0;
      int nonPronouns = 0;
      int iAndWe = 0;
      int others = 0;

      for (final CoreLabel token : lyric.getTokens()) {
         final String word = token.word().toLowerCase();
         if (groupI.contains(word)) {
            i++;
         } else if (groupYou.contains(word)) {
            you++;
         } else if (groupIt.contains(word)) {
            it++;
         } else if (groupWe.contains(word)) {
            we++;
         } else if (groupThey.contains(word)) {
            they++;
         } else {
            nonPronouns++;
         }
      }

      iAndWe = i + we;
      others = you + it + they;

      float iVsYou = getIVsYou(i, you);
      float excentricity = getExcentricity(iAndWe, others);

      pronouns.setLyricId(lyric.getId());
      pronouns.setI(i / totalTokens);
      pronouns.setYou(you / totalTokens);
      pronouns.setIt(it / totalTokens);
      pronouns.setWe(we / totalTokens);
      pronouns.setThey(they / totalTokens);
      pronouns.setIVsYou(iVsYou);
      pronouns.setExcentricity(excentricity);

      return pronouns;
   }

   private float getIVsYou(int i, int you) {
      float iVsYou;
      if (i > 2 * you) {
         iVsYou = 1f;
      } else if (you > 2 * i) {
         iVsYou = 0f;
      } else {
         iVsYou = 0.5f;
      }
      return iVsYou;
   }

   private float getExcentricity(int iAndWe, int others) {
      float excentricity;
      if (iAndWe > 2 * others) {
         excentricity = 1f;
      } else if (others > 2 * iAndWe) {
         excentricity = 0f;
      } else {
         excentricity = 0.5f;
      }
      return excentricity;
   }
}
