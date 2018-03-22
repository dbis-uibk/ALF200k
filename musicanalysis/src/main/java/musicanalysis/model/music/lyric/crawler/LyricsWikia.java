package musicanalysis.model.music.lyric.crawler;

import musicanalysis.model.data.Lyric;
import musicanalysis.model.data.Track;
import musicanalysis.model.music.lyric.language.LanguageDetector;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * API description can be found at http://lyrics.wikia.com/api.php.
 */
@Component
public final class LyricsWikia extends ConcurrentLyricCrawler {

   public static final String NAME = "lyricswikia";
   private static final String URL = "https://lyrics.wikia.com/api.php?action=lyrics&artist=%s&song=%s&fmt=json&func=getSong";

   @Autowired
   public LyricsWikia(MongoTemplate mongoTemplate) {

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
         final org.jsoup.nodes.Document d = Jsoup.parse(executeGetRequest(song));
         if (d != null) {
            final String message = d.body().text().substring("song = ".length());
            final JSONObject json = new JSONObject(message);
            final String lyricURL = json.getString("url");
            if (!lyricURL.contains("index.php")) {
               final String text = extractLyric(lyricURL).trim();
               final boolean instrumental = "instrumental".equalsIgnoreCase(text);
               lyric = new Lyric();
               lyric.setUrl(lyricURL);
               lyric.setTrackId(track.getId());
               lyric.setText(instrumental ? "" : text);
               lyric.setLanguage(LanguageDetector.detect(text));
            }
         }
      }

      return lyric;
   }

   private String extractLyric(final String url)
         throws IOException {

      final org.jsoup.nodes.Document d = Jsoup.parse(executeGetRequest(url));
      return preserveNewlines(d.select("div.lyricbox").html());
   }
}