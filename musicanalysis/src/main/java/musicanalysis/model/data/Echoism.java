package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "echoisms")
public class Echoism extends LyricFeature {

   @Field(Fields.WORD_ECHOISM_MUSICAL_WORDS)
   private Float wordEchoismMusicalWordsRatio;
   @Field(Fields.MULTI_ECHOISM_MUSICAL_WORDS_LENGTH_1)
   private Float multiEchoismMusicalWordsRatioLength1;
   @Field(Fields.MULTI_ECHOISM_REDUPLICATION_LENGTH_1)
   private Float multiEchoismReduplicationRatioLength1;
   @Field(Fields.MULTI_ECHOISM_RHYME_ALIKE_LENGTH_1)
   private Float multiEchoismRhymeAlikeRatioLength1;
   @Field(Fields.MULTI_ECHOISM_MUSICAL_WORDS_LENGTH_2)
   private Float multiEchoismMusicalWordsRatioLength2;
   @Field(Fields.MULTI_ECHOISM_REDUPLICATION_LENGTH_2)
   private Float multiEchoismReduplicationRatioLength2;
   @Field(Fields.MULTI_ECHOISM_RHYME_ALIKE_LENGTH_2)
   private Float multiEchoismRhymeAlikeRatioLength2;
   @Field(Fields.MULTI_ECHOISM_MUSICAL_WORDS_MIN_LENGTH_3)
   private Float multiEchoismMusicalWordsRatioMinLength3;
   @Field(Fields.MULTI_ECHOISM_REDUPLICATION_MIN_LENGTH_3)
   private Float multiEchoismReduplicationRatioMinLength3;
   @Field(Fields.MULTI_ECHOISM_RHYME_ALIKE_MIN_LENGTH_3)
   private Float multiEchoismRhymeAlikeRatioMinLength3;

   public Float getWordEchoismMusicalWordsRatio() {

      return wordEchoismMusicalWordsRatio;
   }

   public void setWordEchoismMusicalWordsRatio(Float value) {

      this.wordEchoismMusicalWordsRatio = value;
   }

   public Float getMultiEchoismMusicalWordsRatioLength1() {
      return multiEchoismMusicalWordsRatioLength1;
   }

   public void setMultiEchoismMusicalWordsRatioLength1(Float multiEchoismMusicalWordsRatioLength1) {
      this.multiEchoismMusicalWordsRatioLength1 = multiEchoismMusicalWordsRatioLength1;
   }

   public Float getMultiEchoismReduplicationRatioLength1() {
      return multiEchoismReduplicationRatioLength1;
   }

   public void setMultiEchoismReduplicationRatioLength1(Float multiEchoismReduplicationRatioLength1) {
      this.multiEchoismReduplicationRatioLength1 = multiEchoismReduplicationRatioLength1;
   }

   public Float getMultiEchoismRhymeAlikeRatioLength1() {
      return multiEchoismRhymeAlikeRatioLength1;
   }

   public void setMultiEchoismRhymeAlikeRatioLength1(Float multiEchoismRhymeAlikeRatioLength1) {
      this.multiEchoismRhymeAlikeRatioLength1 = multiEchoismRhymeAlikeRatioLength1;
   }

   public Float getMultiEchoismMusicalWordsRatioLength2() {
      return multiEchoismMusicalWordsRatioLength2;
   }

   public void setMultiEchoismMusicalWordsRatioLength2(Float multiEchoismMusicalWordsRatioLength2) {
      this.multiEchoismMusicalWordsRatioLength2 = multiEchoismMusicalWordsRatioLength2;
   }

   public Float getMultiEchoismReduplicationRatioLength2() {
      return multiEchoismReduplicationRatioLength2;
   }

   public void setMultiEchoismReduplicationRatioLength2(Float multiEchoismReduplicationRatioLength2) {
      this.multiEchoismReduplicationRatioLength2 = multiEchoismReduplicationRatioLength2;
   }

   public Float getMultiEchoismRhymeAlikeRatioLength2() {
      return multiEchoismRhymeAlikeRatioLength2;
   }

   public void setMultiEchoismRhymeAlikeRatioLength2(Float multiEchoismRhymeAlikeRatioLength2) {
      this.multiEchoismRhymeAlikeRatioLength2 = multiEchoismRhymeAlikeRatioLength2;
   }

   public Float getMultiEchoismMusicalWordsRatioMinLength3() {
      return multiEchoismMusicalWordsRatioMinLength3;
   }

   public void setMultiEchoismMusicalWordsRatioMinLength3(Float multiEchoismMusicalWordsRatioMinLength3) {
      this.multiEchoismMusicalWordsRatioMinLength3 = multiEchoismMusicalWordsRatioMinLength3;
   }

   public Float getMultiEchoismReduplicationRatioMinLength3() {
      return multiEchoismReduplicationRatioMinLength3;
   }

   public void setMultiEchoismReduplicationRatioMinLength3(Float multiEchoismReduplicationRatioMinLength3) {
      this.multiEchoismReduplicationRatioMinLength3 = multiEchoismReduplicationRatioMinLength3;
   }

   public Float getMultiEchoismRhymeAlikeRatioMinLength3() {
      return multiEchoismRhymeAlikeRatioMinLength3;
   }

   public void setMultiEchoismRhymeAlikeRatioMinLength3(Float multiEchoismRhymeAlikeRatioMinLength3) {
      this.multiEchoismRhymeAlikeRatioMinLength3 = multiEchoismRhymeAlikeRatioMinLength3;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.WORD_ECHOISM_MUSICAL_WORDS, wordEchoismMusicalWordsRatio);
      update.set(Fields.MULTI_ECHOISM_MUSICAL_WORDS_LENGTH_1, multiEchoismMusicalWordsRatioLength1);
      update.set(Fields.MULTI_ECHOISM_REDUPLICATION_LENGTH_1, multiEchoismReduplicationRatioLength1);
      update.set(Fields.MULTI_ECHOISM_RHYME_ALIKE_LENGTH_1, multiEchoismRhymeAlikeRatioLength1);
      update.set(Fields.MULTI_ECHOISM_MUSICAL_WORDS_LENGTH_2, multiEchoismMusicalWordsRatioLength2);
      update.set(Fields.MULTI_ECHOISM_REDUPLICATION_LENGTH_2, multiEchoismReduplicationRatioLength2);
      update.set(Fields.MULTI_ECHOISM_RHYME_ALIKE_LENGTH_2, multiEchoismRhymeAlikeRatioLength2);
      update.set(Fields.MULTI_ECHOISM_MUSICAL_WORDS_MIN_LENGTH_3, multiEchoismMusicalWordsRatioMinLength3);
      update.set(Fields.MULTI_ECHOISM_REDUPLICATION_MIN_LENGTH_3, multiEchoismReduplicationRatioMinLength3);
      update.set(Fields.MULTI_ECHOISM_RHYME_ALIKE_MIN_LENGTH_3, multiEchoismRhymeAlikeRatioMinLength3);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String WORD_ECHOISM_MUSICAL_WORDS = "word_echoism_musical_words";
      public static final String MULTI_ECHOISM_MUSICAL_WORDS_LENGTH_1 = "multi_echoism_musical_words_length_1";
      public static final String MULTI_ECHOISM_REDUPLICATION_LENGTH_1 = "multi_echoism_reduplication_length_1";
      public static final String MULTI_ECHOISM_RHYME_ALIKE_LENGTH_1 = "multi_echoism_rhyme_alike_length_1";
      public static final String MULTI_ECHOISM_MUSICAL_WORDS_LENGTH_2 = "multi_echoism_musical_words_length_2";
      public static final String MULTI_ECHOISM_REDUPLICATION_LENGTH_2 = "multi_echoism_reduplication_length_2";
      public static final String MULTI_ECHOISM_RHYME_ALIKE_LENGTH_2 = "multi_echoism_rhyme_alike_length_2";
      public static final String MULTI_ECHOISM_MUSICAL_WORDS_MIN_LENGTH_3 = "multi_echoism_musical_words_min_length_3";
      public static final String MULTI_ECHOISM_REDUPLICATION_MIN_LENGTH_3 = "multi_echoism_reduplication_min_length_3";
      public static final String MULTI_ECHOISM_RHYME_ALIKE_MIN_LENGTH_3 = "multi_echoism_rhyme_alike_min_length_3";

      private Fields() {

      }
   }
}
