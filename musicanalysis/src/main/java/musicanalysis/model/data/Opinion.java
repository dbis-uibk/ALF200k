package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "opinions")
public class Opinion extends LyricFeature {

   @Field(Fields.OPINION)
   private Float opinion;

   public Float getOpinion() {

      return opinion;
   }

   public void setOpinion(Float opinion) {

      this.opinion = opinion;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.OPINION, opinion);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String OPINION = "opinion";

      private Fields() {

      }
   }
}
