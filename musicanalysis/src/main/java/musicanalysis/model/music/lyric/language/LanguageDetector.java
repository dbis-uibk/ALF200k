package musicanalysis.model.music.lyric.language;

import org.apache.tika.language.LanguageIdentifier;

public class LanguageDetector {

   public static String detect(final String text) {

      final LanguageIdentifier identifier = new LanguageIdentifier(text);
      return identifier.getLanguage();
   }
}
