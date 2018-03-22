package musicanalysis.model.music.lyric.crawler;

import musicanalysis.model.data.Lyric;
import musicanalysis.model.data.Track;
import musicanalysis.model.music.lyric.language.LanguageDetector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public final class LyricsMode extends SerialLyricCrawler {

   public static final String NAME = "lyricsmode";
   private static final String BASE_URI = "http://www.lyricsmode.com";
   private static final String URL = BASE_URI + "/search.php?search=%s+%s";

   @Autowired
   public LyricsMode(MongoTemplate mongoTemplate) {

      this.initialize(mongoTemplate);
   }

   @Override
   public String getName() {

      return NAME;
   }

   @Override
   protected Lyric download(final Track track)
         throws IOException {

      Lyric lyric = null;

      final String name = track.getName();
      final String artist = track.getArtists().get(0).getName();
      if (name != null && artist != null && !name.isEmpty() && !artist.isEmpty()) {
         final String song = String.format(URL, encodeURL(artist), encodeURL(name));
         final org.jsoup.nodes.Document d = Jsoup.parse(executeGetRequest(song, USER_AGENT));
         if (d != null) {
            final Element link = d.select("table.songs_list").select("a[href$=.html]").first();
            if (link != null) {
               final String lyricURL = BASE_URI + link.attr("href");
               final String text = extractLyric(lyricURL).trim();
               lyric = new Lyric();
               lyric.setUrl(lyricURL);
               lyric.setTrackId(track.getId());
               lyric.setText(text);
               lyric.setLanguage(LanguageDetector.detect(text));
            }
         }
      }

      return lyric;
   }

   private String extractLyric(final String url)
         throws IOException {

      final org.jsoup.nodes.Document d = Jsoup.parse(executeGetRequest(url, USER_AGENT));
      return preserveNewlines(d.select("p#lyrics_text").html());
   }
}