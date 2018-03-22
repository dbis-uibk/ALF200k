package musicanalysis.model.music.lyric.language;

public class LanguageCount {

   private String language;

   private long total;

   public LanguageCount() {

   }

   public LanguageCount(final String language, final long total) {

      this.language = language;
      this.total = total;
   }

   public String getLanguage() {

      return language;
   }

   public void setLanguage(String language) {

      this.language = language;
   }

   public long getTotal() {

      return total;
   }

   public void setTotal(long total) {

      this.total = total;
   }
}
