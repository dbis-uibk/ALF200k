package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "vadersentiments")
public class VADERSentiment extends LyricFeature {

   @Field(Fields.COMPOUND_MOOD)
   private Float compoundMood;
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

   public Float getCompoundMood() {

      return compoundMood;
   }

   public void setCompoundMood(Float compoundMood) {

      this.compoundMood = compoundMood;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.POSITIVE_MOOD, positiveMood);
      update.set(Fields.NEGATIVE_MOOD, negativeMood);
      update.set(Fields.NEUTRAL_MOOD, neutralMood);
      update.set(Fields.COMPOUND_MOOD, compoundMood);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String POSITIVE_MOOD = "positive_mood";
      public static final String NEGATIVE_MOOD = "negative_mood";
      public static final String NEUTRAL_MOOD = "neutral_mood";
      public static final String COMPOUND_MOOD = "compound_mood";

      private Fields() {

      }
   }
}
