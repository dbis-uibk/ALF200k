package musicanalysis.model.music.lyric;

import edu.stanford.nlp.util.Sets;
import musicanalysis.model.common.NLP;
import musicanalysis.model.data.Lyric;
import musicanalysis.model.music.lyric.feature.PreparedLyric;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class LyricsPicker {

   private LyricsPicker() {

   }

   public static LyricsPicker getInstance() {

      return LyricsPicker.InstanceHolder.INSTANCE;
   }

   private double calculateSimilarity(final Lyric l1, final Lyric l2) {

      if (l1 == null || l2 == null) {
         throw new IllegalArgumentException("Arguments can not be null");
      }

      final List<String> tokens1 = new ArrayList<>();
      final List<String> tokens2 = new ArrayList<>();
      new PreparedLyric(l1).getTokens().forEach(t -> tokens1.add(t.word().toLowerCase()));
      new PreparedLyric(l2).getTokens().forEach(t -> tokens2.add(t.word().toLowerCase()));

      final List<List<String>> grams1 = NLP.createShingles(tokens1, 2);
      final List<List<String>> grams2 = NLP.createShingles(tokens2, 2);

      final HashSet<String> set1 = new HashSet<>();
      final HashSet<String> set2 = new HashSet<>();
      set1.addAll(grams1.stream().map(l -> l.stream().collect(Collectors.joining(" "))).collect(Collectors.toList()));
      set2.addAll(grams2.stream().map(l -> l.stream().collect(Collectors.joining(" "))).collect(Collectors.toList()));

      return Sets.intersection(set1, set2).size() / (double) Sets.union(set1, set2).size();
   }

   public double[][] createAdjacencyMatrix(final List<Lyric> lyrics) {

      final double[][] adjacencyMatrix = new double[lyrics.size()][lyrics.size()];

      for (int i = 0; i < lyrics.size(); i++) {
         for (int j = i + 1; j < lyrics.size(); j++) {
            final Lyric l1 = lyrics.get(i);
            final Lyric l2 = lyrics.get(j);
            adjacencyMatrix[i][j] = adjacencyMatrix[j][i] = calculateSimilarity(l1, l2);
         }
      }

      return adjacencyMatrix;
   }

   public Lyric majorityVote(final List<Lyric> lyrics, final float minSimilarityThreshold, final int minAmountAboveThreshold) {

      Lyric bestVoted = null;

      if (lyrics == null || lyrics.isEmpty()) {
         throw new IllegalArgumentException("Lyrics can not be null or empty");
      }

      final double[][] adjacencyMatrix = createAdjacencyMatrix(lyrics);
      final int lyricIndex = majorityVote(adjacencyMatrix, minSimilarityThreshold, minAmountAboveThreshold);

      if (lyricIndex > -1) {
         bestVoted = lyrics.get(lyricIndex);
      }

      return bestVoted;
   }

   private int majorityVote(double[][] adjacencyMatrix, final float minSimilarityThreshold, final int minAmountAboveThreshold) {

      int lyricIndex = -1;
      float maxSimilarity = 0.0f;

      for (int i = 0; i < adjacencyMatrix.length; i++) {

         int aboveThreshold = 0;
         float currentSimilarity = 0.0f;
         for (int j = 0; j < adjacencyMatrix[i].length; j++) {
            if (adjacencyMatrix[i][j] > minSimilarityThreshold) {
               aboveThreshold++;
               currentSimilarity += adjacencyMatrix[i][j];
            }
         }

         if (aboveThreshold >= minAmountAboveThreshold && currentSimilarity > maxSimilarity) {
            maxSimilarity = currentSimilarity;
            lyricIndex = i;
         }
      }

      return lyricIndex;
   }

   private static class InstanceHolder {

      static final LyricsPicker INSTANCE = new LyricsPicker();
   }
}
