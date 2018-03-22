package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "rhymes")
public class Rhyme extends LyricFeature {

   @Field(Fields.SYLLABLES_PER_LINE)
   private Double syllablesPerLine;
   @Field(Fields.SYLLABLES_PER_WORD)
   private Double syllablesPerWord;
   @Field(Fields.SYLLABLE_VARIATION)
   private Double syllableVariation;
   @Field(Fields.NOVEL_WORD_PROPORTION)
   private Double novelWordProportion;
   @Field(Fields.RHYMES_PER_LINE)
   private Double rhymesPerLine;
   @Field(Fields.RHYMES_PER_SYLLABLE)
   private Double rhymesPerSyllable;
   @Field(Fields.RHYME_DENSITY)
   private Double rhymeDensity;
   @Field(Fields.END_PAIRS_PER_LINE)
   private Double endPairsPerLine;
   @Field(Fields.END_PAIRS_GROWN)
   private Double endPairsGrown;
   @Field(Fields.END_PAIRS_SHRUNK)
   private Double endPairsShrunk;
   @Field(Fields.END_PAIRS_EVEN)
   private Double endPairsEven;
   @Field(Fields.AVERAGE_END_SCORE)
   private Double averageEndScore;
   @Field(Fields.AVERAGE_END_SYL_SCORE)
   private Double averageEndSyllableScore;
   @Field(Fields.SINGLES_PER_RHYME)
   private Double singlesPerRhyme;
   @Field(Fields.DOUBLES_PER_RHYME)
   private Double doublesPerRhyme;
   @Field(Fields.TRIPLES_PER_RHYME)
   private Double triplesPerRhyme;
   @Field(Fields.QUADS_PER_RHYME)
   private Double quadsPerRhyme;
   @Field(Fields.LONGS_PER_RHYME)
   private Double longsPerRhyme;
   @Field(Fields.PERFECT_RHYMES)
   private Double perfectRhymes;
   @Field(Fields.LINE_INTERNALS_PER_LINE)
   private Double lineInternalsPerLine;
   @Field(Fields.LINKS_PER_LINE)
   private Double linksPerLine;
   @Field(Fields.BRIDGES_PER_LINE)
   private Double bridgesPerLine;
   @Field(Fields.COMPOUNDS_PER_LINE)
   private Double compoundsPerLine;
   @Field(Fields.CHAINING_PER_LINE)
   private Double chainingPerLine;

   public Double getSyllablesPerLine() {

      return syllablesPerLine;
   }

   public void setSyllablesPerLine(Double syllablesPerLine) {

      this.syllablesPerLine = syllablesPerLine;
   }

   public Double getSyllablesPerWord() {

      return syllablesPerWord;
   }

   public void setSyllablesPerWord(Double syllablesPerWord) {

      this.syllablesPerWord = syllablesPerWord;
   }

   public Double getSyllableVariation() {

      return syllableVariation;
   }

   public void setSyllableVariation(Double syllableVariation) {

      this.syllableVariation = syllableVariation;
   }

   public Double getNovelWordProportion() {

      return novelWordProportion;
   }

   public void setNovelWordProportion(Double novelWordProportion) {

      this.novelWordProportion = novelWordProportion;
   }

   public Double getRhymesPerLine() {

      return rhymesPerLine;
   }

   public void setRhymesPerLine(Double rhymesPerLine) {

      this.rhymesPerLine = rhymesPerLine;
   }

   public Double getRhymesPerSyllable() {

      return rhymesPerSyllable;
   }

   public void setRhymesPerSyllable(Double rhymesPerSyllable) {

      this.rhymesPerSyllable = rhymesPerSyllable;
   }

   public Double getRhymeDensity() {

      return rhymeDensity;
   }

   public void setRhymeDensity(Double rhymeDensity) {

      this.rhymeDensity = rhymeDensity;
   }

   public Double getEndPairsPerLine() {

      return endPairsPerLine;
   }

   public void setEndPairsPerLine(Double endPairsPerLine) {

      this.endPairsPerLine = endPairsPerLine;
   }

   public Double getEndPairsGrown() {

      return endPairsGrown;
   }

   public void setEndPairsGrown(Double endPairsGrown) {

      this.endPairsGrown = endPairsGrown;
   }

   public Double getEndPairsShrunk() {

      return endPairsShrunk;
   }

   public void setEndPairsShrunk(Double endPairsShrunk) {

      this.endPairsShrunk = endPairsShrunk;
   }

   public Double getEndPairsEven() {

      return endPairsEven;
   }

   public void setEndPairsEven(Double endPairsEven) {

      this.endPairsEven = endPairsEven;
   }

   public Double getAverageEndScore() {

      return averageEndScore;
   }

   public void setAverageEndScore(Double averageEndScore) {

      this.averageEndScore = averageEndScore;
   }

   public Double getAverageEndSyllableScore() {

      return averageEndSyllableScore;
   }

   public void setAverageEndSyllableScore(Double averageEndSyllableScore) {

      this.averageEndSyllableScore = averageEndSyllableScore;
   }

   public Double getSinglesPerRhyme() {

      return singlesPerRhyme;
   }

   public void setSinglesPerRhyme(Double singlesPerRhyme) {

      this.singlesPerRhyme = singlesPerRhyme;
   }

   public Double getDoublesPerRhyme() {

      return doublesPerRhyme;
   }

   public void setDoublesPerRhyme(Double doublesPerRhyme) {

      this.doublesPerRhyme = doublesPerRhyme;
   }

   public Double getTriplesPerRhyme() {

      return triplesPerRhyme;
   }

   public void setTriplesPerRhyme(Double triplesPerRhyme) {

      this.triplesPerRhyme = triplesPerRhyme;
   }

   public Double getQuadsPerRhyme() {

      return quadsPerRhyme;
   }

   public void setQuadsPerRhyme(Double quadsPerRhyme) {

      this.quadsPerRhyme = quadsPerRhyme;
   }

   public Double getLongsPerRhyme() {

      return longsPerRhyme;
   }

   public void setLongsPerRhyme(Double longsPerRhyme) {

      this.longsPerRhyme = longsPerRhyme;
   }

   public Double getPerfectRhymes() {

      return perfectRhymes;
   }

   public void setPerfectRhymes(Double perfectRhymes) {

      this.perfectRhymes = perfectRhymes;
   }

   public Double getLineInternalsPerLine() {

      return lineInternalsPerLine;
   }

   public void setLineInternalsPerLine(Double lineInternalsPerLine) {

      this.lineInternalsPerLine = lineInternalsPerLine;
   }

   public Double getLinksPerLine() {

      return linksPerLine;
   }

   public void setLinksPerLine(Double linksPerLine) {

      this.linksPerLine = linksPerLine;
   }

   public Double getBridgesPerLine() {

      return bridgesPerLine;
   }

   public void setBridgesPerLine(Double bridgesPerLine) {

      this.bridgesPerLine = bridgesPerLine;
   }

   public Double getCompoundsPerLine() {

      return compoundsPerLine;
   }

   public void setCompoundsPerLine(Double compoundsPerLine) {

      this.compoundsPerLine = compoundsPerLine;
   }

   public Double getChainingPerLine() {

      return chainingPerLine;
   }

   public void setChainingPerLine(Double chainingPerLine) {

      this.chainingPerLine = chainingPerLine;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.SYLLABLES_PER_LINE, syllablesPerLine);
      update.set(Fields.SYLLABLES_PER_WORD, syllablesPerWord);
      update.set(Fields.SYLLABLE_VARIATION, syllableVariation);
      update.set(Fields.NOVEL_WORD_PROPORTION, novelWordProportion);
      update.set(Fields.RHYMES_PER_LINE, rhymesPerLine);
      update.set(Fields.RHYMES_PER_SYLLABLE, rhymesPerSyllable);
      update.set(Fields.RHYME_DENSITY, rhymeDensity);
      update.set(Fields.END_PAIRS_PER_LINE, endPairsPerLine);
      update.set(Fields.END_PAIRS_GROWN, endPairsGrown);
      update.set(Fields.END_PAIRS_SHRUNK, endPairsShrunk);
      update.set(Fields.END_PAIRS_EVEN, endPairsEven);
      update.set(Fields.AVERAGE_END_SCORE, averageEndScore);
      update.set(Fields.AVERAGE_END_SYL_SCORE, averageEndSyllableScore);
      update.set(Fields.SINGLES_PER_RHYME, singlesPerRhyme);
      update.set(Fields.DOUBLES_PER_RHYME, doublesPerRhyme);
      update.set(Fields.TRIPLES_PER_RHYME, triplesPerRhyme);
      update.set(Fields.QUADS_PER_RHYME, quadsPerRhyme);
      update.set(Fields.LONGS_PER_RHYME, longsPerRhyme);
      update.set(Fields.PERFECT_RHYMES, perfectRhymes);
      update.set(Fields.LINE_INTERNALS_PER_LINE, lineInternalsPerLine);
      update.set(Fields.LINKS_PER_LINE, linksPerLine);
      update.set(Fields.BRIDGES_PER_LINE, bridgesPerLine);
      update.set(Fields.COMPOUNDS_PER_LINE, compoundsPerLine);
      update.set(Fields.CHAINING_PER_LINE, chainingPerLine);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String SYLLABLES_PER_LINE = "syllables_per_line";
      public static final String SYLLABLES_PER_WORD = "syllables_per_word";
      public static final String SYLLABLE_VARIATION = "syllable_variation";
      public static final String NOVEL_WORD_PROPORTION = "novel_word_proportion";
      public static final String RHYMES_PER_LINE = "rhymes_per_line";
      public static final String RHYMES_PER_SYLLABLE = "rhymes_per_syllable";
      public static final String RHYME_DENSITY = "rhyme_density";
      public static final String END_PAIRS_PER_LINE = "end_pairs_per_line";
      public static final String END_PAIRS_GROWN = "end_pairs_grown";
      public static final String END_PAIRS_SHRUNK = "end_pairs_shrunk";
      public static final String END_PAIRS_EVEN = "end_pairs_even";
      public static final String AVERAGE_END_SCORE = "average_end_score";
      public static final String AVERAGE_END_SYL_SCORE = "average_end_syllable_score";
      public static final String SINGLES_PER_RHYME = "singles_per_rhyme";
      public static final String DOUBLES_PER_RHYME = "doubles_per_rhyme";
      public static final String TRIPLES_PER_RHYME = "triples_per_rhyme";
      public static final String QUADS_PER_RHYME = "quads_per_rhyme";
      public static final String LONGS_PER_RHYME = "longs_per_rhyme";
      public static final String PERFECT_RHYMES = "perfect_rhymes";
      public static final String LINE_INTERNALS_PER_LINE = "line_internals_per_line";
      public static final String LINKS_PER_LINE = "links_per_line";
      public static final String BRIDGES_PER_LINE = "bridges_per_line";
      public static final String COMPOUNDS_PER_LINE = "compounds_per_line";
      public static final String CHAINING_PER_LINE = "chaining_per_line";
      private Fields() {

      }
   }
}
