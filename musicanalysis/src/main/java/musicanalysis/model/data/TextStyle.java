package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "textstyles")
public class TextStyle extends LyricFeature {

   @Field(Fields.TOKEN_COUNT)
   private Integer tokenCount;
   @Field(Fields.UNIQUE_TOKEN_RATIO)
   private Float uniqueTokenRatio;
   @Field(Fields.UNIQUE_BIGRAM_RATIO)
   private Float uniqueBigramRatio;
   @Field(Fields.UNIQUE_TRIGRAM_RATIO)
   private Float uniqueTrigramRatio;
   @Field(Fields.AVERAGE_TOKEN_LENGTH)
   private Float averageTokenLength;
   @Field(Fields.UNIQUE_TOKENS_PER_LINE)
   private Float uniqueTokensPerLine;
   @Field(Fields.AVERAGE_TOKENS_PER_LINE)
   private Float averageTokensPerLine;
   @Field(Fields.REPEAT_WORD_RATIO)
   private Float repeatWordRatio;
   @Field(Fields.LINE_COUNT)
   private Integer lineCount;
   @Field(Fields.UNIQUE_LINE_COUNT)
   private Integer uniqueLineCount;
   @Field(Fields.BLANK_LINE_COUNT)
   private Integer blankLineCount;
   @Field(Fields.BLANK_LINE_RATIO)
   private Float blankLineRatio;
   @Field(Fields.REPEAT_LINE_RATIO)
   private Float repeatLineRatio;
   @Field(Fields.WORDS_PER_MINUTE)
   private Float wordsPerMinute;
   @Field(Fields.CHARS_PER_MINUTE)
   private Float charsPerMinute;
   @Field(Fields.LINES_PER_MINUTE)
   private Float linesPerMinute;
   @Field(Fields.DIGITS)
   private Float digits;
   @Field(Fields.EXCLAMATION_MARKS)
   private Float exclamationMarks;
   @Field(Fields.QUESTION_MARKS)
   private Float questionMarks;
   @Field(Fields.COLONS)
   private Float colons;
   @Field(Fields.SEMICOLONS)
   private Float semicolons;
   @Field(Fields.QUOTES)
   private Float quotes;
   @Field(Fields.COMMAS)
   private Float commas;
   @Field(Fields.DOTS)
   private Float dots;
   @Field(Fields.HYPHENS)
   private Float hyphens;
   @Field(Fields.STOPWORDS_RATIO)
   private Float stopWordRatio;
   @Field(Fields.STOPWORDS_PER_LINE)
   private Float stopWordsPerLine;
   @Field(Fields.HAPAX_LEGOMENON_RATIO)
   private Float hapaxLegomenonRatio;
   @Field(Fields.DIS_LEGOMENON_RATIO)
   private Float disLegomenonRatio;
   @Field(Fields.TRIS_LEGOMENON_RATIO)
   private Float trisLegomenonRatio;

   public Integer getTokenCount() {
      return tokenCount;
   }

   public void setTokenCount(Integer tokenCount) {

      this.tokenCount = tokenCount;
   }

   public Float getAverageTokensPerLine() {
      return averageTokensPerLine;
   }

   public void setAverageTokensPerLine(Float averageWordsPerLine) {
      this.averageTokensPerLine = averageWordsPerLine;
   }

   public Float getRepeatWordRatio() {
      return repeatWordRatio;
   }

   public void setRepeatWordRatio(Float repeatWordRatio) {
      this.repeatWordRatio = repeatWordRatio;
   }

   public Integer getLineCount() {
      return lineCount;
   }

   public void setLineCount(Integer lineCount) {
      this.lineCount = lineCount;
   }

   public Integer getUniqueLineCount() {
      return uniqueLineCount;
   }

   public void setUniqueLineCount(Integer uniqueLineCount) {
      this.uniqueLineCount = uniqueLineCount;
   }

   public Integer getBlankLineCount() {
      return blankLineCount;
   }

   public void setBlankLineCount(Integer blankLineCount) {
      this.blankLineCount = blankLineCount;
   }

   public Float getBlankLineRatio() {
      return blankLineRatio;
   }

   public void setBlankLineRatio(Float blankLineRatio) {
      this.blankLineRatio = blankLineRatio;
   }

   public Float getRepeatLineRatio() {
      return repeatLineRatio;
   }

   public void setRepeatLineRatio(Float repeatLineRatio) {
      this.repeatLineRatio = repeatLineRatio;
   }

   public Float getWordsPerMinute() {
      return wordsPerMinute;
   }

   public void setWordsPerMinute(Float wordsPerMinute) {
      this.wordsPerMinute = wordsPerMinute;
   }

   public Float getCharsPerMinute() {
      return charsPerMinute;
   }

   public void setCharsPerMinute(Float charsPerMinute) {
      this.charsPerMinute = charsPerMinute;
   }

   public Float getLinesPerMinute() {
      return linesPerMinute;
   }

   public void setLinesPerMinute(Float linesPerMinute) {
      this.linesPerMinute = linesPerMinute;
   }

   public Float getUniqueTokenRatio() {

      return uniqueTokenRatio;
   }

   public void setUniqueTokenRatio(Float wordRatio) {

      this.uniqueTokenRatio = wordRatio;
   }

   public Float getUniqueBigramRatio() {

      return uniqueBigramRatio;
   }

   public void setUniqueBigramRatio(Float uniqueBigramRatio) {

      this.uniqueBigramRatio = uniqueBigramRatio;
   }

   public Float getUniqueTrigramRatio() {

      return uniqueTrigramRatio;
   }

   public void setUniqueTrigramRatio(Float uniqueTrigramRatio) {

      this.uniqueTrigramRatio = uniqueTrigramRatio;
   }

   public Float getAverageTokenLength() {

      return averageTokenLength;
   }

   public void setAverageTokenLength(Float averageWordLength) {

      this.averageTokenLength = averageWordLength;
   }

   public Float getUniqueTokensPerLine() {

      return uniqueTokensPerLine;
   }

   public void setUniqueTokensPerLine(Float uniqueWordsPerLine) {

      this.uniqueTokensPerLine = uniqueWordsPerLine;
   }

   public Float getDigits() {
      return digits;
   }

   public void setDigits(Float digitCount) {
      this.digits = digitCount;
   }

   public Float getExclamationMarks() {
      return exclamationMarks;
   }

   public void setExclamationMarks(Float exclamationMarks) {
      this.exclamationMarks = exclamationMarks;
   }

   public Float getQuestionMarks() {
      return questionMarks;
   }

   public void setQuestionMarks(Float questionMarks) {
      this.questionMarks = questionMarks;
   }

   public Float getColons() {
      return colons;
   }

   public void setColons(Float colons) {
      this.colons = colons;
   }

   public Float getSemicolons() {
      return semicolons;
   }

   public void setSemicolons(Float semicolons) {
      this.semicolons = semicolons;
   }

   public Float getQuotes() {
      return quotes;
   }

   public void setQuotes(Float quotes) {
      this.quotes = quotes;
   }

   public Float getCommas() {
      return commas;
   }

   public void setCommas(Float commas) {
      this.commas = commas;
   }

   public Float getDots() {
      return dots;
   }

   public void setDots(Float dots) {
      this.dots = dots;
   }

   public Float getHyphens() {
      return hyphens;
   }

   public void setHyphens(Float hyphens) {
      this.hyphens = hyphens;
   }

   public float getStopWordRatio() {
      return stopWordRatio;
   }

   public void setStopWordRatio(Float stopWordRatio) {
      this.stopWordRatio = stopWordRatio;
   }

   public float getStopWordsPerLine() {
      return stopWordsPerLine;
   }

   public void setStopWordsPerLine(Float stopWordsPerLine) {
      this.stopWordsPerLine = stopWordsPerLine;
   }

   public Float getHapaxLegomenonRatio() {
      return hapaxLegomenonRatio;
   }

   public void setHapaxLegomenonRatio(Float hapaxLegomenonRatio) {
      this.hapaxLegomenonRatio = hapaxLegomenonRatio;
   }

   public Float getDisLegomenonRatio() {
      return disLegomenonRatio;
   }

   public void setDisLegomenonRatio(Float disLegomenonRatio) {
      this.disLegomenonRatio = disLegomenonRatio;
   }

   public float getTrisLegomenonRatio() {
      return trisLegomenonRatio;
   }

   public void setTrisLegomenonRatio(Float trisLegomenonRatio) {
      this.trisLegomenonRatio = trisLegomenonRatio;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.TOKEN_COUNT, tokenCount);
      update.set(Fields.UNIQUE_TOKEN_RATIO, uniqueTokenRatio);
      update.set(Fields.UNIQUE_BIGRAM_RATIO, uniqueBigramRatio);
      update.set(Fields.UNIQUE_TRIGRAM_RATIO, uniqueTrigramRatio);
      update.set(Fields.AVERAGE_TOKEN_LENGTH, averageTokenLength);
      update.set(Fields.UNIQUE_TOKENS_PER_LINE, uniqueTokensPerLine);
      update.set(Fields.AVERAGE_TOKENS_PER_LINE, averageTokensPerLine);
      update.set(Fields.REPEAT_WORD_RATIO, repeatWordRatio);
      update.set(Fields.LINE_COUNT, lineCount);
      update.set(Fields.UNIQUE_LINE_COUNT, uniqueLineCount);
      update.set(Fields.BLANK_LINE_COUNT, blankLineCount);
      update.set(Fields.BLANK_LINE_RATIO, blankLineRatio);
      update.set(Fields.REPEAT_LINE_RATIO, repeatLineRatio);
      update.set(Fields.WORDS_PER_MINUTE, wordsPerMinute);
      update.set(Fields.CHARS_PER_MINUTE, charsPerMinute);
      update.set(Fields.LINES_PER_MINUTE, linesPerMinute);
      update.set(Fields.DIGITS, digits);
      update.set(Fields.EXCLAMATION_MARKS, exclamationMarks);
      update.set(Fields.QUESTION_MARKS, questionMarks);
      update.set(Fields.COLONS, colons);
      update.set(Fields.SEMICOLONS, semicolons);
      update.set(Fields.QUOTES, quotes);
      update.set(Fields.COMMAS, commas);
      update.set(Fields.DOTS, dots);
      update.set(Fields.HYPHENS, hyphens);
      update.set(Fields.STOPWORDS_RATIO, stopWordRatio);
      update.set(Fields.STOPWORDS_PER_LINE, stopWordsPerLine);
      update.set(Fields.HAPAX_LEGOMENON_RATIO, hapaxLegomenonRatio);
      update.set(Fields.DIS_LEGOMENON_RATIO, disLegomenonRatio);
      update.set(Fields.TRIS_LEGOMENON_RATIO, trisLegomenonRatio);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String TOKEN_COUNT = "token_count";
      public static final String UNIQUE_TOKEN_RATIO = "unique_token_ratio";
      public static final String UNIQUE_BIGRAM_RATIO = "unique_bigram_ratio";
      public static final String UNIQUE_TRIGRAM_RATIO = "unique_trigram_ratio";
      public static final String AVERAGE_TOKEN_LENGTH = "average_token_length";
      public static final String UNIQUE_TOKENS_PER_LINE = "unique_tokens_per_line";
      public static final String AVERAGE_TOKENS_PER_LINE = "average_tokens_per_line";
      public static final String REPEAT_WORD_RATIO = "repeat_word_ratio";
      public static final String LINE_COUNT = "line_count";
      public static final String UNIQUE_LINE_COUNT = "unique_line_count";
      public static final String BLANK_LINE_COUNT = "blank_line_count";
      public static final String BLANK_LINE_RATIO = "blank_line_ratio";
      public static final String REPEAT_LINE_RATIO = "repeat_line_ratio";
      public static final String WORDS_PER_MINUTE = "words_per_minute";
      public static final String CHARS_PER_MINUTE = "chars_per_minute";
      public static final String LINES_PER_MINUTE = "lines_per_minute";
      public static final String DIGITS = "digits";
      public static final String EXCLAMATION_MARKS = "exclamation_marks";
      public static final String QUESTION_MARKS = "question_marks";
      public static final String COLONS = "colons";
      public static final String SEMICOLONS = "semicolons";
      public static final String QUOTES = "quotes";
      public static final String COMMAS = "commas";
      public static final String DOTS = "dots";
      public static final String HYPHENS = "hyphens";
      public static final String STOPWORDS_RATIO = "stopwords_ratio";
      public static final String STOPWORDS_PER_LINE = "stopwords_per_line";
      public static final String HAPAX_LEGOMENON_RATIO = "hapax_legomenon_ratio";
      public static final String DIS_LEGOMENON_RATIO = "dis_legomenon_ratio";
      public static final String TRIS_LEGOMENON_RATIO = "tris_legomenon_ratio";

      private Fields() {

      }
   }
}
