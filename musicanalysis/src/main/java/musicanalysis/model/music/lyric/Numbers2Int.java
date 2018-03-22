package musicanalysis.model.music.lyric;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Numbers2Int {

   private static Map<String, Integer> numbers;

   static {
      numbers = new HashMap<>();
      numbers.put("one", 1);
      numbers.put("once", 1);
      numbers.put("two", 2);
      numbers.put("twice", 2);
      numbers.put("three", 3);
      numbers.put("thrice", 3);
      numbers.put("four", 4);
      numbers.put("five", 5);
      numbers.put("six", 6);
      numbers.put("seven", 7);
      numbers.put("eight", 8);
      numbers.put("nine", 9);
      numbers.put("ten", 10);
      numbers.put("eleven", 11);
      numbers.put("twelve", 12);
      numbers.put("thirteen", 13);
      numbers.put("fourteen", 14);
      numbers.put("fifteen", 15);
   }

   public static int parse(final String word) {

      final String key = word.trim().toLowerCase();
      if (!numbers.containsKey(key)) {
         throw new IllegalArgumentException();
      }

      return numbers.get(key);
   }

   public static Set<String> getWords() {

      return numbers.keySet();
   }
}
