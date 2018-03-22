package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@Document(collection = "bagsofwords")
public class BagOfWords extends LyricFeature {

   @Field(Fields.ENTIRE_WORDS)
   private List<String> entireWords;
   @Field(Fields.STOP_WORDS)
   private List<String> stopWords;
   @Field(Fields.CONTENT_WORDS)
   private List<String> contentWords;
   @Field(Fields.LEMMATIZED_CONTENT_WORDS)
   private List<String> lemmatizedContentWords;
   @Field(Fields.POS_TAGS)
   private List<String> posTags;

   public List<String> getEntireWords() {
      return entireWords;
   }

   public void setEntireWords(List<String> entireWords) {
      this.entireWords = entireWords;
   }

   public List<String> getStopWords() {
      return stopWords;
   }

   public void setStopWords(List<String> stopWords) {
      this.stopWords = stopWords;
   }

   public List<String> getContentWords() {
      return contentWords;
   }

   public void setContentWords(List<String> contentWords) {
      this.contentWords = contentWords;
   }

   public List<String> getLemmatizedContentWords() {
      return lemmatizedContentWords;
   }

   public void setLemmatizedContentWords(List<String> lemmatizedContentWords) {
      this.lemmatizedContentWords = lemmatizedContentWords;
   }

   public List<String> getPOSTags() {
      return posTags;
   }

   public void setPOSTags(List<String> posTags) {
      this.posTags = posTags;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.ENTIRE_WORDS, entireWords);
      update.set(Fields.STOP_WORDS, stopWords);
      update.set(Fields.CONTENT_WORDS, contentWords);
      update.set(Fields.LEMMATIZED_CONTENT_WORDS, lemmatizedContentWords);
      update.set(Fields.POS_TAGS, posTags);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String ENTIRE_WORDS = "entire_words";
      public static final String STOP_WORDS = "stop_words";
      public static final String CONTENT_WORDS = "content_words";
      public static final String LEMMATIZED_CONTENT_WORDS = "lemmatized_content_words";
      public static final String POS_TAGS = "pos_tags";

      private Fields() {

      }
   }
}
