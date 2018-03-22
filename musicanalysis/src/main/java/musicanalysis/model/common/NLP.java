package musicanalysis.model.common;

import java.util.ArrayList;
import java.util.List;

public class NLP {

   private NLP() {

   }

   public static <T> List<List<T>> createShingles(final List<T> objects, final int shingleSize) {

      final List<List<T>> shingles = new ArrayList<>();

      for (int i = 0; i <= objects.size() - shingleSize; i++) {
         final List<T> temp = new ArrayList<>();
         for (int j = 0; j < shingleSize; j++) {
            temp.add(objects.get(i + j));
         }
         shingles.add(temp);
      }

      return shingles;
   }
}
