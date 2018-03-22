package musicanalysis.model.music.lyric.feature;

import musicanalysis.model.data.LyricFeature;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class FeatureLoader {

   private final MongoTemplate mongoTemplate;

   public FeatureLoader(final MongoTemplate mongoTemplate) {

      this.mongoTemplate = mongoTemplate;
   }

   public <T> T load(final Class<T> type, final ObjectId lyricId) {

      return mongoTemplate.findOne(Query.query(Criteria.where(LyricFeature.Fields.LYRIC_ID).is(lyricId)), type);
   }
}
