package musicanalysis.model.nlp;

import musicanalysis.model.common.Path;
import musicanalysis.model.settings.Settings;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

public class Stopwords {

   private final Set<String> stopwords;

   private Stopwords() {

      stopwords = new HashSet<>();
      try {
         FileUtils.readLines(new File(Path.getInstance().getResourcePath(Settings.STOPWORDS_LIST_PATH)), Charset.forName("UTF-8")).forEach(l -> {
            final String stopword = l.toLowerCase().trim();
            if (!stopword.isEmpty()) {
               stopwords.add(stopword);
            }
         });
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public static Stopwords getInstance() {

      return Stopwords.InstanceHolder.INSTANCE;
   }

   public boolean isStopword(final String word) {

      return stopwords.contains(word.toLowerCase());
   }

   private static class InstanceHolder {

      static final Stopwords INSTANCE = new Stopwords();
   }
}
