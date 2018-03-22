package musicanalysis.model.music.lyric.feature.semantic;

import musicanalysis.model.common.Path;
import musicanalysis.model.settings.Settings;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

class AFINNDictionary {

   private final Map<String, Integer> dictionary;

   private AFINNDictionary() {

      dictionary = new HashMap<>();

      initialize();
   }

   public static AFINNDictionary getInstance() {

      return AFINNDictionary.InstanceHolder.INSTANCE;
   }

   private void initialize() {

      try {
         FileUtils.readLines(new File(Path.getInstance().getResourcePath(Settings.AFINN_PATH)), "UTF-8").forEach(l -> {
            final String[] values = l.split("\t");
            values[0] = values[0].toLowerCase().trim();
            if (!values[0].isEmpty()) {
               dictionary.put(values[0], Integer.parseInt(values[1]));
            }
         });
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public int get(final String word) {

      if (dictionary.containsKey(word.toLowerCase().trim())) {
         return dictionary.get(word.toLowerCase().trim());
      }
      return 0;
   }

   private static class InstanceHolder {

      static final AFINNDictionary INSTANCE = new AFINNDictionary();
   }
}
