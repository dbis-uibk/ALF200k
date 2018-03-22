package musicanalysis.model.music.lyric.feature.lexical;

import edu.stanford.nlp.ling.CoreLabel;
import musicanalysis.model.data.BagOfWords;
import musicanalysis.model.music.lyric.feature.PreparedLyric;
import musicanalysis.model.nlp.Stopwords;

import java.util.ArrayList;
import java.util.List;

public class BagOfWordsEvaluator {

   private final PreparedLyric lyric;

   public BagOfWordsEvaluator(final PreparedLyric lyric) {

      this.lyric = lyric;
   }

   public BagOfWords evaluate() {

      final BagOfWords bagOfWords = new BagOfWords();

      bagOfWords.setLyricId(lyric.getId());
      bagOfWords.setEntireWords(getEntireWords());
      bagOfWords.setStopWords(getStopwords());
      bagOfWords.setContentWords(getContentWords());
      bagOfWords.setLemmatizedContentWords(getLemmatizedContentWords());
      bagOfWords.setPOSTags(getPOSTags());

      return bagOfWords;
   }

   private List<String> getEntireWords() {

      final List<String> entireWords = new ArrayList<>();

      for (final CoreLabel token : lyric.getTokens()) {
         entireWords.add(token.word().toLowerCase());
      }

      return entireWords;
   }

   private List<String> getStopwords() {

      final List<String> stopWords = new ArrayList<>();

      for (final CoreLabel token : lyric.getTokens()) {
         final String word = token.word().toLowerCase();
         if (Stopwords.getInstance().isStopword(word)) {
            stopWords.add(word);
         }
      }

      return stopWords;
   }

   private List<String> getContentWords() {

      final List<String> contentWords = new ArrayList<>();

      for (final CoreLabel token : lyric.getTokens()) {
         final String word = token.word().toLowerCase();
         if (!Stopwords.getInstance().isStopword(word)) {
            contentWords.add(word);
         }
      }

      return contentWords;
   }

   private List<String> getLemmatizedContentWords() {

      final List<String> contentWords = new ArrayList<>();

      for (final CoreLabel token : lyric.getTokens()) {
         if (!Stopwords.getInstance().isStopword(token.word())) {
            contentWords.add(token.lemma().toLowerCase());
         }
      }

      return contentWords;
   }

   private List<String> getPOSTags() {

      final List<String> posTags = new ArrayList<>();

      for (final CoreLabel token : lyric.getTokens()) {
         posTags.add(token.tag());
      }

      return posTags;
   }
}
