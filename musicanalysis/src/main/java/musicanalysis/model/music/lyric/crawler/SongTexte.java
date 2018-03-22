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

/**
 * songtexte.com does not offer any public API.
 */
@Component
public final class SongTexte extends SerialLyricCrawler {

   public static final String NAME = "songtexte";
   private static final String BASE_URL = "http://www.songtexte.com/";
   private static final String URL = "http://www.songtexte.com/search?q=%s&c=songs";

   @Autowired
   public SongTexte(MongoTemplate mongoTemplate) {

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
            final Element songResults = d.select("div.songResultTable").first();
            final Element link = songResults.select("span.song > a").first();
            if (link != null) {
               final String lyricURL = BASE_URL + link.attr("href");
               final String text = extractLyric(lyricURL).trim();
               lyric = new Lyric();
               lyric.setUrl(lyricURL);
               lyric.setTrackId(track.getId());
               lyric.setText(text.equalsIgnoreCase("Leider kein Songtext vorahnden.") ? "" : text);
               lyric.setLanguage(LanguageDetector.detect(text));
            }
         }
      }

      return lyric;
   }

   private String extractLyric(final String url)
         throws IOException {

      final org.jsoup.nodes.Document d = Jsoup.parse(executeGetRequest(url));
      return preserveNewlines(d.select("div#lyrics").html());
   }
}