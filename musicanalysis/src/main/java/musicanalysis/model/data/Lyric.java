package musicanalysis.model.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "lyrics")
public class Lyric {

   @Id
   private ObjectId id;
   @Field(Fields.URL)
   private String url;
   @Indexed
   @Field(Fields.TRACK_ID)
   private ObjectId trackId;
   @Field(Fields.TEXT)
   private String text;
   @Field(Fields.LANGUAGE)
   private String language;
   @Field(Fields.SOURCE)
   private String source;

   public ObjectId getId() {

      return id;
   }

   public void setId(ObjectId id) {

      this.id = id;
   }

   public String getUrl() {

      return url;
   }

   public void setUrl(String url) {

      this.url = url;
   }

   public ObjectId getTrackId() {

      return trackId;
   }

   public void setTrackId(ObjectId trackId) {

      this.trackId = trackId;
   }

   public String getText() {

      return text;
   }

   public void setText(String text) {

      this.text = text;
   }

   public String getLanguage() {

      return language;
   }

   public void setLanguage(String language) {

      this.language = language;
   }

   public String getSource() {

      return source;
   }

   public void setSource(String source) {

      this.source = source;
   }

   @Override
   public String toString() {

      return "Lyric{" +
            "id='" + id + '\'' +
            ", trackId='" + trackId + '\'' +
            ", text='" + text + '\'' +
            ", language='" + language + '\'' +
            ", source='" + source + '\'' +
            '}';
   }

   public Update toUpdate() {

      final Update update = new Update();
      update.set(Fields.URL, url);
      update.set(Fields.TRACK_ID, trackId);
      update.set(Fields.TEXT, text);
      update.set(Fields.LANGUAGE, language);
      update.set(Fields.SOURCE, source);
      return update;
   }

   public class Fields {

      public static final String ID = "_id";
      public static final String URL = "url";
      public static final String TRACK_ID = "track_id";
      public static final String TEXT = "text";
      public static final String LANGUAGE = "language";
      public static final String SOURCE = "source";

      private Fields() {

      }
   }
}
