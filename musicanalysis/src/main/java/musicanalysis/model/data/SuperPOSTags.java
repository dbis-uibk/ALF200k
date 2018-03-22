package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "postags")
public class SuperPOSTags extends LyricFeature {

   @Field(Fields.VERBS)
   private float verbs;
   @Field(Fields.NOUNS)
   private float nouns;
   @Field(Fields.ADJECTIVES)
   private float adjectives;
   @Field(Fields.ADVERBS)
   private float adverbs;
   @Field(Fields.WH_QUESTIONS)
   private float whQuestions;
   @Field(Fields.SPECIAL_CHARACTERS)
   private float specialCharacters;
   @Field(Fields.TAGS)
   private List<POSTag> tags;

   public SuperPOSTags() {

      tags = new ArrayList<>();
   }

   public float getVerbs() {

      return verbs;
   }

   public void setVerbs(float verbs) {

      this.verbs = verbs;
   }

   public float getNouns() {

      return nouns;
   }

   public void setNouns(float nouns) {

      this.nouns = nouns;
   }

   public float getAdverbs() {

      return adverbs;
   }

   public void setAdverbs(float adverbs) {

      this.adverbs = adverbs;
   }

   public float getAdjectives() {

      return adjectives;
   }

   public void setAdjectives(float adjectives) {

      this.adjectives = adjectives;
   }

   public float getWhQuestions() {

      return whQuestions;
   }

   public void setWhQuestions(float whQuestions) {

      this.whQuestions = whQuestions;
   }

   public float getSpecialCharacters() {

      return specialCharacters;
   }

   public void setSpecialCharacters(float specialCharacters) {

      this.specialCharacters = specialCharacters;
   }

   public List<POSTag> getTags() {

      return tags;
   }

   public void addTag(POSTag tag) {

      tags.add(tag);
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.VERBS, verbs);
      update.set(Fields.NOUNS, nouns);
      update.set(Fields.ADJECTIVES, adjectives);
      update.set(Fields.ADVERBS, adverbs);
      update.set(Fields.WH_QUESTIONS, whQuestions);
      update.set(Fields.SPECIAL_CHARACTERS, specialCharacters);
      update.set(Fields.TAGS, tags);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String VERBS = "verbs";
      public static final String NOUNS = "nouns";
      public static final String ADJECTIVES = "adjectives";
      public static final String ADVERBS = "adverbs";
      public static final String WH_QUESTIONS = "wh_questions";
      public static final String SPECIAL_CHARACTERS = "special_characters";
      public static final String TAGS = "tags";

      private Fields() {

      }
   }
}
