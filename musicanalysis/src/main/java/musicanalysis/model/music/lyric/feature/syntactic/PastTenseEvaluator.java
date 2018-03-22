package musicanalysis.model.music.lyric.feature.syntactic;

import edu.stanford.nlp.ling.CoreLabel;
import musicanalysis.model.data.PastTense;
import musicanalysis.model.music.lyric.feature.PreparedLyric;

import java.util.Arrays;
import java.util.List;

public class PastTenseEvaluator {

   private static final List<String> verbs = Arrays.asList("VB", "VBG", "VBP", "VBZ", "VBD", "VBN");
   private static final List<String> verbsPast = Arrays.asList("VBD", "VBN");

   private final PreparedLyric lyric;

   public PastTenseEvaluator(final PreparedLyric lyric) {

      this.lyric = lyric;
   }

   public PastTense evaluate() {

      final PastTense pastTense = new PastTense();

      int verbCounter = 0;
      int verbPastCounter = 0;

      for (final CoreLabel t : lyric.getTokens()) {
         final String tag = t.tag().toUpperCase();
         if (verbsPast.contains(tag)) {
            verbPastCounter++;
         }
         if (verbs.contains(tag)) {
            verbCounter++;
         }
      }

      float pastTenseRatio = verbPastCounter / (float) verbCounter;

      pastTense.setLyricId(lyric.getId());
      pastTense.setPastTenseRatio(pastTenseRatio);

      return pastTense;
   }
}
