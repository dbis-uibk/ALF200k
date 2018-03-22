package musicanalysis.model.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "tracks")
public class Track {

   @Id
   @Field(Fields.ID)
   private ObjectId id;
   @Indexed
   @Field("id")
   private String trackId;
   @Field(Fields.TRACK_NAME)
   private String name;
   @Field(Fields.ARTISTS)
   private List<Artist> artists;

   public String getName() {

      return name;
   }

   public void setName(String name) {

      this.name = name;
   }

   public List<Artist> getArtists() {

      return artists;
   }

   public ObjectId getId() {

      return id;
   }

   public String getTrackId() {

      return trackId;
   }

   public void setTrackId(String trackId) {

      this.trackId = trackId;
   }

   @Override
   public String toString() {

      return "Track{" +
            "id=" + id +
            ", trackId='" + trackId + '\'' +
            ", name='" + name + '\'' +
            ", artists=" + artists +
            '}';
   }

   public class Fields {

      public static final String ID = "_id";
      public static final String TRACK_ID = "trackId";
      public static final String TRACK_NAME = "name";
      public static final String ARTISTS = "artists";

      private Fields() {

      }
   }
}
