package musicanalysis.model.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

public abstract class LyricFeature {

   @Id
   private ObjectId id;
   @Indexed
   @Field(Echoism.Fields.LYRIC_ID)
   private ObjectId lyricId;

   public ObjectId getId() {

      return id;
   }

   public ObjectId getLyricId() {

      return lyricId;
   }

   public void setLyricId(ObjectId lyricId) {

      this.lyricId = lyricId;
   }

   public abstract Update toUpdate();

   public class Fields {

      public static final String LYRIC_ID = "lyric_id";

      private Fields() {

      }
   }
}
