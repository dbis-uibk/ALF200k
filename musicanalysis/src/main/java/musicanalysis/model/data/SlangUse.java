package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "slanguses")
public class SlangUse extends LyricFeature {

   @Field(Fields.UNCOMMON_WORDS)
   private Float uncommonWordsRatio;
   @Field(Fields.SLANG_WORDS)
   private Float slangWordsRatio;
   @Field(Fields.UNIQUE_UNCOMMON_WORDS)
   private Float uniqueUncommonWordsRatio;
   @Field(Fields.LEMMAS)
   private Float lemmasRatio;

   public Float getUncommonWordsRatio() {

      return uncommonWordsRatio;
   }

   public void setUncommonWordsRatio(Float uncommonWordsRatio) {

      this.uncommonWordsRatio = uncommonWordsRatio;
   }

   public Float getSlangWordsRatio() {

      return slangWordsRatio;
   }

   public void setSlangWordsRatio(Float slangWordsRatio) {

      this.slangWordsRatio = slangWordsRatio;
   }

   public Float getUniqueUncommonWordsRatio() {
      return uniqueUncommonWordsRatio;
   }

   public void setUniqueUncommonWordsRatio(Float uniqueUncommonWordsRatio) {
      this.uniqueUncommonWordsRatio = uniqueUncommonWordsRatio;
   }

   public Float getLemmasRatio() {
      return lemmasRatio;
   }

   public void setLemmasRatio(Float lemmasRatio) {
      this.lemmasRatio = lemmasRatio;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.UNCOMMON_WORDS, uncommonWordsRatio);
      update.set(Fields.SLANG_WORDS, slangWordsRatio);
      update.set(Fields.UNIQUE_UNCOMMON_WORDS, uniqueUncommonWordsRatio);
      update.set(Fields.LEMMAS, lemmasRatio);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String UNCOMMON_WORDS = "uncommon_words_ratio";
      public static final String SLANG_WORDS = "slang_words_ratio";
      public static final String UNIQUE_UNCOMMON_WORDS = "unique_uncommon_words_ratio";
      public static final String LEMMAS = "lemmas_ratio";

      private Fields() {

      }
   }
}
