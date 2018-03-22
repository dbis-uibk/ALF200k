package musicanalysis.model.music.lyric.crawler;

import musicanalysis.model.data.Lyric;
import musicanalysis.model.data.Track;

import java.util.Iterator;
import java.util.List;

public abstract class SerialLyricCrawler extends LyricCrawler {

   private volatile long downloads;
   private volatile long totalTracks;

   public SerialLyricCrawler() {

      super();

      downloads = 0;
      totalTracks = -1;
   }

   @Override
   public final float calculateProgress() {

      float progress = 0.0f;

      if (totalTracks > 0) {
         progress = downloads * 100.0f / totalTracks;
      }

      return progress;
   }

   public final void requestLyrics() {

      final List<Track> tracks = fetchTracks(getTrackFetchMode());

      downloads = 0;
      totalTracks = tracks.size();

      // it's not possible to open multiple connections
      final Iterator<Track> iterator = tracks.iterator();
      while (iterator.hasNext() && !isCancelled()) {

         final Track track = iterator.next();
         try {
            store(download(track));
         } catch (Exception e) {
            downloads++;
            logError(e.toString());
         } finally {
            try {
               Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
         }
      }
   }

   @Override
   protected final void store(final Lyric lyric) {

      long docs = ++downloads;
      super.store(lyric);
      logInfo(String.format("%.2f%% - %d/%d tracks requested", ((docs * 1.0) / totalTracks) * 100.0, docs, totalTracks));
   }

   protected abstract Lyric download(final Track track) throws Exception;
}
