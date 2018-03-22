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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * API description can be found at http://www.lyricsnmusic.com/api.
 */
@Component
public final class LyricsNMusic extends ConcurrentLyricCrawler {

   public static final String NAME = "lyricsnmusic";

   private static final String API_KEY = "1c0143c7ae9d524be2dc375b4b84ed";
   private static final String URL = "http://api.lyricsnmusic.com/songs?api_key=" + API_KEY + "&artist=%s&track=%s";

   @Autowired
   public LyricsNMusic(MongoTemplate mongoTemplate) {

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
            final Pattern pattern = Pattern.compile("^\\[(.*)\\]$");
            final Matcher matcher = pattern.matcher(d.body().text());
            if (matcher.matches()) {
               final String content = matcher.group(1);
               if (!content.trim().isEmpty()) {
                  final JSONObject json = new JSONObject(content);
                  final String lyricURL = json.getString("url");
                  if (!lyricURL.trim().isEmpty()) {
                     final String text = extractLyric(lyricURL).trim();
                     lyric = new Lyric();
                     lyric.setUrl(lyricURL);
                     lyric.setTrackId(track.getId());
                     lyric.setText(text);
                     lyric.setLanguage(LanguageDetector.detect(text));
                  }
               }
            }
         }
      }

      return lyric;
   }

   private String extractLyric(final String url)
         throws IOException {

      final org.jsoup.nodes.Document d = Jsoup.parse(executeGetRequest(url));
      return preserveNewlines(d.select("pre[itemprop=\"description\"]").html());
   }
}
