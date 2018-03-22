package musicanalysis.model.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "playlists")
public class Playlist {

   @Id
   private ObjectId id;
   @Indexed
   private String name;
   private String owner;
   private Set<String> tracks;

   public Playlist(final String owner, final String name) {

      this.tracks = new HashSet<>();
      this.owner = owner;
      this.name = name;
   }

   public ObjectId getId() {

      return id;
   }

   public String getName() {

      return name;
   }

   public void addTrack(final String track) {

      this.tracks.add(track);
   }

   public void addTracks(Set<String> tracks) {

      this.tracks.addAll(tracks);
   }

   public void removeTrack(final String track) {

      this.tracks.remove(track);
   }

   public Set<String> getTracks() {

      return Collections.unmodifiableSet(this.tracks);
   }

   public String getOwner() {

      return owner;
   }

   @Override
   public boolean equals(Object o) {

      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Playlist playlist = (Playlist) o;

      return id != null ? id.equals(playlist.id) : playlist.id == null;

   }

   @Override
   public int hashCode() {

      return id != null ? id.hashCode() : 0;
   }

   @Override
   public String toString() {

      return "Playlist{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", owner='" + owner + '\'' +
            ", tracks=" + tracks +
            '}';
   }

   public Update toUpdate() {

      final Update update = new Update();
      update.set(Fields.NAME, name);
      update.set(Fields.OWNER, owner);
      update.set(Fields.TRACKS, tracks);
      return update;
   }

   public class Fields {

      public static final String ID = "_id";
      public static final String NAME = "name";
      public static final String OWNER = "owner";
      public static final String TRACKS = "tracks";
      private Fields() {

      }
   }
}
