package musicanalysis.model.music.lyric.feature.semantic;

import musicanalysis.model.data.SentiStrength;
import musicanalysis.model.music.lyric.feature.PreparedLyric;
import musicanalysis.model.nlp.Line;
import musicanalysis.model.settings.Settings;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

/**
 * http://de.slideshare.net/akhilpanchal/music-mood-detection
 */
public class SentiStrengthEvaluator {

   private final PreparedLyric lyric;
   private final SentimentStrengthTool sentimentStrengthTool;

   public SentiStrengthEvaluator(final PreparedLyric lyric) {

      this.lyric = lyric;
      this.sentimentStrengthTool = SentimentStrengthTool.getInstance();
   }

   public SentiStrength evaluate() {

      final SentiStrength sentiStrength = new SentiStrength();

      float positive = 0.0f;
      float negative = 0.0f;
      float neutral = 0.0f;

      final List<Line> lines = lyric.getLines();

      for (final Line l : lines) {
         final String[] result = sentimentStrengthTool.computeSentimentScores(l.getText());
         positive += Float.parseFloat(result[0]);
         negative += Float.parseFloat(result[1]);
         neutral += Float.parseFloat(result[2]);
      }

      positive /= lines.size();
      negative /= lines.size();
      neutral /= lines.size();

      sentiStrength.setLyricId(lyric.getId());
      sentiStrength.setPositiveMood(positive);
      sentiStrength.setNegativeMood(negative);
      sentiStrength.setNeutralMood(neutral);

      return sentiStrength;
   }
}
