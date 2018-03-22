package musicanalysis.model.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "urbandictionary")
public class UrbanDictionary {

   @Id
   private ObjectId id;
   @Field(Fields.TOKEN)
   private String token;
   @Field(Fields.IS_PRESENT)
   private Boolean present;

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public Boolean getIsPresent() {
      return present;
   }

   public void setIsPresent(Boolean present) {
      this.present = present;
   }

   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.TOKEN, token);
      update.set(Fields.IS_PRESENT, present);

      return update;
   }

   public class Fields {

      public static final String TOKEN = "token";
      public static final String IS_PRESENT = "is_present";

      private Fields() {

      }
   }
}
