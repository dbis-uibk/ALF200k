package musicanalysis.model.music.lyric.feature.semantic;

import musicanalysis.model.data.DerivedRegressiveImage;
import musicanalysis.model.data.RegressiveImage;

public class DerivedRegressiveImageEvaluator {

   private final RegressiveImage image;

   public DerivedRegressiveImageEvaluator(final RegressiveImage image) {

      this.image = image;
   }

   public DerivedRegressiveImage compute() {

      final DerivedRegressiveImage derivedRegressiveImage = new DerivedRegressiveImage();

      int drive = image.getPrimaryNeedOrality() + image.getPrimaryNeedAnality() + image.getPrimaryNeedSex();
      int sensation = image.getPrimarySensationSound() + image.getPrimarySensationVision();
      int regressive = image.getPrimaryRegressiveCognitionNarcissism();
      int icarian = image.getPrimaryIcarianImageryFire() + image.getPrimaryIcarianImageryWater();

      int socialBehaviour = image.getSecondarySocialBehavior();
      int moralImperative = image.getSecondaryMoralImperative();

      int affection = image.getEmotionsAffection();
      int aggression = image.getEmotionsAggression();
      int glory = image.getEmotionsGlory();

      int primordialThough = drive + sensation + regressive + icarian;
      int conceptualThough = socialBehaviour + moralImperative;
      int emotion = affection + aggression + glory;

      int imagery = primordialThough + conceptualThough + emotion;

      // avoid division by zero
      float sexAnality;
      if (image.getPrimaryNeedSex() == 0 && image.getPrimaryNeedAnality() == 0) {
         sexAnality = 1;
      } else {
         float sex = image.getPrimaryNeedSex() == 0 ? 0.9f : image.getPrimaryNeedSex();
         float anality = image.getPrimaryNeedAnality() == 0 ? 0.1f : image.getPrimaryNeedAnality();
         sexAnality = sex / anality;
      }

      final float totalTokens = image.getWordCount();
      derivedRegressiveImage.setDrive(drive / totalTokens);
      derivedRegressiveImage.setSensation(sensation / totalTokens);
      derivedRegressiveImage.setRegressive(regressive / totalTokens);
      derivedRegressiveImage.setIcarian(icarian / totalTokens);
      derivedRegressiveImage.setSocialBehaviour(socialBehaviour / totalTokens);
      derivedRegressiveImage.setMoralImperative(moralImperative / totalTokens);
      derivedRegressiveImage.setAffection(affection / totalTokens);
      derivedRegressiveImage.setAggression(aggression / totalTokens);
      derivedRegressiveImage.setGlory(glory / totalTokens);
      derivedRegressiveImage.setPrimordialThough(primordialThough / totalTokens);
      derivedRegressiveImage.setConceptualThough(conceptualThough / totalTokens);
      derivedRegressiveImage.setEmotion(emotion / totalTokens);
      derivedRegressiveImage.setImagery(imagery / totalTokens);
      derivedRegressiveImage.setSexAnality(sexAnality);

      return derivedRegressiveImage;
   }
}
