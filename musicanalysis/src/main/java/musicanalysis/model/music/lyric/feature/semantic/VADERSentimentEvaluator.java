package musicanalysis.model.music.lyric.feature.semantic;

import musicanalysis.model.data.VADERSentiment;
import musicanalysis.model.music.lyric.feature.PreparedLyric;
import musicanalysis.model.nlp.Line;

import java.util.List;

public class VADERSentimentEvaluator {

   private final PreparedLyric lyric;
   private final VADERSentimentTool vaderSentimentTool;

   public VADERSentimentEvaluator(final PreparedLyric lyric) {

      this.lyric = lyric;
      this.vaderSentimentTool = VADERSentimentTool.getInstance();
   }

   public VADERSentiment evaluate() {

      final VADERSentiment sentiment = new VADERSentiment();

      float compound = 0.0f;
      float positive = 0.0f;
      float negative = 0.0f;
      float neutral = 0.0f;

      final List<Line> lines = lyric.getLines();

      for (final Line l : lines) {
         try {
            final VADERSentimentTool.Values result = vaderSentimentTool.getSentiment(l.getText());
            positive += result.getPositive();
            negative += result.getNegative();
            neutral += result.getNeutral();
            compound += result.getCompound();
         } catch (Exception e) {
            System.err.println("Errorneous line: " + l.getText());
            e.printStackTrace();
         }
      }

      positive /= lines.size();
      negative /= lines.size();
      neutral /= lines.size();
      compound /= lines.size();

      sentiment.setLyricId(lyric.getId());
      sentiment.setPositiveMood(positive);
      sentiment.setNegativeMood(negative);
      sentiment.setNeutralMood(neutral);
      sentiment.setCompoundMood(compound);

      return sentiment;
   }
}
