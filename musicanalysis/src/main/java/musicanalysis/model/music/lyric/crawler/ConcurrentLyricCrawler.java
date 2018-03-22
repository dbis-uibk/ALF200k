package musicanalysis.model.music.lyric.crawler;

import musicanalysis.model.data.Lyric;
import musicanalysis.model.data.Track;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public abstract class ConcurrentLyricCrawler extends LyricCrawler {

   private final Queue<CompletableFuture<Void>> futures;

   private volatile AtomicLong downloads;
   private volatile AtomicLong totalTracks;

   private int maxThreads = 4;

   protected ConcurrentLyricCrawler() {

      super();
      futures = new LinkedBlockingQueue<>();
      downloads = new AtomicLong(0);
      totalTracks = new AtomicLong(0);
   }

   protected ConcurrentLyricCrawler(int maxThreads) {

      this();
      this.maxThreads = maxThreads;
   }

   @Override
   public final float calculateProgress() {

      float progress = 0.0f;

      if (totalTracks.get() > 0) {
         progress = downloads.get() * 100.0f / totalTracks.get();
      }

      return progress;
   }

   @Override
   public final void onCancellation() {

      futures.forEach(f -> {
         try {
            if (!f.isDone()) {
               f.cancel(true);
            }
         } catch (Exception ex) {
         }
      });
   }

   public final void requestLyrics() {

      final ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);
      final List<Track> tracks = fetchTracks(getTrackFetchMode());

      downloads.set(0);
      totalTracks.set(tracks.size());
      futures.clear();

      final Iterator<Track> iterator = tracks.iterator();
      while (iterator.hasNext() && !isCancelled()) {

         final Track track = iterator.next();
         final CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> this.load(track), executorService)
               .thenAcceptAsync(this::store).exceptionally(e -> {
                  if (!(e.getCause() instanceof CancellationException)) {
                     downloads.incrementAndGet();
                     logError(e.toString());
                  }
                  return null;
               });
         futures.add(future);
      }

      CompletableFuture<Void> future = null;
      while ((future = futures.poll()) != null) {
         try {
            future.join();
         } catch (Exception ex) {
         }
      }

      executorService.shutdownNow();
   }

   private Lyric load(final Track track) {

      if (isCancelled()) {
         throw new CompletionException(new CancellationException());
      }

      Lyric lyric = null;

      try {
         lyric = download(track);
      } catch (Exception e) {
         throw new CompletionException(e);
      }

      return lyric;
   }

   @Override
   protected final void store(final Lyric lyric) {

      final long docs = downloads.incrementAndGet();
      super.store(lyric);
      logInfo(String.format("%.2f%% - %d/%d tracks requested", ((docs * 1.0) / totalTracks.get()) * 100.0, docs, totalTracks.get()));
   }

   protected abstract Lyric download(final Track track) throws Exception;
}
