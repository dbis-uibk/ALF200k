package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "pasttenses")
public class PastTense extends LyricFeature {

   @Field(Fields.PAST_TENSE_RATIO)
   private float pastTenseRatio;

   public float getPastTenseRatio() {

      return pastTenseRatio;
   }

   public void setPastTenseRatio(float pastTenseRatio) {

      this.pastTenseRatio = pastTenseRatio;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.PAST_TENSE_RATIO, pastTenseRatio);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String PAST_TENSE_RATIO = "past_tense_ratio";
      private Fields() {

      }
   }
}
