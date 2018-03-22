package musicanalysis.model.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@Document(collection = "testcases")
public class TestCase {

   @Id
   @Field(Fields.ID)
   private ObjectId id;
   @Field(Fields.ADAPTED_PLAYLIST_ID)
   private ObjectId adaptedPlaylistId;
   @Field(Fields.VALID_TRACKS)
   private List<ObjectId> validTracks;
   @Field(Fields.INVALID_TRACKS)
   private List<ObjectId> invalidTracks;

   public ObjectId getId() {
      return id;
   }

   public ObjectId getAdaptedPlaylistId() {
      return adaptedPlaylistId;
   }

   public void setAdaptedPlaylistId(ObjectId adaptedPlaylistId) {
      this.adaptedPlaylistId = adaptedPlaylistId;
   }

   public List<ObjectId> getValidTracks() {
      return validTracks;
   }

   public void setValidTracks(List<ObjectId> validTracks) {
      this.validTracks = validTracks;
   }

   public List<ObjectId> getInvalidTracks() {
      return invalidTracks;
   }

   public void setInvalidTracks(List<ObjectId> invalidTracks) {
      this.invalidTracks = invalidTracks;
   }

   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.ID, getId());
      update.set(Fields.ADAPTED_PLAYLIST_ID, getAdaptedPlaylistId());
      update.set(Fields.VALID_TRACKS, getValidTracks());
      update.set(Fields.INVALID_TRACKS, getInvalidTracks());

      return update;
   }

   public class Fields {

      public static final String ID = "_id";
      public static final String ADAPTED_PLAYLIST_ID = "adapted_playlist_id";
      public static final String VALID_TRACKS = "valid_tracks";
      public static final String INVALID_TRACKS = "invalid_tracks";

      private Fields() {

      }
   }
}
