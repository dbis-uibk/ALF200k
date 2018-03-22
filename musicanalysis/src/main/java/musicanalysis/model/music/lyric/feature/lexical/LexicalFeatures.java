package musicanalysis.model.music.lyric.feature.lexical;

import musicanalysis.model.data.BagOfWords;
import musicanalysis.model.data.TextStyle;
import musicanalysis.model.music.Config;
import musicanalysis.model.music.lyric.feature.FeatureLoader;
import musicanalysis.model.music.lyric.feature.PreparedLyric;

public class LexicalFeatures {

   private final PreparedLyric l;

   private TextStyle textStyle = null;
   private BagOfWords bagOfWords = null;

   public LexicalFeatures(final PreparedLyric l) {

      this.l = l;
   }

   public static LexicalFeatures load(final FeatureLoader loader, final PreparedLyric l) {

      return load(loader, l, (new Config(true)).lexicalConfig);
   }

   public static LexicalFeatures load(final FeatureLoader loader, final PreparedLyric l, final Config.LexicalConfig config) {

      final LexicalFeatures lexicalFeatures = new LexicalFeatures(l);
      lexicalFeatures.bagOfWords = config.enableBagOfWordsFeatures ? loader.load(BagOfWords.class, l.getId()) : null;
      lexicalFeatures.textStyle = config.enableTextStyleFeatures ? loader.load(TextStyle.class, l.getId()) : null;
      return lexicalFeatures;
   }

   public void compute(final float duration) {

      final BagOfWordsEvaluator bagOfWordsEvaluator = new BagOfWordsEvaluator(l);
      final TextStyleEvaluator textStyleEvaluator = new TextStyleEvaluator(l, duration);

      bagOfWords = bagOfWordsEvaluator.evaluate();
      textStyle = textStyleEvaluator.evaluate();
   }

   public TextStyle getTextStyle() {

      return textStyle;
   }

   public BagOfWords getBagOfWords() {

      return bagOfWords;
   }
}
