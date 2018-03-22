package musicanalysis.model.music.lyric.feature;

import musicanalysis.model.data.Lyric;
import musicanalysis.model.music.Config;
import musicanalysis.model.music.lyric.feature.lexical.LexicalFeatures;
import musicanalysis.model.music.lyric.feature.linguistic.LinguisticFeatures;
import musicanalysis.model.music.lyric.feature.semantic.SemanticFeatures;
import musicanalysis.model.music.lyric.feature.syntactic.SyntacticFeatures;

public class LyricFeatures {

   private LexicalFeatures lexicalFeatures;
   private SyntacticFeatures syntacticFeatures;
   private SemanticFeatures semanticFeatures;
   private LinguisticFeatures linguisticFeatures;

   public LyricFeatures(final PreparedLyric l) {

      this.lexicalFeatures = new LexicalFeatures(l);
      this.syntacticFeatures = new SyntacticFeatures(l);
      this.semanticFeatures = new SemanticFeatures(l);
      this.linguisticFeatures = new LinguisticFeatures(l);
   }

   public static LyricFeatures load(final FeatureLoader loader, final Lyric l) {

      return load(loader, l, new Config(true));
   }

   public static LyricFeatures load(final FeatureLoader loader, final Lyric l, final Config config) {

      final PreparedLyric pl = new PreparedLyric(l);
      final LyricFeatures lyricFeatures = new LyricFeatures(pl);
      lyricFeatures.lexicalFeatures = LexicalFeatures.load(loader, pl, config.lexicalConfig);
      lyricFeatures.linguisticFeatures = LinguisticFeatures.load(loader, pl, config.linguisticConfig);
      lyricFeatures.syntacticFeatures = SyntacticFeatures.load(loader, pl, config.syntacticConfig);
      lyricFeatures.semanticFeatures = SemanticFeatures.load(loader, pl, config.semanticConfig);
      return lyricFeatures;
   }

   public void computeAll(final float duration) {

      lexicalFeatures.compute(duration);
      syntacticFeatures.compute();
      semanticFeatures.compute();
      linguisticFeatures.compute();
   }

   public LexicalFeatures getLexicalFeatures() {

      return lexicalFeatures;
   }

   public SyntacticFeatures getSyntacticFeatures() {

      return syntacticFeatures;
   }

   public SemanticFeatures getSemanticFeatures() {

      return semanticFeatures;
   }

   public LinguisticFeatures getLinguisticFeatures() {

      return linguisticFeatures;
   }
}
