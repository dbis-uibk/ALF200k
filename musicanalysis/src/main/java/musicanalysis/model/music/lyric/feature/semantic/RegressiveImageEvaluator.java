package musicanalysis.model.music.lyric.feature.semantic;

import musicanalysis.model.data.RegressiveImage;
import musicanalysis.model.music.lyric.feature.PreparedLyric;

import java.util.ArrayList;
import java.util.List;

public class RegressiveImageEvaluator {

   final RegressiveImageDictionary dictionary;
   private final PreparedLyric lyric;

   public RegressiveImageEvaluator(final PreparedLyric lyric) {

      this.lyric = lyric;
      this.dictionary = RegressiveImageDictionary.getInstance();
   }

   public RegressiveImage evaluate() {

      final RegressiveImage regressiveImage;

      final List<String> words = new ArrayList<>();
      lyric.getTokens().forEach(t -> words.add(t.word()));
      regressiveImage = dictionary.getRegressiveImage(words);
      regressiveImage.setLyricId(lyric.getId());

      return regressiveImage;
   }
}
