package musicanalysis.model.common;

import java.text.SimpleDateFormat;

public class Date {

   private Date() {

   }

   public static SimpleDateFormat getDateTimeFormat() {

      return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
   }
}
