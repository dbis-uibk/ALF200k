package musicanalysis.model.music.lyric.feature.syntactic;

import musicanalysis.model.data.PastTense;
import musicanalysis.model.data.Pronouns;
import musicanalysis.model.data.SuperChunkTags;
import musicanalysis.model.data.SuperPOSTags;
import musicanalysis.model.music.Config;
import musicanalysis.model.music.lyric.feature.FeatureLoader;
import musicanalysis.model.music.lyric.feature.PreparedLyric;

public class SyntacticFeatures {

   private final PreparedLyric l;

   private Pronouns pronouns = null;
   private PastTense pastTense = null;
   private SuperChunkTags superChunkTags = null;
   private SuperPOSTags superPOSTags = null;

   public SyntacticFeatures(final PreparedLyric l) {

      this.l = l;
   }

   public static SyntacticFeatures load(final FeatureLoader loader, final PreparedLyric l) {

      return load(loader, l, (new Config(true)).syntacticConfig);
   }

   public static SyntacticFeatures load(final FeatureLoader loader, final PreparedLyric l, final Config.SyntacticConfig config) {

      final SyntacticFeatures syntacticFeatures = new SyntacticFeatures(l);
      syntacticFeatures.pronouns = config.enablePronounFeatures ? loader.load(Pronouns.class, l.getId()) : null;
      syntacticFeatures.pastTense = config.enablePastTenseRatioFeatures ? loader.load(PastTense.class, l.getId()) : null;
      syntacticFeatures.superChunkTags = config.enableSuperChunkTagFeatures ? loader.load(SuperChunkTags.class, l.getId()) : null;
      syntacticFeatures.superPOSTags = config.enablePOSTagFeatures ? loader.load(SuperPOSTags.class, l.getId()) : null;
      return syntacticFeatures;
   }

   public void compute() {

      final PronounsEvaluator pronounsEvaluator = new PronounsEvaluator(l);
      final PastTenseEvaluator pastTenseEvaluator = new PastTenseEvaluator(l);
      final SuperChunkTagsEvaluator superChunkTagsEvaluator = new SuperChunkTagsEvaluator(l);
      final SuperPOSTagsEvaluator superPOSTagsEvaluator = new SuperPOSTagsEvaluator(l);

      pronouns = pronounsEvaluator.evaluate();
      pastTense = pastTenseEvaluator.evaluate();
      superChunkTags = superChunkTagsEvaluator.evaluate();
      superPOSTags = superPOSTagsEvaluator.evaluate();
   }

   public Pronouns getPronouns() {
      return pronouns;
   }

   public PastTense getPastTense() {
      return pastTense;
   }

   public SuperChunkTags getSuperChunkTags() {
      return superChunkTags;
   }

   public SuperPOSTags getSuperPOSTags() {
      return superPOSTags;
   }
}
