package musicanalysis.model.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "users")
public class User {

   @Id
   private ObjectId id;
   @Indexed
   private String name;

   public User() {

   }

   public User(final String name) {

      this.name = name;
   }

   public String getName() {

      return this.name;
   }

   public void setName(final String name) {

      this.name = name;
   }

   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.NAME, name);

      return update;
   }

   @Override
   public boolean equals(Object o) {

      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      User user = (User) o;

      return name.equals(user.name);

   }

   @Override
   public int hashCode() {

      return name.hashCode();
   }

   @Override
   public String toString() {

      return "User{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
   }

   public class Fields {

      public static final String NAME = "name";

      private Fields() {

      }
   }
}