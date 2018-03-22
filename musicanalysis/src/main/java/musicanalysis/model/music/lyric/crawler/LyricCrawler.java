package musicanalysis.model.music.lyric.crawler;

import musicanalysis.model.Task;
import musicanalysis.model.data.Lyric;
import musicanalysis.model.data.Track;
import musicanalysis.model.net.HttpRequester;
import org.apache.http.NameValuePair;
import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class LyricCrawler extends Task {

   protected static final String USER_AGENT = "Mozilla/5.0";
   private final String trackCollectionName = "tracks";
   private TrackFetchMode trackFetchMode;

   protected LyricCrawler() {

      trackFetchMode = TrackFetchMode.All;
   }

   private String getTrackCollectionName() {

      return trackCollectionName;
   }

   protected String encodeURL(final String value)
         throws UnsupportedEncodingException {

      return musicanalysis.model.common.Net.encodeURL(value);
   }

   protected String encodePath(final String value)
         throws UnsupportedEncodingException {

      return musicanalysis.model.common.Net.encodePath(value);
   }

   protected String preserveNewlines(final String html) {

      if (html == null) return null;
      org.jsoup.nodes.Document document = Jsoup.parse(html.replaceAll("(\n<br>|<br>\n)", "\n").replaceAll("<br>", "\n"));
      document.outputSettings(new org.jsoup.nodes.Document.OutputSettings().prettyPrint(false));
      return Jsoup.clean(document.html(), "", Whitelist.none(), new org.jsoup.nodes.Document.OutputSettings().prettyPrint(false));
   }

   public long getLyricAmount() {

      return getDatabaseConnection().getCollection(getName()).count();
   }

   protected List<Track> fetchTracks(TrackFetchMode option) {

      final List<Track> tracks = getDatabaseConnection().findAll(Track.class, getTrackCollectionName());

      if (!TrackFetchMode.All.equals(option)) {
         final Map<ObjectId, Track> indexedTracks = new HashMap<>();
         tracks.forEach(t -> indexedTracks.put(t.getId(), t));
         final List<Lyric> lyrics = getDatabaseConnection().findAll(Lyric.class, getName());
         final List<Lyric> filterdLyrics = lyrics.stream().filter(l -> !l.getText().trim().isEmpty()).collect(Collectors.toList());

         if (TrackFetchMode.TracksWithoutLyrics.equals(option)) {
            filterdLyrics.forEach(l -> {
               if (indexedTracks.containsKey(l.getTrackId())) {
                  tracks.remove(indexedTracks.remove(l.getTrackId()));
               }
            });
         } else if (TrackFetchMode.TracksWithLyrics.equals(option)) {
            tracks.clear();
            filterdLyrics.forEach(l -> {
               if (indexedTracks.containsKey(l.getTrackId())) {
                  tracks.add(indexedTracks.get(l.getTrackId()));
               }
            });
         }
      }

      return tracks;
   }

   protected void store(final Lyric lyric) {

      if (lyric != null && !lyric.getText().trim().isEmpty()) {
         // final Query query = new Query();
         // query.addCriteria(Criteria.where(Lyric.Fields.TRACK_ID).is(lyric.getTrackId()));
         // getDatabaseConnection().upsert(query, lyric.toUpdate(), getName());
         getDatabaseConnection().insert(lyric, getName());
      }
   }

   @Override
   protected void execute() {

      requestLyrics();
   }

   @Override
   protected String getTaskName() {

      return getName();
   }

   protected String executeGetRequest(final String url)
         throws IOException {

      return HttpRequester.get(url);
   }

   protected String executeGetRequest(final String url, final String agent)
         throws IOException {

      return HttpRequester.get(url, agent);
   }

   protected String executePostRequest(final String url, final List<NameValuePair> data)
         throws IOException {

      return HttpRequester.post(url, data);
   }

   protected abstract void requestLyrics();

   public abstract String getName();

   public TrackFetchMode getTrackFetchMode() {

      return trackFetchMode;
   }

   public void setTrackFetchMode(TrackFetchMode option) {

      this.trackFetchMode = option;
   }

   public enum TrackFetchMode {
      All,
      TracksWithLyrics,
      TracksWithoutLyrics
   }
}
