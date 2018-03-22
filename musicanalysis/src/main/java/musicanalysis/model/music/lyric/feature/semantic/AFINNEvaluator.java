package musicanalysis.model.music.lyric.feature.semantic;

import edu.stanford.nlp.ling.CoreLabel;
import musicanalysis.model.data.AFINN;
import musicanalysis.model.music.lyric.feature.PreparedLyric;

public class AFINNEvaluator {

   final AFINNDictionary dictionary;
   private final PreparedLyric lyric;

   public AFINNEvaluator(final PreparedLyric lyric) {

      this.lyric = lyric;
      this.dictionary = AFINNDictionary.getInstance();
   }

   public AFINN evaluate() {

      final AFINN afinn = new AFINN();

      int valence = 0;
      int sentimentWords = 0;

      for (final CoreLabel token : lyric.getTokens()) {
         int value = dictionary.get(token.word());
         if (value != 0) {
            sentimentWords++;
         }
         valence += value;
      }

      afinn.setLyricId(lyric.getId());
      afinn.setValence(valence / (float) sentimentWords);

      return afinn;
   }
}
