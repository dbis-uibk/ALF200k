package musicanalysis.model.music.lyric.feature.semantic;

import musicanalysis.model.common.Path;
import musicanalysis.model.settings.Settings;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

class OpinionLexicon {

   private final Set<String> negativeWords;
   private final Set<String> positiveWords;

   private OpinionLexicon() {

      negativeWords = new HashSet<>();
      positiveWords = new HashSet<>();

      initialize();
   }

   public static OpinionLexicon getInstance() {

      return OpinionLexicon.InstanceHolder.INSTANCE;
   }

   private void initialize() {

      try {
         readFile(Settings.OPINION_LEXICON_NEGATIVE_WORDS_PATH, negativeWords);
         readFile(Settings.OPINION_LEXICON_POSITIVE_WORDS_PATH, positiveWords);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   private void readFile(final String path, final Set<String> set)
         throws IOException {

      FileUtils.readLines(new File(Path.getInstance().getResourcePath(path)), Charset.forName("UTF-8")).forEach(l -> {
         final String value = l.toLowerCase().trim();
         if (!value.isEmpty()) {
            set.add(value);
         }
      });
   }

   public int get(final String word) {

      final String key = word.toLowerCase().trim();

      if (negativeWords.contains(key)) {
         return -1;
      } else if (positiveWords.contains(key)) {
         return 1;
      } else {
         return 0;
      }
   }

   private static class InstanceHolder {

      static final OpinionLexicon INSTANCE = new OpinionLexicon();
   }
}
