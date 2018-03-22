package musicanalysis.model.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@Document(collection = "adaptedplaylists")
public class AdaptedPlaylist {

   @Id
   private ObjectId id;
   @Field(Fields.PLAYLIST_ID)
   private ObjectId playlistId;
   @Field(Fields.MISSING_TRACKS)
   private Integer missingTracks;
   @Field(Fields.TRACKS)
   private List<ObjectId> tracks;

   public ObjectId getId() {
      return id;
   }

   public Integer getMissingTracks() {
      return missingTracks;
   }

   public void setMissingTracks(Integer missingTracks) {
      this.missingTracks = missingTracks;
   }

   public ObjectId getPlaylistId() {
      return playlistId;
   }

   public void setPlaylistId(ObjectId playlistId) {
      this.playlistId = playlistId;
   }

   public List<ObjectId> getTracks() {

      return tracks;
   }

   public void setTracks(List<ObjectId> tracks) {

      this.tracks = tracks;
   }


   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.ID, getId());
      update.set(Fields.PLAYLIST_ID, getPlaylistId());
      update.set(Fields.MISSING_TRACKS, getMissingTracks());
      update.set(Fields.TRACKS, getTracks());

      return update;
   }

   public class Fields {

      public static final String ID = "_id";
      public static final String PLAYLIST_ID = "playlist_id";
      public static final String MISSING_TRACKS = "missing_tracks";
      public static final String TRACKS = "tracks";

      private Fields() {

      }
   }
}
