package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "regressiveimages")
public class RegressiveImage extends LyricFeature {

   @Field(Fields.PRIMARY)
   private Integer primary;
   @Field(Fields.PRIMARY_NEED)
   private Integer primaryNeed;
   @Field(Fields.PRIMARY_NEED_ORALITY)
   private Integer primaryNeedOrality;
   @Field(Fields.PRIMARY_NEED_ANALITY)
   private Integer primaryNeedAnality;
   @Field(Fields.PRIMARY_NEED_SEX)
   private Integer primaryNeedSex;
   @Field(Fields.PRIMARY_SENSATION)
   private Integer primarySensation;
   @Field(Fields.PRIMARY_SENSATION_TOUCH)
   private Integer primarySensationTouch;
   @Field(Fields.PRIMARY_SENSATION_TASTE)
   private Integer primarySensationTaste;
   @Field(Fields.PRIMARY_SENSATION_ODOR)
   private Integer primarySensationOdor;
   @Field(Fields.PRIMARY_SENSATION_GENERAL_SENSATION)
   private Integer primarySensationGeneralSensation;
   @Field(Fields.PRIMARY_SENSATION_SOUND)
   private Integer primarySensationSound;
   @Field(Fields.PRIMARY_SENSATION_VISION)
   private Integer primarySensationVision;
   @Field(Fields.PRIMARY_SENSATION_COLD)
   private Integer primarySensationCold;
   @Field(Fields.PRIMARY_SENSATION_HARD)
   private Integer primarySensationHard;
   @Field(Fields.PRIMARY_SENSATION_SOFT)
   private Integer primarySensationSoft;
   @Field(Fields.PRIMARY_DEFENSIVE_SYMBOL)
   private Integer primaryDefensiveSymbol;
   @Field(Fields.PRIMARY_DEFENSIVE_SYMBOL_PASSIVITY)
   private Integer primaryDefensiveSymbolPassivity;
   @Field(Fields.PRIMARY_DEFENSIVE_SYMBOL_VOYAGE)
   private Integer primaryDefensiveSymbolVoyage;
   @Field(Fields.PRIMARY_DEFENSIVE_SYMBOL_RANDOM_MOVEMENT)
   private Integer primaryDefensiveSymbolRandomMovement;
   @Field(Fields.PRIMARY_DEFENSIVE_SYMBOL_DIFFUSION)
   private Integer primaryDefensiveSymbolDiffusion;
   @Field(Fields.PRIMARY_DEFENSIVE_SYMBOL_CHAOS)
   private Integer primaryDefensiveSymbolChaos;
   @Field(Fields.PRIMARY_REGRESSIVE_COGNITION)
   private Integer primaryRegressiveCognition;
   @Field(Fields.PRIMARY_REGRESSIVE_COGNITION_UNKNOWN)
   private Integer primaryRegressiveCognitionUnknown;
   @Field(Fields.PRIMARY_REGRESSIVE_COGNITION_TIMELESSNESS)
   private Integer primaryRegressiveCognitionTimelessness;
   @Field(Fields.PRIMARY_REGRESSIVE_COGNITION_CONSCIOUSNESS_ALTERNATION)
   private Integer primaryRegressiveCognitionConsciousnessAlternation;
   @Field(Fields.PRIMARY_REGRESSIVE_COGNITION_BRINK_PASSAGE)
   private Integer primaryRegressiveCognitionBrinkPassage;
   @Field(Fields.PRIMARY_REGRESSIVE_COGNITION_NARCISSISM)
   private Integer primaryRegressiveCognitionNarcissism;
   @Field(Fields.PRIMARY_REGRESSIVE_COGNITION_CONCRETENESS)
   private Integer primaryRegressiveCognitionConcreteness;
   @Field(Fields.PRIMARY_ICARIAN_IMAGERY)
   private Integer primaryIcarianImagery;
   @Field(Fields.PRIMARY_ICARIAN_IMAGERY_ASCEND)
   private Integer primaryIcarianImageryAscend;
   @Field(Fields.PRIMARY_ICARIAN_IMAGERY_HEIGHT)
   private Integer primaryIcarianImageryHeight;
   @Field(Fields.PRIMARY_ICARIAN_IMAGERY_DESCENT)
   private Integer primaryIcarianImageryDescent;
   @Field(Fields.PRIMARY_ICARIAN_IMAGERY_DEPTH)
   private Integer primaryIcarianImageryDepth;
   @Field(Fields.PRIMARY_ICARIAN_IMAGERY_FIRE)
   private Integer primaryIcarianImageryFire;
   @Field(Fields.PRIMARY_ICARIAN_IMAGERY_WATER)
   private Integer primaryIcarianImageryWater;
   @Field(Fields.SECONDARY)
   private Integer secondary;
   @Field(Fields.SECONDARY_ABSTRACTION)
   private Integer secondaryAbstraction;
   @Field(Fields.SECONDARY_SOCIAL_BEHAVIOR)
   private Integer secondarySocialBehavior;
   @Field(Fields.SECONDARY_INSTRUMENTAL_BEHAVIOR)
   private Integer secondaryInstrumentalBehavior;
   @Field(Fields.SECONDARY_RESTRAINT)
   private Integer secondaryRestraint;
   @Field(Fields.SECONDARY_ORDER)
   private Integer secondaryOrder;
   @Field(Fields.SECONDARY_TEMPORAL_REFERENCES)
   private Integer secondaryTemporalReferences;
   @Field(Fields.SECONDARY_MORAL_IMPERATIVE)
   private Integer secondaryMoralImperative;
   @Field(Fields.EMOTIONS)
   private Integer emotions;
   @Field(Fields.EMOTIONS_POSITIVE_AFFECT)
   private Integer emotionsPositiveAffect;
   @Field(Fields.EMOTIONS_ANXIETY)
   private Integer emotionsAnxiety;
   @Field(Fields.EMOTIONS_SADNESS)
   private Integer emotionsSadness;
   @Field(Fields.EMOTIONS_AFFECTION)
   private Integer emotionsAffection;
   @Field(Fields.EMOTIONS_AGGRESSION)
   private Integer emotionsAggression;
   @Field(Fields.EMOTIONS_EXPRESSIVE_BEHAVIOR)
   private Integer emotionsExpressiveBehavior;
   @Field(Fields.EMOTIONS_GLORY)
   private Integer emotionsGlory;
   @Field(Fields.WORD_COUNT)
   private Integer wordCount;

   public Integer getWordCount() {

      return this.wordCount;
   }

   public void setWordCount(int wordCount) {
      this.wordCount = wordCount;
   }

   public Integer getPrimary() {

      return primary;
   }

   public void setPrimary(Integer primary) {

      this.primary = primary;
   }

   public Integer getPrimaryNeed() {

      return primaryNeed;
   }

   public void setPrimaryNeed(Integer primaryNeed) {

      this.primaryNeed = primaryNeed;
   }

   public Integer getPrimaryNeedOrality() {

      return primaryNeedOrality;
   }

   public void setPrimaryNeedOrality(Integer primaryNeedOrality) {

      this.primaryNeedOrality = primaryNeedOrality;
   }

   public Integer getPrimaryNeedAnality() {

      return primaryNeedAnality;
   }

   public void setPrimaryNeedAnality(Integer primaryNeedAnality) {

      this.primaryNeedAnality = primaryNeedAnality;
   }

   public Integer getPrimaryNeedSex() {

      return primaryNeedSex;
   }

   public void setPrimaryNeedSex(Integer primaryNeedSex) {

      this.primaryNeedSex = primaryNeedSex;
   }

   public Integer getPrimarySensation() {

      return primarySensation;
   }

   public void setPrimarySensation(Integer primarySensation) {

      this.primarySensation = primarySensation;
   }

   public Integer getPrimarySensationTouch() {

      return primarySensationTouch;
   }

   public void setPrimarySensationTouch(Integer primarySensationTouch) {

      this.primarySensationTouch = primarySensationTouch;
   }

   public Integer getPrimarySensationTaste() {

      return primarySensationTaste;
   }

   public void setPrimarySensationTaste(Integer primarySensationTaste) {

      this.primarySensationTaste = primarySensationTaste;
   }

   public Integer getPrimarySensationOdor() {

      return primarySensationOdor;
   }

   public void setPrimarySensationOdor(Integer primarySensationOdor) {

      this.primarySensationOdor = primarySensationOdor;
   }

   public Integer getPrimarySensationGeneralSensation() {

      return primarySensationGeneralSensation;
   }

   public void setPrimarySensationGeneralSensation(Integer primarySensationGeneralSensation) {

      this.primarySensationGeneralSensation = primarySensationGeneralSensation;
   }

   public Integer getPrimarySensationSound() {

      return primarySensationSound;
   }

   public void setPrimarySensationSound(Integer primarySensationSound) {

      this.primarySensationSound = primarySensationSound;
   }

   public Integer getPrimarySensationVision() {

      return primarySensationVision;
   }

   public void setPrimarySensationVision(Integer primarySensationVision) {

      this.primarySensationVision = primarySensationVision;
   }

   public Integer getPrimarySensationCold() {

      return primarySensationCold;
   }

   public void setPrimarySensationCold(Integer primarySensationCold) {

      this.primarySensationCold = primarySensationCold;
   }

   public Integer getPrimarySensationHard() {

      return primarySensationHard;
   }

   public void setPrimarySensationHard(Integer primarySensationHard) {

      this.primarySensationHard = primarySensationHard;
   }

   public Integer getPrimarySensationSoft() {

      return primarySensationSoft;
   }

   public void setPrimarySensationSoft(Integer primarySensationSoft) {

      this.primarySensationSoft = primarySensationSoft;
   }

   public Integer getPrimaryDefensiveSymbol() {

      return primaryDefensiveSymbol;
   }

   public void setPrimaryDefensiveSymbol(Integer primaryDefensiveSymbol) {

      this.primaryDefensiveSymbol = primaryDefensiveSymbol;
   }

   public Integer getPrimaryDefensiveSymbolPassivity() {

      return primaryDefensiveSymbolPassivity;
   }

   public void setPrimaryDefensiveSymbolPassivity(Integer primaryDefensiveSymbolPassivity) {

      this.primaryDefensiveSymbolPassivity = primaryDefensiveSymbolPassivity;
   }

   public Integer getPrimaryDefensiveSymbolVoyage() {

      return primaryDefensiveSymbolVoyage;
   }

   public void setPrimaryDefensiveSymbolVoyage(Integer primaryDefensiveSymbolVoyage) {

      this.primaryDefensiveSymbolVoyage = primaryDefensiveSymbolVoyage;
   }

   public Integer getPrimaryDefensiveSymbolRandomMovement() {

      return primaryDefensiveSymbolRandomMovement;
   }

   public void setPrimaryDefensiveSymbolRandomMovement(Integer primaryDefensiveSymbolRandomMovement) {

      this.primaryDefensiveSymbolRandomMovement = primaryDefensiveSymbolRandomMovement;
   }

   public Integer getPrimaryDefensiveSymbolDiffusion() {

      return primaryDefensiveSymbolDiffusion;
   }

   public void setPrimaryDefensiveSymbolDiffusion(Integer primaryDefensiveSymbolDiffusion) {

      this.primaryDefensiveSymbolDiffusion = primaryDefensiveSymbolDiffusion;
   }

   public Integer getPrimaryDefensiveSymbolChaos() {

      return primaryDefensiveSymbolChaos;
   }

   public void setPrimaryDefensiveSymbolChaos(Integer primaryDefensiveSymbolChaos) {

      this.primaryDefensiveSymbolChaos = primaryDefensiveSymbolChaos;
   }

   public Integer getPrimaryRegressiveCognition() {

      return primaryRegressiveCognition;
   }

   public void setPrimaryRegressiveCognition(Integer primaryRegressiveCognition) {

      this.primaryRegressiveCognition = primaryRegressiveCognition;
   }

   public Integer getPrimaryRegressiveCognitionUnknown() {

      return primaryRegressiveCognitionUnknown;
   }

   public void setPrimaryRegressiveCognitionUnknown(Integer primaryRegressiveCognitionUnknown) {

      this.primaryRegressiveCognitionUnknown = primaryRegressiveCognitionUnknown;
   }

   public Integer getPrimaryRegressiveCognitionTimelessness() {

      return primaryRegressiveCognitionTimelessness;
   }

   public void setPrimaryRegressiveCognitionTimelessness(Integer primaryRegressiveCognitionTimelessness) {

      this.primaryRegressiveCognitionTimelessness = primaryRegressiveCognitionTimelessness;
   }

   public Integer getPrimaryRegressiveCognitionConsciousnessAlternation() {

      return primaryRegressiveCognitionConsciousnessAlternation;
   }

   public void setPrimaryRegressiveCognitionConsciousnessAlternation(Integer primaryRegressiveCognitionConsciousnessAlternation) {

      this.primaryRegressiveCognitionConsciousnessAlternation = primaryRegressiveCognitionConsciousnessAlternation;
   }

   public Integer getPrimaryRegressiveCognitionBrinkPassage() {

      return primaryRegressiveCognitionBrinkPassage;
   }

   public void setPrimaryRegressiveCognitionBrinkPassage(Integer primaryRegressiveCognitionBrinkPassage) {

      this.primaryRegressiveCognitionBrinkPassage = primaryRegressiveCognitionBrinkPassage;
   }

   public Integer getPrimaryRegressiveCognitionNarcissism() {

      return primaryRegressiveCognitionNarcissism;
   }

   public void setPrimaryRegressiveCognitionNarcissism(Integer primaryRegressiveCognitionNarcissism) {

      this.primaryRegressiveCognitionNarcissism = primaryRegressiveCognitionNarcissism;
   }

   public Integer getPrimaryRegressiveCognitionConcreteness() {

      return primaryRegressiveCognitionConcreteness;
   }

   public void setPrimaryRegressiveCognitionConcreteness(Integer primaryRegressiveCognitionConcreteness) {

      this.primaryRegressiveCognitionConcreteness = primaryRegressiveCognitionConcreteness;
   }

   public Integer getPrimaryIcarianImagery() {

      return primaryIcarianImagery;
   }

   public void setPrimaryIcarianImagery(Integer primaryIcarianImagery) {

      this.primaryIcarianImagery = primaryIcarianImagery;
   }

   public Integer getPrimaryIcarianImageryAscend() {

      return primaryIcarianImageryAscend;
   }

   public void setPrimaryIcarianImageryAscend(Integer primaryIcarianImageryAscend) {

      this.primaryIcarianImageryAscend = primaryIcarianImageryAscend;
   }

   public Integer getPrimaryIcarianImageryHeight() {

      return primaryIcarianImageryHeight;
   }

   public void setPrimaryIcarianImageryHeight(Integer primaryIcarianImageryHeight) {

      this.primaryIcarianImageryHeight = primaryIcarianImageryHeight;
   }

   public Integer getPrimaryIcarianImageryDescent() {

      return primaryIcarianImageryDescent;
   }

   public void setPrimaryIcarianImageryDescent(Integer primaryIcarianImageryDescent) {

      this.primaryIcarianImageryDescent = primaryIcarianImageryDescent;
   }

   public Integer getPrimaryIcarianImageryDepth() {

      return primaryIcarianImageryDepth;
   }

   public void setPrimaryIcarianImageryDepth(Integer primaryIcarianImageryDepth) {

      this.primaryIcarianImageryDepth = primaryIcarianImageryDepth;
   }

   public Integer getPrimaryIcarianImageryFire() {

      return primaryIcarianImageryFire;
   }

   public void setPrimaryIcarianImageryFire(Integer primaryIcarianImageryFire) {

      this.primaryIcarianImageryFire = primaryIcarianImageryFire;
   }

   public Integer getPrimaryIcarianImageryWater() {

      return primaryIcarianImageryWater;
   }

   public void setPrimaryIcarianImageryWater(Integer primaryIcarianImageryWater) {

      this.primaryIcarianImageryWater = primaryIcarianImageryWater;
   }

   public Integer getSecondary() {

      return secondary;
   }

   public void setSecondary(Integer secondary) {

      this.secondary = secondary;
   }

   public Integer getSecondaryAbstraction() {

      return secondaryAbstraction;
   }

   public void setSecondaryAbstraction(Integer secondaryAbstraction) {

      this.secondaryAbstraction = secondaryAbstraction;
   }

   public Integer getSecondarySocialBehavior() {

      return secondarySocialBehavior;
   }

   public void setSecondarySocialBehavior(Integer secondarySocialBehavior) {

      this.secondarySocialBehavior = secondarySocialBehavior;
   }

   public Integer getSecondaryInstrumentalBehavior() {

      return secondaryInstrumentalBehavior;
   }

   public void setSecondaryInstrumentalBehavior(Integer secondaryInstrumentalBehavior) {

      this.secondaryInstrumentalBehavior = secondaryInstrumentalBehavior;
   }

   public Integer getSecondaryRestraint() {

      return secondaryRestraint;
   }

   public void setSecondaryRestraint(Integer secondaryRestraint) {

      this.secondaryRestraint = secondaryRestraint;
   }

   public Integer getSecondaryOrder() {

      return secondaryOrder;
   }

   public void setSecondaryOrder(Integer secondaryOrder) {

      this.secondaryOrder = secondaryOrder;
   }

   public Integer getSecondaryTemporalReferences() {

      return secondaryTemporalReferences;
   }

   public void setSecondaryTemporalReferences(Integer secondaryTemporalReferences) {

      this.secondaryTemporalReferences = secondaryTemporalReferences;
   }

   public Integer getSecondaryMoralImperative() {

      return secondaryMoralImperative;
   }

   public void setSecondaryMoralImperative(Integer secondaryMoralImperative) {

      this.secondaryMoralImperative = secondaryMoralImperative;
   }

   public Integer getEmotions() {

      return emotions;
   }

   public void setEmotions(Integer emotions) {

      this.emotions = emotions;
   }

   public Integer getEmotionsPositiveAffect() {

      return emotionsPositiveAffect;
   }

   public void setEmotionsPositiveAffect(Integer emotionsPositiveAffect) {

      this.emotionsPositiveAffect = emotionsPositiveAffect;
   }

   public Integer getEmotionsAnxiety() {

      return emotionsAnxiety;
   }

   public void setEmotionsAnxiety(Integer emotionsAnxiety) {

      this.emotionsAnxiety = emotionsAnxiety;
   }

   public Integer getEmotionsSadness() {

      return emotionsSadness;
   }

   public void setEmotionsSadness(Integer emotionsSadness) {

      this.emotionsSadness = emotionsSadness;
   }

   public Integer getEmotionsAffection() {

      return emotionsAffection;
   }

   public void setEmotionsAffection(Integer emotionsAffection) {

      this.emotionsAffection = emotionsAffection;
   }

   public Integer getEmotionsAggression() {

      return emotionsAggression;
   }

   public void setEmotionsAggression(Integer emotionsAggression) {

      this.emotionsAggression = emotionsAggression;
   }

   public Integer getEmotionsExpressiveBehavior() {

      return emotionsExpressiveBehavior;
   }

   public void setEmotionsExpressiveBehavior(Integer emotionsExpressiveBehavior) {

      this.emotionsExpressiveBehavior = emotionsExpressiveBehavior;
   }

   public Integer getEmotionsGlory() {

      return emotionsGlory;
   }

   public void setEmotionsGlory(Integer emotionsGlory) {

      this.emotionsGlory = emotionsGlory;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.PRIMARY, primary);
      update.set(Fields.PRIMARY_NEED, primaryNeed);
      update.set(Fields.PRIMARY_NEED_ORALITY, primaryNeedOrality);
      update.set(Fields.PRIMARY_NEED_ANALITY, primaryNeedAnality);
      update.set(Fields.PRIMARY_NEED_SEX, primaryNeedSex);
      update.set(Fields.PRIMARY_SENSATION, primarySensation);
      update.set(Fields.PRIMARY_SENSATION_TOUCH, primarySensationTouch);
      update.set(Fields.PRIMARY_SENSATION_TASTE, primarySensationTaste);
      update.set(Fields.PRIMARY_SENSATION_ODOR, primarySensationOdor);
      update.set(Fields.PRIMARY_SENSATION_GENERAL_SENSATION, primarySensationGeneralSensation);
      update.set(Fields.PRIMARY_SENSATION_SOUND, primarySensationSound);
      update.set(Fields.PRIMARY_SENSATION_VISION, primarySensationVision);
      update.set(Fields.PRIMARY_SENSATION_COLD, primarySensationCold);
      update.set(Fields.PRIMARY_SENSATION_HARD, primarySensationHard);
      update.set(Fields.PRIMARY_SENSATION_SOFT, primarySensationSoft);
      update.set(Fields.PRIMARY_DEFENSIVE_SYMBOL, primaryDefensiveSymbol);
      update.set(Fields.PRIMARY_DEFENSIVE_SYMBOL_PASSIVITY, primaryDefensiveSymbolPassivity);
      update.set(Fields.PRIMARY_DEFENSIVE_SYMBOL_VOYAGE, primaryDefensiveSymbolVoyage);
      update.set(Fields.PRIMARY_DEFENSIVE_SYMBOL_RANDOM_MOVEMENT, primaryDefensiveSymbolRandomMovement);
      update.set(Fields.PRIMARY_DEFENSIVE_SYMBOL_DIFFUSION, primaryDefensiveSymbolDiffusion);
      update.set(Fields.PRIMARY_DEFENSIVE_SYMBOL_CHAOS, primaryDefensiveSymbolChaos);
      update.set(Fields.PRIMARY_REGRESSIVE_COGNITION, primaryRegressiveCognition);
      update.set(Fields.PRIMARY_REGRESSIVE_COGNITION_UNKNOWN, primaryRegressiveCognitionUnknown);
      update.set(Fields.PRIMARY_REGRESSIVE_COGNITION_TIMELESSNESS, primaryRegressiveCognitionTimelessness);
      update.set(Fields.PRIMARY_REGRESSIVE_COGNITION_CONSCIOUSNESS_ALTERNATION, primaryRegressiveCognitionConsciousnessAlternation);
      update.set(Fields.PRIMARY_REGRESSIVE_COGNITION_BRINK_PASSAGE, primaryRegressiveCognitionBrinkPassage);
      update.set(Fields.PRIMARY_REGRESSIVE_COGNITION_NARCISSISM, primaryRegressiveCognitionNarcissism);
      update.set(Fields.PRIMARY_REGRESSIVE_COGNITION_CONCRETENESS, primaryRegressiveCognitionConcreteness);
      update.set(Fields.PRIMARY_ICARIAN_IMAGERY, primaryIcarianImagery);
      update.set(Fields.PRIMARY_ICARIAN_IMAGERY_ASCEND, primaryIcarianImageryAscend);
      update.set(Fields.PRIMARY_ICARIAN_IMAGERY_HEIGHT, primaryIcarianImageryHeight);
      update.set(Fields.PRIMARY_ICARIAN_IMAGERY_DESCENT, primaryIcarianImageryDescent);
      update.set(Fields.PRIMARY_ICARIAN_IMAGERY_DEPTH, primaryIcarianImageryDepth);
      update.set(Fields.PRIMARY_ICARIAN_IMAGERY_FIRE, primaryIcarianImageryFire);
      update.set(Fields.PRIMARY_ICARIAN_IMAGERY_WATER, primaryIcarianImageryWater);
      update.set(Fields.SECONDARY, secondary);
      update.set(Fields.SECONDARY_ABSTRACTION, secondaryAbstraction);
      update.set(Fields.SECONDARY_SOCIAL_BEHAVIOR, secondarySocialBehavior);
      update.set(Fields.SECONDARY_INSTRUMENTAL_BEHAVIOR, secondaryInstrumentalBehavior);
      update.set(Fields.SECONDARY_RESTRAINT, secondaryRestraint);
      update.set(Fields.SECONDARY_ORDER, secondaryOrder);
      update.set(Fields.SECONDARY_TEMPORAL_REFERENCES, secondaryTemporalReferences);
      update.set(Fields.SECONDARY_MORAL_IMPERATIVE, secondaryMoralImperative);
      update.set(Fields.EMOTIONS, emotions);
      update.set(Fields.EMOTIONS_POSITIVE_AFFECT, emotionsPositiveAffect);
      update.set(Fields.EMOTIONS_ANXIETY, emotionsAnxiety);
      update.set(Fields.EMOTIONS_SADNESS, emotionsSadness);
      update.set(Fields.EMOTIONS_AFFECTION, emotionsAffection);
      update.set(Fields.EMOTIONS_AGGRESSION, emotionsAggression);
      update.set(Fields.EMOTIONS_EXPRESSIVE_BEHAVIOR, emotionsExpressiveBehavior);
      update.set(Fields.EMOTIONS_GLORY, emotionsGlory);
      update.set(Fields.WORD_COUNT, wordCount);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String PRIMARY = "primary";
      public static final String PRIMARY_NEED = "primary_need";
      public static final String PRIMARY_NEED_ORALITY = "primary_need_orality";
      public static final String PRIMARY_NEED_ANALITY = "primary_need_anality";
      public static final String PRIMARY_NEED_SEX = "primary_need_sex";
      public static final String PRIMARY_SENSATION = "primary_sensation";
      public static final String PRIMARY_SENSATION_TOUCH = "primary_sensation_touch";
      public static final String PRIMARY_SENSATION_TASTE = "primary_sensation_taste";
      public static final String PRIMARY_SENSATION_ODOR = "primary_sensation_odor";
      public static final String PRIMARY_SENSATION_GENERAL_SENSATION = "primary_sensation_general_sensation";
      public static final String PRIMARY_SENSATION_SOUND = "primary_sensation_sound";
      public static final String PRIMARY_SENSATION_VISION = "primary_sensation_vision";
      public static final String PRIMARY_SENSATION_COLD = "primary_sensation_cold";
      public static final String PRIMARY_SENSATION_HARD = "primary_sensation_hard";
      public static final String PRIMARY_SENSATION_SOFT = "primary_sensation_soft";
      public static final String PRIMARY_DEFENSIVE_SYMBOL = "primary_defensive_symbol";
      public static final String PRIMARY_DEFENSIVE_SYMBOL_PASSIVITY = "primary_defensive_symbol_passivity";
      public static final String PRIMARY_DEFENSIVE_SYMBOL_VOYAGE = "primary_defensive_voyage";
      public static final String PRIMARY_DEFENSIVE_SYMBOL_RANDOM_MOVEMENT = "primary_defensive_symbol_random_movement";
      public static final String PRIMARY_DEFENSIVE_SYMBOL_DIFFUSION = "primary_defensive_symbol_diffusion";
      public static final String PRIMARY_DEFENSIVE_SYMBOL_CHAOS = "primary_defensive_symbol_chaos";
      public static final String PRIMARY_REGRESSIVE_COGNITION = "primary_regressive_cognition";
      public static final String PRIMARY_REGRESSIVE_COGNITION_UNKNOWN = "primary_regressive_cognition_unknown";
      public static final String PRIMARY_REGRESSIVE_COGNITION_TIMELESSNESS = "primary_regressive_cognition_timelessness";
      public static final String PRIMARY_REGRESSIVE_COGNITION_CONSCIOUSNESS_ALTERNATION = "primary_regressive_cognition_consciousness_alternation";
      public static final String PRIMARY_REGRESSIVE_COGNITION_BRINK_PASSAGE = "primary_regressive_cognition_brink_passage";
      public static final String PRIMARY_REGRESSIVE_COGNITION_NARCISSISM = "primary_regressive_cognition_narcissism";
      public static final String PRIMARY_REGRESSIVE_COGNITION_CONCRETENESS = "primary_regressive_cognition_concreteness";
      public static final String PRIMARY_ICARIAN_IMAGERY = "primary_icarian_imagery";
      public static final String PRIMARY_ICARIAN_IMAGERY_ASCEND = "primary_icarian_imagery_ascend";
      public static final String PRIMARY_ICARIAN_IMAGERY_HEIGHT = "primary_icarian_imagery_height";
      public static final String PRIMARY_ICARIAN_IMAGERY_DESCENT = "primary_icarian_imagery_descent";
      public static final String PRIMARY_ICARIAN_IMAGERY_DEPTH = "primary_icarian_imagery_depth";
      public static final String PRIMARY_ICARIAN_IMAGERY_FIRE = "primary_icarian_imagery_fire";
      public static final String PRIMARY_ICARIAN_IMAGERY_WATER = "primary_icarian_imagery_water";
      public static final String SECONDARY = "secondary";
      public static final String SECONDARY_ABSTRACTION = "secondary_abstraction";
      public static final String SECONDARY_SOCIAL_BEHAVIOR = "secondary_social_behavior";
      public static final String SECONDARY_INSTRUMENTAL_BEHAVIOR = "secondary_instrumental_behavior";
      public static final String SECONDARY_RESTRAINT = "secondary_restraint";
      public static final String SECONDARY_ORDER = "secondary_order";
      public static final String SECONDARY_TEMPORAL_REFERENCES = "secondary_temporal_references";
      public static final String SECONDARY_MORAL_IMPERATIVE = "secondary_moral_imperative";
      public static final String EMOTIONS = "emotions";
      public static final String EMOTIONS_POSITIVE_AFFECT = "emotions_positive_affect";
      public static final String EMOTIONS_ANXIETY = "emotions_anxiety";
      public static final String EMOTIONS_SADNESS = "emotions_sadness";
      public static final String EMOTIONS_AFFECTION = "emotions_affection";
      public static final String EMOTIONS_AGGRESSION = "emotions_aggression";
      public static final String EMOTIONS_EXPRESSIVE_BEHAVIOR = "emotions_expressive_behavior";
      public static final String EMOTIONS_GLORY = "emotions_glory";
      public static final String WORD_COUNT = "word_count";

      private Fields() {

      }
   }
}
