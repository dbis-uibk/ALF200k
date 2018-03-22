package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "chunktags")
public class SuperChunkTags extends LyricFeature {

   @Field(Fields.NOUN_PHRASES_RATIO)
   private float nounPhrasesRatio;
   @Field(Fields.VERB_PHRASES_RATIO)
   private float verbPhrasesRatio;
   @Field(Fields.PREPOSITIONAL_PHRASES_RATIO)
   private float prepositionalPhrasesRatio;
   @Field(Fields.ADJECTIVE_ADVERB_PHRASES_RATIO)
   private float adjectiveAndAdverbPhrasesRatio;
   @Field(Fields.SPECIAL_PHRASES_RATIO)
   private float specialPhrasesRatio;
   @Field(Fields.TAGS)
   private List<ChunkTag> tags;

   public SuperChunkTags() {

      tags = new ArrayList<>();
   }

   public float getNounPhrasesRatio() {

      return nounPhrasesRatio;
   }

   public void setNounPhrasesRatio(float nounPhrasesRatio) {

      this.nounPhrasesRatio = nounPhrasesRatio;
   }

   public float getVerbPhrasesRatio() {

      return verbPhrasesRatio;
   }

   public void setVerbPhrasesRatio(float verbPhrasesRatio) {

      this.verbPhrasesRatio = verbPhrasesRatio;
   }

   public float getPrepositionalPhrasesRatio() {

      return prepositionalPhrasesRatio;
   }

   public void setPrepositionalPhrasesRatio(float prepositionalPhrasesRatio) {

      this.prepositionalPhrasesRatio = prepositionalPhrasesRatio;
   }

   public float getAdjectiveAndAdverbPhrasesRatio() {

      return adjectiveAndAdverbPhrasesRatio;
   }

   public void setAdjectiveAndAdverbPhrasesRatio(float adjectiveAndAdverbPhrasesRatio) {

      this.adjectiveAndAdverbPhrasesRatio = adjectiveAndAdverbPhrasesRatio;
   }

   public float getSpecialPhrasesRatio() {

      return specialPhrasesRatio;
   }

   public void setSpecialPhrasesRatio(float specialPhrasesRatio) {

      this.specialPhrasesRatio = specialPhrasesRatio;
   }

   public List<ChunkTag> getTags() {

      return tags;
   }

   public void addTag(ChunkTag tag) {

      tags.add(tag);
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.VERB_PHRASES_RATIO, verbPhrasesRatio);
      update.set(Fields.NOUN_PHRASES_RATIO, nounPhrasesRatio);
      update.set(Fields.PREPOSITIONAL_PHRASES_RATIO, prepositionalPhrasesRatio);
      update.set(Fields.ADJECTIVE_ADVERB_PHRASES_RATIO, adjectiveAndAdverbPhrasesRatio);
      update.set(Fields.SPECIAL_PHRASES_RATIO, specialPhrasesRatio);
      update.set(Fields.TAGS, tags);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String VERB_PHRASES_RATIO = "verb_phrases_ratio";
      public static final String NOUN_PHRASES_RATIO = "noun_phrases_ratio";
      public static final String PREPOSITIONAL_PHRASES_RATIO = "prepositional_phrases_ratio";
      public static final String ADJECTIVE_ADVERB_PHRASES_RATIO = "adjective_adverb_phrases_ratio";
      public static final String SPECIAL_PHRASES_RATIO = "special_phrases_ratio";
      public static final String TAGS = "tags";

      private Fields() {

      }
   }
}
