package musicanalysis.model.music.lyric.feature.linguistic;

import backend.*;
import musicanalysis.model.data.Rhyme;
import musicanalysis.model.music.lyric.feature.PreparedLyric;
import musicanalysis.model.nlp.Line;
import rhymeapp.mainUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RhymesEvaluator {

   private final PreparedLyric lyric;

   public RhymesEvaluator(final PreparedLyric lyric) {

      this.lyric = lyric;
   }

   public Rhyme evaluate() {

      Rhyme rhyme = null;

      try {
         final List<Line> lines = lyric.getLines();
         if (lines.size() > 1) {

            final Transcriptor tr = new Transcriptor();
            final Stats st = new Stats(mainUI.STATS_FILE);
            final Scoring sc = new Scoring(st, Stats.SPLIT);
            final Detector det = new Detector(sc);

            final ArrayList<PLine> inLines = new ArrayList<PLine>();
            lines.forEach(l -> inLines.add(tr.transcribe(l.getText())));

            final Analyzer an = new Analyzer("Input Lines", sc);
            final RhymeCollection rc = det.getRhymes(inLines);
            rc.lines = inLines;
            if (an.addRhymes(rc)) {
               Analysis analysis = an.createAnalysis();
               rhyme = new Rhyme();
               rhyme.setSyllablesPerLine(analysis == null ? 0.0 : analysis.getStat(Analyzer.SYLLABLES_PER_LINE));
               rhyme.setSyllablesPerWord(analysis == null ? 0.0 : analysis.getStat(Analyzer.SYLLABLES_PER_WORD));
               rhyme.setSyllableVariation(analysis == null ? 0.0 : analysis.getStat(Analyzer.SYLLABLE_VARIATION));
               rhyme.setNovelWordProportion(analysis == null ? 0.0 : analysis.getStat(Analyzer.NOVEL_WORD_PROPORTION));
               rhyme.setRhymesPerLine(analysis == null ? 0.0 : analysis.getStat(Analyzer.RHYMES_PER_LINE));
               rhyme.setRhymesPerSyllable(analysis == null ? 0.0 : analysis.getStat(Analyzer.RHYMES_PER_SYLLABLE));
               rhyme.setRhymeDensity(analysis == null ? 0.0 : analysis.getStat(Analyzer.RHYME_DENSITY));
               rhyme.setEndPairsPerLine(analysis == null ? 0.0 : analysis.getStat(Analyzer.END_PAIRS_PER_LINE));
               rhyme.setEndPairsGrown(analysis == null ? 0.0 : analysis.getStat(Analyzer.END_PAIRS_GROWN));
               rhyme.setEndPairsShrunk(analysis == null ? 0.0 : analysis.getStat(Analyzer.END_PAIRS_SHRUNK));
               rhyme.setEndPairsEven(analysis == null ? 0.0 : analysis.getStat(Analyzer.END_PAIRS_EVEN));
               rhyme.setAverageEndScore(analysis == null ? 0.0 : analysis.getStat(Analyzer.AVERAGE_END_SCORE));
               rhyme.setAverageEndSyllableScore(analysis == null ? 0.0 : analysis.getStat(Analyzer.AVERAGE_END_SYL_SCORE));
               rhyme.setSinglesPerRhyme(analysis == null ? 0.0 : analysis.getStat(Analyzer.SINGLES_PER_RHYME));
               rhyme.setDoublesPerRhyme(analysis == null ? 0.0 : analysis.getStat(Analyzer.DOUBLES_PER_RHYME));
               rhyme.setTriplesPerRhyme(analysis == null ? 0.0 : analysis.getStat(Analyzer.TRIPLES_PER_RHYME));
               rhyme.setQuadsPerRhyme(analysis == null ? 0.0 : analysis.getStat(Analyzer.QUADS_PER_RHYME));
               rhyme.setLongsPerRhyme(analysis == null ? 0.0 : analysis.getStat(Analyzer.LONGS_PER_RHYME));
               rhyme.setPerfectRhymes(analysis == null ? 0.0 : analysis.getStat(Analyzer.PERFECT_RHYMES));
               rhyme.setLineInternalsPerLine(analysis == null ? 0.0 : analysis.getStat(Analyzer.LINE_INTERNALS_PER_LINE));
               rhyme.setLinksPerLine(analysis == null ? 0.0 : analysis.getStat(Analyzer.LINKS_PER_LINE));
               rhyme.setBridgesPerLine(analysis == null ? 0.0 : analysis.getStat(Analyzer.BRIDGES_PER_LINE));
               rhyme.setCompoundsPerLine(analysis == null ? 0.0 : analysis.getStat(Analyzer.COMPOUNDS_PER_LINE));
               rhyme.setChainingPerLine(analysis == null ? 0.0 : analysis.getStat(Analyzer.CHAINING_PER_LINE));
               rhyme.setLyricId(lyric.getId());
            }
         }
      } catch (IOException ex) {
         // should not happen
      }

      return rhyme;
   }
}
