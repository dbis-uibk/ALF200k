package musicanalysis.model.music.lyric.crawler;

import musicanalysis.model.data.Lyric;
import musicanalysis.model.data.Track;
import musicanalysis.model.music.lyric.language.LanguageDetector;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public final class ELyrics extends SerialLyricCrawler {

   public static final String NAME = "elyrics";
   private static final String BASE_URL = "http://www.elyrics.net";
   private static final String URL = "http://www.elyrics.net/find.php";

   @Autowired
   public ELyrics(MongoTemplate mongoTemplate) {

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
         final List<NameValuePair> params = new ArrayList<>();
         params.add(new BasicNameValuePair("q", String.format("%s+%s", encodeURL(artist), encodeURL(name))));
         final org.jsoup.nodes.Document d = Jsoup.parse(executePostRequest(URL, params));
         final Element link = d.select("main.inner_right").select("a").first();
         if (link != null) {
            final String lyricURL = BASE_URL + encodePath(link.attr("href"));
            final String text = extractLyric(lyricURL).trim();
            lyric = new Lyric();
            lyric.setUrl(lyricURL);
            lyric.setTrackId(track.getId());
            lyric.setText(text);
            lyric.setLanguage(LanguageDetector.detect(text));
         }
      }

      return lyric;
   }

   private String extractLyric(final String url)
         throws IOException {

      final org.jsoup.nodes.Document d = Jsoup.parse(executeGetRequest(url));
      final String lyrics = d.select("div#inlyr").html();
      int index = lyrics.indexOf("<div class=\"read3\">");
      if (index < 0) {
         index = lyrics.length() - 1;
      }
      return preserveNewlines(lyrics.substring(0, index));
   }
}