package musicanalysis.model.music.lyric.feature.semantic;

import edu.stanford.nlp.ling.CoreLabel;
import musicanalysis.model.data.Opinion;
import musicanalysis.model.music.lyric.feature.PreparedLyric;

public class OpinionEvaluator {

   private final PreparedLyric lyric;
   private final OpinionLexicon lexicon;

   public OpinionEvaluator(final PreparedLyric lyric) {

      this.lyric = lyric;
      this.lexicon = OpinionLexicon.getInstance();
   }

   public Opinion evaluate() {

      final Opinion opinion = new Opinion();

      int opinionValue = 0;
      int sentimentWords = 0;

      for (final CoreLabel token : lyric.getTokens()) {
         int value = lexicon.get(token.word());
         if (value != 0) {
            sentimentWords++;
         }
         opinionValue += value;
      }

      opinion.setLyricId(lyric.getId());
      opinion.setOpinion(opinionValue / (float) sentimentWords);

      return opinion;
   }
}
