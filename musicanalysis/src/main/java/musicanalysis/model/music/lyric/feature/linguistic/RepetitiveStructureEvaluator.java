package musicanalysis.model.music.lyric.feature.linguistic;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Sets;
import musicanalysis.model.common.NLP;
import musicanalysis.model.common.Pair;
import musicanalysis.model.data.RepetitiveStructure;
import musicanalysis.model.music.lyric.feature.PreparedLyric;
import musicanalysis.model.music.lyric.feature.Verse;
import musicanalysis.model.nlp.Line;

import java.util.*;
import java.util.stream.Collectors;

public class RepetitiveStructureEvaluator {

   private final PreparedLyric lyric;

   public RepetitiveStructureEvaluator(final PreparedLyric lyric) {

      this.lyric = lyric;
   }

   public RepetitiveStructure evaluate() {

      final RepetitiveStructure repetitiveStructure = new RepetitiveStructure();

      final List<Block> blocks = identifyRepetitiveBlocks();
      // consider only lines within a verse -> new lines are skipped
      final List<Line> lines = lyric.getVerses().stream().map(Verse::getLines).collect(ArrayList::new, List::addAll, List::addAll);
      final int totalLines = lines.size();
      final int repetitiveLines = blocks.stream().map(b -> b.lines.size()).reduce(0, (a, b) -> a + b);
      double alignmentScores = blocks.stream().map(block ->
            block.lines.stream().map(l -> l.similarity).reduce(0.0, (a, b) -> a + b)
      ).reduce(0.0, (a, b) -> a + b);

      repetitiveStructure.setLyricId(lyric.getId());
      repetitiveStructure.setBlockCount(blocks.size());
      repetitiveStructure.setAverageBlockSize(1.0f / blocks.size() * repetitiveLines);
      repetitiveStructure.setBlocksPerLine(blocks.size() / (float) totalLines);
      repetitiveStructure.setAverageAlignmentScore((float) alignmentScores / repetitiveLines);
      repetitiveStructure.setRepetitivity(repetitiveLines / (float) totalLines);

      final Set<String> repetitiveBlocks = new HashSet<>();
      blocks.forEach(b -> {
         final StringBuilder builder = new StringBuilder();
         b.lines.forEach(l -> builder.append(l.line.getText().toLowerCase()).append(System.lineSeparator()));
         repetitiveBlocks.add(builder.toString().trim());
      });

      repetitiveStructure.setBlockReduplication(repetitiveBlocks.size() * 1.0f / blocks.size());
      repetitiveStructure.setTypeTokenRatioLines(lines.stream().map(l -> l.getText().toLowerCase()).distinct().count() * 1.0f / totalLines);
      repetitiveStructure.setTypeTokenRatioInlines(1.0f / lines.size()
            * lines.stream()
            .map(l -> l.getTokens().stream().map(t -> t.lemma().toLowerCase()).distinct().count() * 1.0f / l.getTokens().size())
            .reduce(0.0f, (a, b) -> a + b));

      return repetitiveStructure;
   }

   private List<Block> identifyRepetitiveBlocks() {

      final List<Verse> verses = lyric.getVerses();
      final List<Pair<SimilarLine, SimilarLine>> similarLines = getSimilarLines(verses);
      final Set<Block> blocks = getBlocks(similarLines);
      final List<Block> sortedBlocks = new ArrayList<>(blocks);
      sortBlocksBySizeDescending(sortedBlocks);
      return removeDuplicateRepetitions(sortedBlocks);
   }

   private List<Pair<SimilarLine, SimilarLine>> getSimilarLines(final List<Verse> verses) {

      final List<Pair<SimilarLine, SimilarLine>> similarLines = new ArrayList<>();

      for (int i = 0; i < verses.size(); i++) {
         final Verse verse1 = verses.get(i);
         for (int j = i + 1; j < verses.size(); j++) {
            final Verse verse2 = verses.get(j);
            for (int k = 0; k < verse1.getLines().size(); k++) {
               final Line l1 = verse1.getLines().get(k);
               for (int l = 0; l < verse2.getLines().size(); l++) {
                  final Line l2 = verse2.getLines().get(l);
                  final double similarity = similarity(l1.getTokens(), l2.getTokens());
                  if (similarity >= 0.25) {
                     similarLines.add(new Pair<>(new SimilarLine(l1, i, k, similarity), new SimilarLine(l2, j, l, similarity)));
                     similarLines.add(new Pair<>(new SimilarLine(l2, j, l, similarity), new SimilarLine(l1, i, k, similarity)));
                  }
               }
            }
         }
      }

      return similarLines;
   }

   private Set<Block> getBlocks(final List<Pair<SimilarLine, SimilarLine>> similarLines) {

      final Set<Block> blocks = new HashSet<>();

      for (final Pair<SimilarLine, SimilarLine> pair : similarLines) {
         final Block block = new Block();
         block.lines.add(pair.getFirst());

         // search forward
         boolean similar = false;
         int line1 = pair.getFirst().lineIndex;
         int line2 = pair.getSecond().lineIndex;
         do {
            final SimilarLine s1 = pair.getFirst();
            final SimilarLine s2 = pair.getSecond();

            // create needle to search in haystack
            final Pair<SimilarLine, SimilarLine> nextPair = new Pair<>(
                  new SimilarLine(s1.verseIndex, ++line1),
                  new SimilarLine(s2.verseIndex, ++line2));

            similar = similarLines.contains(nextPair);
            if (similar) {
               block.lines.add(similarLines.get(similarLines.indexOf(nextPair)).getFirst());
            }
         } while (similar);

         // search backward
         line1 = pair.getFirst().lineIndex;
         line2 = pair.getSecond().lineIndex;
         do {
            final SimilarLine s1 = pair.getFirst();
            final SimilarLine s2 = pair.getSecond();

            // create needle to search in haystack
            final Pair<SimilarLine, SimilarLine> nextPair = new Pair<>(
                  new SimilarLine(s1.verseIndex, --line1),
                  new SimilarLine(s2.verseIndex, --line2));

            similar = similarLines.contains(nextPair);
            if (similar) {
               block.lines.add(0, similarLines.get(similarLines.indexOf(nextPair)).getFirst());
            }
         } while (similar);

         blocks.add(block);
      }

      return blocks;
   }

   private void sortBlocksBySizeDescending(final List<Block> blocks) {

      blocks.sort(Comparator.comparing((Block b) -> b.lines.size()).reversed());
   }

   private List<Block> removeDuplicateRepetitions(final List<Block> sortedBlocks) {

      List<Block> unique = new ArrayList<>();

      while (sortedBlocks.size() > 0) {
         final Block block = sortedBlocks.remove(0);
         if (block.lines.size() > 0) {
            unique.add(block);
            sortedBlocks.forEach(b -> block.lines.forEach(l -> b.lines.remove(l)));
         }
         // resort blocks by size
         sortBlocksBySizeDescending(sortedBlocks);
      }

      return unique;
   }

   private double similarity(final List<CoreLabel> x, final List<CoreLabel> y) {

      final double wordSimilarity = wordSimilarity(x, y);
      final double structSimilarity = structSimilarity(x, y);

      final double alpha = wordSimilarity;

      return alpha * wordSimilarity + (1 - alpha) * structSimilarity;
   }

   private double wordSimilarity(final List<CoreLabel> x, final List<CoreLabel> y) {

      final Set<String> ngramsX = new HashSet<>(NLP.createShingles(x, 2).stream()
            .map(l -> l.stream().map(CoreLabel::lemma).collect(Collectors.joining(" ")))
            .collect(Collectors.toList()));
      final Set<String> ngramsY = new HashSet<>(NLP.createShingles(y, 2).stream()
            .map(l -> l.stream().map(CoreLabel::lemma).collect(Collectors.joining(" ")))
            .collect(Collectors.toList()));

      return (Sets.intersection(ngramsX, ngramsY).size() * 1.0f) / Math.max(ngramsX.size(), ngramsY.size());
   }

   private double structSimilarity(final List<CoreLabel> x, final List<CoreLabel> y) {

      double similarity;

      final List<List<CoreLabel>> ngramsX = NLP.createShingles(x, 2);
      final List<List<CoreLabel>> ngramsY = NLP.createShingles(y, 2);

      final List<List<CoreLabel>> xPrime = new ArrayList<>();
      final List<List<CoreLabel>> yPrime = new ArrayList<>();

      ngramsX.forEach(t1 -> {
         boolean found = false;
         for (final List<CoreLabel> t2 : ngramsY) {
            if (t1.stream().map(CoreLabel::lemma).collect(Collectors.joining(" "))
                  .equals(t2.stream().map(CoreLabel::lemma).collect(Collectors.joining(" ")))) {
               found = true;
               break;
            }
         }
         if (!found) {
            xPrime.add(t1);
         }
      });

      ngramsY.forEach(t1 -> {
         boolean found = false;
         for (final List<CoreLabel> t2 : ngramsX) {
            if (t1.stream().map(CoreLabel::lemma).collect(Collectors.joining(" "))
                  .equals(t2.stream().map(CoreLabel::lemma).collect(Collectors.joining(" ")))) {
               found = true;
               break;
            }
         }
         if (!found) {
            yPrime.add(t1);
         }
      });

      final Set<String> ngramsXPrime = new HashSet<>(xPrime.stream()
            .map(l -> l.stream().map(CoreLabel::tag).collect(Collectors.joining(" ")))
            .collect(Collectors.toList()));
      final Set<String> ngramsYPrime = new HashSet<>(yPrime.stream()
            .map(l -> l.stream().map(CoreLabel::tag).collect(Collectors.joining(" ")))
            .collect(Collectors.toList()));

      if (ngramsXPrime.size() <= 0 && ngramsYPrime.size() <= 0) {
         similarity = 1.0;
      } else {
         similarity = Math
               .pow((Sets.intersection(ngramsXPrime, ngramsYPrime).size() * 1.0f) / Math.max(ngramsXPrime.size(), ngramsYPrime.size()),
                     2.0f);
      }

      return similarity;
   }

   private class SimilarLine {

      private Line line;
      private int verseIndex;
      private int lineIndex;
      private double similarity;

      SimilarLine(int verseIndex, int lineIndex) {
         this.verseIndex = verseIndex;
         this.lineIndex = lineIndex;
      }

      SimilarLine(Line line, int verseIndex, int lineIndex, double similarity) {
         this.line = line;
         this.verseIndex = verseIndex;
         this.lineIndex = lineIndex;
         this.similarity = similarity;
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;

         SimilarLine that = (SimilarLine) o;

         if (verseIndex != that.verseIndex) return false;
         return lineIndex == that.lineIndex;
      }

      @Override
      public int hashCode() {
         int result = verseIndex;
         result = 31 * result + lineIndex;
         return result;
      }

      @Override
      public String toString() {
         return "SimilarLine{" +
               "verseIndex=" + verseIndex +
               ", lineIndex=" + lineIndex +
               ", similarity=" + similarity +
               ", text=" + line.getText() +
               '}';
      }
   }

   private class Block {

      private List<SimilarLine> lines;

      Block() {
         lines = new ArrayList<>();
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;

         Block block = (Block) o;

         return lines != null ? lines.equals(block.lines) : block.lines == null;
      }

      @Override
      public int hashCode() {
         return lines != null ? lines.hashCode() : 0;
      }
   }
}
