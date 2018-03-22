package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "sentistrengths")
public class SentiStrength extends LyricFeature {

   @Field(Fields.POSITIVE_MOOD)
   private Float positiveMood;
   @Field(Fields.NEGATIVE_MOOD)
   private Float negativeMood;
   @Field(Fields.NEUTRAL_MOOD)
   private Float neutralMood;

   public Float getPositiveMood() {

      return positiveMood;
   }

   public void setPositiveMood(Float positiveMood) {

      this.positiveMood = positiveMood;
   }

   public Float getNegativeMood() {

      return negativeMood;
   }

   public void setNegativeMood(Float negativeMood) {

      this.negativeMood = negativeMood;
   }

   public Float getNeutralMood() {

      return neutralMood;
   }

   public void setNeutralMood(Float neutralMood) {

      this.neutralMood = neutralMood;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.POSITIVE_MOOD, positiveMood);
      update.set(Fields.NEGATIVE_MOOD, negativeMood);
      update.set(Fields.NEUTRAL_MOOD, neutralMood);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String POSITIVE_MOOD = "positive_mood";
      public static final String NEGATIVE_MOOD = "negative_mood";
      public static final String NEUTRAL_MOOD = "neutral_mood";

      private Fields() {

      }
   }
}
