package musicanalysis.model.common;

import java.util.Collections;
import java.util.List;

public class Math {

   private Math() {

   }

   public static float median(final List<Integer> m) {

      Collections.sort(m);

      int middle = m.size() / 2;
      if (m.size() % 2 == 1) {
         return m.get(middle);
      } else {
         return (m.get(middle - 1) + m.get(middle)) / 2.0f;
      }
   }
}
