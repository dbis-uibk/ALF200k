package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "afinns")
public class AFINN extends LyricFeature {

   @Field(Fields.VALENCE)
   private Float valence;

   public Float getValence() {

      return valence;
   }

   public void setValence(Float valence) {

      this.valence = valence;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.VALENCE, valence);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String VALENCE = "valence";

      private Fields() {

      }
   }
}
