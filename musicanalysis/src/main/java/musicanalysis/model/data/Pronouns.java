package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "pronouns")
public class Pronouns extends LyricFeature {

   @Field(Fields.I)
   private float i;
   @Field(Fields.YOU)
   private float you;
   @Field(Fields.IT)
   private float it;
   @Field(Fields.WE)
   private float we;
   @Field(Fields.THEY)
   private float they;
   @Field(Fields.I_VS_YOU)
   private float iVsYou;
   @Field(Fields.EXCENTRICITY)
   private float excentricity;

   public float getI() {

      return i;
   }

   public void setI(float i) {

      this.i = i;
   }

   public float getYou() {

      return you;
   }

   public void setYou(float you) {

      this.you = you;
   }

   public float getIt() {

      return it;
   }

   public void setIt(float it) {

      this.it = it;
   }

   public float getWe() {

      return we;
   }

   public void setWe(float we) {

      this.we = we;
   }

   public float getThey() {

      return they;
   }

   public void setThey(float they) {

      this.they = they;
   }

   public float getIVsYou() {
      return iVsYou;
   }

   public void setIVsYou(float iVsYou) {
      this.iVsYou = iVsYou;
   }

   public float getExcentricity() {
      return excentricity;
   }

   public void setExcentricity(float excentricity) {
      this.excentricity = excentricity;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.I, i);
      update.set(Fields.YOU, you);
      update.set(Fields.IT, it);
      update.set(Fields.WE, we);
      update.set(Fields.THEY, they);
      update.set(Fields.I_VS_YOU, iVsYou);
      update.set(Fields.EXCENTRICITY, excentricity);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String I = "i";
      public static final String YOU = "you";
      public static final String IT = "it";
      public static final String WE = "we";
      public static final String THEY = "they";
      public static final String I_VS_YOU = "i_vs_you";
      public static final String EXCENTRICITY = "excentricity";

      private Fields() {

      }
   }
}
