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

@Component
public final class MP3Lyrics extends ConcurrentLyricCrawler {

   public static final String NAME = "mp3lyrics";
   private static final String SEARCH_URL = "http://mp3lyrics.com/?_return_json_=/Search/Advanced/?Track=%s&Artist=%s";
   private static final String LYRIC_URL = "http://mp3lyrics.com/?_return_json_=/Lyric/%d/";

   @Autowired
   public MP3Lyrics(MongoTemplate mongoTemplate) {

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
         final String song = String.format(SEARCH_URL, encodeURL(name), encodeURL(artist));
         final org.jsoup.nodes.Document d = Jsoup.parse(executeGetRequest(song));
         if (d != null) {
            final JSONObject json = new JSONObject(d.body().text());
            final JSONArray lyricIds = json.getJSONObject("SearchResult").getJSONArray("Tracks");
            if (lyricIds.length() > 0) {
               final Integer lyricId = lyricIds.getJSONObject(0).getInt("Id");
               final String lyricURL = String.format(LYRIC_URL, lyricId);
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
      final JSONObject json = new JSONObject(d.body().text());
      return preserveNewlines(json.getJSONObject("LyricModel").getString("LyricsText"));
   }
}