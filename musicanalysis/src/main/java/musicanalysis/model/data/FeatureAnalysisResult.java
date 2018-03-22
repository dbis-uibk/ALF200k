package musicanalysis.model.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "featureanalysisresults")
public class FeatureAnalysisResult {

   @Id
   @Field(Fields.ID)
   private ObjectId id;
   @Field(Fields.TEST_CASE)
   private ObjectId testCaseId;
   @Field(Fields.MOST_DISCRIMINATIVE_FEATURES)
   private List<FeatureInformationGain> featureInformationGains;
   @Field(Fields.CONFIG)
   private Integer config;

   public ObjectId getId() {
      return id;
   }

   public void setId(ObjectId id) {
      this.id = id;
   }

   public ObjectId getTestCaseId() {
      return testCaseId;
   }

   public void setTestCaseId(ObjectId testCaseId) {
      this.testCaseId = testCaseId;
   }

   public List<FeatureInformationGain> getFeatureInformationGains() {
      return featureInformationGains;
   }

   public void setFeatureInformationGains(List<FeatureInformationGain> featureInformationGains) {
      this.featureInformationGains = featureInformationGains;
   }

   public Integer getConfig() {
      return config;
   }

   public void setConfig(Integer config) {
      this.config = config;
   }

   public class Fields {

      public static final String ID = "_id";
      public static final String TEST_CASE = "test_case";
      public static final String MOST_DISCRIMINATIVE_FEATURES = "most_discriminative_features";
      public static final String CONFIG = "config";

      private Fields() {

      }
   }
}
