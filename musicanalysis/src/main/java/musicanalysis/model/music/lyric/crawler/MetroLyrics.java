package musicanalysis.model.music.lyric.crawler;

import musicanalysis.model.data.Lyric;
import musicanalysis.model.data.Track;
import musicanalysis.model.music.lyric.language.LanguageDetector;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * MetroLyrics.com does not offer any public API.
 */
@Component
public final class MetroLyrics extends ConcurrentLyricCrawler {

   public static final String NAME = "metrolyrics";
   private static final String BASE_URL = "http://www.metrolyrics.com/";
   private static final String URL = "http://api.metrolyrics.com/v1//multisearch/all/X-API-KEY/196f657a46afb63ce3fd2015b9ed781280337ea7/format/json?find=%s";

   @Autowired
   public MetroLyrics(MongoTemplate mongoTemplate) {

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
         final String song = String.format(URL, encodeURL(artist + " - " + name));
         final org.jsoup.nodes.Document d = Jsoup.parse(executeGetRequest(song));
         if (d != null) {
            final JSONObject json = new JSONObject(d.body().text());
            final JSONObject results = json.getJSONObject("results");
            final JSONArray songs = results.getJSONObject("songs").getJSONArray("d");
            if (songs.length() > 0) {
               final String lyricURL = BASE_URL + songs.getJSONObject(0).getString("u");
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

      final org.jsoup.nodes.Document d = Jsoup.parse(executeGetRequest(url));

      String text = d.select("div#lyrics-body-text").html();
      text = text.replace("</p>", "");
      text = text.replace("<p class=\"verse\">", "\n");
      text = text.replaceAll("<br>\\s*", "\n");
      text = text.trim();

      return preserveNewlines(text);
   }
}
