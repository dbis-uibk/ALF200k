package musicanalysis.model.music.lyric.feature.linguistic;

import musicanalysis.model.data.Echoism;
import musicanalysis.model.data.RepetitiveStructure;
import musicanalysis.model.data.Rhyme;
import musicanalysis.model.data.SlangUse;
import musicanalysis.model.music.Config;
import musicanalysis.model.music.lyric.feature.FeatureLoader;
import musicanalysis.model.music.lyric.feature.PreparedLyric;

public class LinguisticFeatures {

   private PreparedLyric l;
   private Echoism echoism = null;
   private RepetitiveStructure repetitiveStructure = null;
   private SlangUse slangUse = null;
   private Rhyme rhyme = null;

   public LinguisticFeatures(final PreparedLyric l) {

      this.l = l;
   }

   public static LinguisticFeatures load(final FeatureLoader loader, final PreparedLyric l) {

      return load(loader, l, (new Config(true)).linguisticConfig);
   }

   public static LinguisticFeatures load(final FeatureLoader loader, final PreparedLyric l, final Config.LinguisticConfig config) {

      final LinguisticFeatures linguisticFeatures = new LinguisticFeatures(l);
      linguisticFeatures.slangUse = config.enableSlangFeatures ? loader.load(SlangUse.class, l.getId()) : null;
      linguisticFeatures.rhyme = config.enableRhymeFeatures ? loader.load(Rhyme.class, l.getId()) : null;
      linguisticFeatures.echoism = config.enableEchoismFeatures ? loader.load(Echoism.class, l.getId()) : null;
      linguisticFeatures.repetitiveStructure = config.enableRepetitiveStructureFeatures ? loader.load(RepetitiveStructure.class, l.getId()) : null;
      return linguisticFeatures;
   }

   public void compute() {

      final SlangUseEvaluator slangUseEvaluator = new SlangUseEvaluator(l);
      final RhymesEvaluator rhymesEvaluator = new RhymesEvaluator(l);
      final EchoismEvaluator echoismEvaluator = new EchoismEvaluator(l);
      final RepetitiveStructureEvaluator repetitiveStructureEvaluator = new RepetitiveStructureEvaluator(l);

      slangUse = slangUseEvaluator.evaluate();
      rhyme = rhymesEvaluator.evaluate();
      echoism = echoismEvaluator.evaluate();
      repetitiveStructure = repetitiveStructureEvaluator.evaluate();
   }

   public Echoism getEchoism() {
      return echoism;
   }

   public RepetitiveStructure getRepetitiveStructure() {
      return repetitiveStructure;
   }

   public SlangUse getSlangUse() {
      return slangUse;
   }

   public Rhyme getRhyme() {
      return rhyme;
   }
}
