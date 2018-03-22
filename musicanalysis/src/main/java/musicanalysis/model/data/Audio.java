package musicanalysis.model.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "audios")
public class Audio {

   public class Fields {

      private Fields() {

      }

      public static final String TRACK_ID = "track_id";
      public static final String TEMPO = "tempo";
      public static final String ENERGY = "energy";
      public static final String LIVENESS = "liveness";
      public static final String SPEECHINESS = "speechiness";
      public static final String ACOUSTICNESS = "acousticness";
      public static final String DANCEABILITY = "danceability";
      public static final String DURATION = "duration";
      public static final String LOUDNESS = "loudness";
      public static final String VALENCE = "valence";
      public static final String INSTRUMENTALNESS = "instrumentalness";
   }

   @Id
   private ObjectId id;

   @Indexed
   @Field(Fields.TRACK_ID)
   private ObjectId trackId;

   @Field(Fields.TEMPO)
   private Float tempo;

   @Field(Fields.ENERGY)
   private Float energy;

   @Field(Fields.LIVENESS)
   private Float liveness;

   @Field(Fields.SPEECHINESS)
   private Float speechiness;

   @Field(Fields.ACOUSTICNESS)
   private Float acousticness;

   @Field(Fields.DANCEABILITY)
   private Float danceability;

   @Field(Fields.DURATION)
   private Float duration;

   @Field(Fields.LOUDNESS)
   private Float loudness;

   @Field(Fields.VALENCE)
   private Float valence;

   @Field(Fields.INSTRUMENTALNESS)
   private Float instrumentalness;

   public ObjectId getId() {

      return id;
   }

   public ObjectId getTrackId() {

      return trackId;
   }

   public void setTrackId(ObjectId trackId) {

      this.trackId = trackId;
   }

   /**
    * Beats per minute.
    *
    * @return tempo score
    */
   public Float getTempo() {

      return tempo;
   }

   public void setTempo(Float tempo) {

      this.tempo = tempo;
   }

   /**
    * Represents a perceptual measure of intensity and powerful activity released throughout the track.
    * Typical energetic tracks feel fast, loud, and noisy. For example, death metal has high energy, while a Bach prelude scores low on the scale.
    * Perceptual features contributing to this attribute include dynamic range, perceived loudness, timbre, onset rate, and general entropy.
    *
    * @return energy score
    */
   public Float getEnergy() {

      return energy;
   }

   public void setEnergy(Float energy) {

      this.energy = energy;
   }

   /**
    * Detects the presence of an audience in the recording. The more confident that the track is live, the closer to 1.0 the attribute value.
    * Due to the relatively small population of live tracks in the overall domain, the threshold for detecting liveness is higher than
    * for speechiness. A value above 0.8 provides strong likelihood that the track is live.
    * Values between 0.6 and 0.8 describe tracks that may or may not be live or contain simulated audience sounds at the beginning or end.
    * Values below 0.6 most likely represent studio recordings.
    *
    * @return liveness score
    */
   public Float getLiveness() {

      return liveness;
   }

   public void setLiveness(Float liveness) {

      this.liveness = liveness;
   }

   /**
    * Detects the presence of spoken words in a track. The more exclusively speech-like the recording (e.g. talk show, audio book, poetry),
    * the closer to 1.0 the attribute value. Values above 0.66 describe tracks that are probably made entirely of spoken words.
    * Values between 0.33 and 0.66 describe tracks that may contain both music and speech, either in sections or layered,
    * including such cases as rap music. Values below 0.33 most likely represent music and other non-speech-like tracks.
    *
    * @return speechiness score
    */
   public Float getSpeechiness() {

      return speechiness;
   }

   public void setSpeechiness(Float speechiness) {

      this.speechiness = speechiness;
   }

   /**
    * Represents the likelihood a recording was created by solely acoustic means such as voice and acoustic instruments as opposed to
    * electronically such as with synthesized, amplified, or effected instruments. Tracks with low acousticness include electric guitars,
    * distortion, synthesizers, auto-tuned vocals, and drum machines, whereas songs with orchestral instruments, acoustic guitars,
    * unaltered voice, and natural drum kits will have acousticness values closer to 1.0.
    *
    * @return acousticness score
    */
   public Float getAcousticness() {

      return acousticness;
   }

   public void setAcousticness(Float acousticness) {

      this.acousticness = acousticness;
   }

   /**
    * Describes how suitable a track is for dancing using a number of musical elements (the more suitable for dancing, the closer to 1.0 the value).
    * The combination of musical elements that best characterize danceability include tempo, rhythm stability, beat strength,
    * and overall regularity.
    *
    * @return danceability score
    */
   public Float getDanceability() {

      return danceability;
   }

   public void setDanceability(Float danceability) {

      this.danceability = danceability;
   }

   public Float getDuration() {

      return duration;
   }

   public void setDuration(Float duration) {

      this.duration = duration;
   }

   public Float getLoudness() {

      return loudness;
   }

   public void setLoudness(Float loudness) {

      this.loudness = loudness;
   }

   /**
    * Describes the musical positiveness conveyed by a track. Tracks with high valence sound more positive (e.g., happy, cheerful, euphoric),
    * while tracks with low valence sound more negative (e.g. sad, depressed, angry).
    * This attribute in combination with energy is a strong indicator of acoustic mood, the general emotional qualities that may
    * characterize the track's acoustics. Note that in the case of vocal music, lyrics may differ semantically
    * from the perceived acoustic mood.
    *
    * @return valence score
    */
   public Float getValence() {

      return valence;
   }

   public void setValence(Float valence) {

      this.valence = valence;
   }

   public Float getInstrumentalness() {

      return instrumentalness;
   }

   public void setInstrumentalness(Float instrumentalness) {

      this.instrumentalness = instrumentalness;
   }

   public Update toUpdate() {

      final Update update = new Update();
      update.set(Fields.TRACK_ID, trackId);
      update.set(Fields.TEMPO, tempo);
      update.set(Fields.ENERGY, energy);
      update.set(Fields.LIVENESS, liveness);
      update.set(Fields.SPEECHINESS, speechiness);
      update.set(Fields.ACOUSTICNESS, acousticness);
      update.set(Fields.DANCEABILITY, danceability);
      update.set(Fields.DURATION, duration);
      update.set(Fields.LOUDNESS, loudness);
      update.set(Fields.VALENCE, valence);
      update.set(Fields.INSTRUMENTALNESS, instrumentalness);
      return update;
   }
}
