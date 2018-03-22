package musicanalysis.model.music.lyric.feature.lexical;

import edu.stanford.nlp.ling.CoreLabel;
import musicanalysis.model.common.NLP;
import musicanalysis.model.data.TextStyle;
import musicanalysis.model.music.lyric.feature.PreparedLyric;
import musicanalysis.model.nlp.Line;
import musicanalysis.model.nlp.Stopwords;

import java.util.*;
import java.util.stream.Collectors;

public class TextStyleEvaluator {

   private final PreparedLyric lyric;
   private final float duration;

   public TextStyleEvaluator(final PreparedLyric lyric, final float duration) {

      this.lyric = lyric;
      this.duration = duration;
   }

   public TextStyle evaluate() {

      final TextStyle style = new TextStyle();

      final List<Line> lines = lyric.getLines();

      final List<CoreLabel> tokens = lyric.getTokens();

      final List<String> tokenWords = new ArrayList<>();
      for (int i = 0; i < lines.size(); i++) {
         final Line l = lines.get(i);
         l.getTokens().forEach(t -> tokenWords.add(t.word().toLowerCase()));
         if (i < (lines.size() - 1)) {
            // don't add new line the last line
            tokenWords.add(System.lineSeparator());
         }
      }

      final Set<String> tokenSet = new HashSet<>();
      final Set<String> bigramSet = new HashSet<>();
      final Set<String> trigramSet = new HashSet<>();

      final List<List<String>> bigrams = NLP.createShingles(tokenWords, 2);
      final List<List<String>> trigrams = NLP.createShingles(tokenWords, 3);

      bigramSet.addAll(bigrams.stream().map(l -> l.stream().collect(Collectors.joining(" "))).collect(Collectors.toList()));
      trigramSet.addAll(trigrams.stream().map(l -> l.stream().collect(Collectors.joining(" "))).collect(Collectors.toList()));

      tokenWords.forEach(tokenSet::add);
      // newline has been added for creating bigrams, hence we have to remove it
      tokenSet.remove(System.lineSeparator());

      final Set<String> uniqueLines = new HashSet<>();
      lines.forEach(l -> uniqueLines.add(l.getText().toLowerCase()));

      style.setLyricId(lyric.getId());

      style.setTokenCount(tokens.size());
      style.setUniqueTokenRatio(tokenSet.size() / (float) tokens.size());
      style.setUniqueBigramRatio(bigramSet.size() / (float) bigrams.size());
      style.setUniqueTrigramRatio(trigramSet.size() / (float) trigrams.size());
      style.setUniqueTokensPerLine(tokenSet.size() / (float) lines.size());
      style.setAverageTokensPerLine(tokens.size() / (float) lines.size());
      style.setAverageTokenLength(tokens.stream().mapToInt(t -> t.word().length()).sum() / (float) tokens.size());
      style.setRepeatWordRatio((tokens.size() - tokenSet.size()) / (float) tokens.size());

      style.setLineCount(lines.size());
      style.setUniqueLineCount(uniqueLines.size());
      style.setBlankLineCount((int) lines.stream().filter(l -> l.getText().trim().isEmpty()).count());
      style.setBlankLineRatio(style.getBlankLineCount() / (float) lines.size());
      style.setRepeatLineRatio((lines.size() - uniqueLines.size()) / (float) lines.size());
      style.setWordsPerMinute((tokens.size() / duration) * 60);
      style.setLinesPerMinute((lines.size() / duration) * 60);
      style.setCharsPerMinute(tokens.stream().mapToInt(t -> t.word().length()).sum() / duration * 60);

      final int totalChars = tokens.stream().mapToInt(t -> t.word().length()).sum();
      style.setExclamationMarks(tokens.stream().mapToInt(t -> countCharacter(t.word(), "!")).sum() / (float) totalChars);
      style.setQuestionMarks(tokens.stream().mapToInt(t -> countCharacter(t.word(), "\\?")).sum() / (float) totalChars);
      style.setDigits(tokens.stream().mapToInt(t -> countCharacter(t.word(), "\\d")).sum() / (float) totalChars);
      style.setColons(tokens.stream().mapToInt(t -> countCharacter(t.word(), ":")).sum() / (float) totalChars);
      style.setSemicolons(tokens.stream().mapToInt(t -> countCharacter(t.word(), ";")).sum() / (float) totalChars);
      style.setQuotes(tokens.stream().mapToInt(t -> countCharacter(t.word(), "[`']")).sum() / (float) totalChars);
      style.setCommas(tokens.stream().mapToInt(t -> countCharacter(t.word(), ",")).sum() / (float) totalChars);
      style.setDots(tokens.stream().mapToInt(t -> countCharacter(t.word(), "\\.")).sum() / (float) totalChars);
      style.setHyphens(tokens.stream().mapToInt(t -> countCharacter(t.word(), "-")).sum() / (float) totalChars);

      final List<CoreLabel> stopwords = tokens.stream().filter(t -> Stopwords.getInstance().isStopword(t.word())).collect(Collectors.toList());
      style.setStopWordRatio(stopwords.size() / (float) tokens.size());
      style.setStopWordsPerLine(stopwords.size() / (float) lines.size());
      final Map<String, Integer> tokenFrequencies = getTokenFrequencies(tokens);
      style.setHapaxLegomenonRatio(legomenon(tokenFrequencies, 1) / (float) tokens.size());
      style.setDisLegomenonRatio(legomenon(tokenFrequencies, 2) / (float) tokens.size());
      style.setTrisLegomenonRatio(legomenon(tokenFrequencies, 3) / (float) tokens.size());

      return style;
   }

   private int countCharacter(final String token, final String regex) {

      int counter = 0;
      for (int i = 0; i < token.length(); i++) {
         if (String.valueOf(token.charAt(i)).matches(regex)) {
            counter++;
         }
      }
      return counter;
   }

   private Map<String, Integer> getTokenFrequencies(final List<CoreLabel> tokens) {

      final Map<String, Integer> frequencies = new HashMap<>();
      for (final CoreLabel token : tokens) {
         final String key = token.word().toLowerCase();
         int value = 0;
         if (frequencies.containsKey(key)) {
            value = frequencies.get(key);
         }
         frequencies.put(key, ++value);
      }
      return frequencies;
   }

   private int legomenon(final Map<String, Integer> frequencies, int amount) {

      int count = 0;

      for (final Integer value : frequencies.values()) {
         if (value.equals(amount)) {
            count++;
         }
      }

      return count;
   }
}
