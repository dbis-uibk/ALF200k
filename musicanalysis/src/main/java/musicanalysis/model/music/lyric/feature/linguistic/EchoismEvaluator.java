package musicanalysis.model.music.lyric.feature.linguistic;

import edu.stanford.nlp.ling.CoreLabel;
import musicanalysis.model.data.Echoism;
import musicanalysis.model.music.lyric.feature.PreparedLyric;
import musicanalysis.model.nlp.Line;
import ru.fuzzysearch.DamerauLevensteinMetric;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EchoismEvaluator {

   private final Wiktionary wiktionary;
   private final DamerauLevensteinMetric lev;
   private final PreparedLyric lyric;

   private WordEchoism wordEchoism;             // musical words upholstered to all tokens
   private LineEchoism lineEchoismLength1;      // line echoism length 1
   private LineEchoism lineEchoismLength2;      // line echoism length 2
   private LineEchoism lineEchoismMinLength3;   // line echoism min length 3

   public EchoismEvaluator(final PreparedLyric lyric) {

      this.wiktionary = Wiktionary.getInstance();
      this.lev = new DamerauLevensteinMetric();
      this.lyric = lyric;
   }

   public Echoism evaluate() {

      final Echoism echoism = new Echoism();

      wordEchoism = new WordEchoism();
      lineEchoismLength1 = new LineEchoism();
      lineEchoismLength2 = new LineEchoism();
      lineEchoismMinLength3 = new LineEchoism();

      computeMusicalWords();
      computeMultiWordEchoisms();

      final float totalTokens = lyric.getTokens().size();

      echoism.setLyricId(lyric.getId());

      echoism.setWordEchoismMusicalWordsRatio(wordEchoism.musicalWords / totalTokens);

      echoism.setMultiEchoismMusicalWordsRatioLength1(lineEchoismLength1.musicalWords / totalTokens);
      echoism.setMultiEchoismReduplicationRatioLength1(lineEchoismLength1.reduplication / totalTokens);
      echoism.setMultiEchoismRhymeAlikeRatioLength1(lineEchoismLength1.rhymeAlike / totalTokens);

      echoism.setMultiEchoismMusicalWordsRatioLength2(lineEchoismLength2.musicalWords / totalTokens);
      echoism.setMultiEchoismReduplicationRatioLength2(lineEchoismLength2.reduplication / totalTokens);
      echoism.setMultiEchoismRhymeAlikeRatioLength2(lineEchoismLength2.rhymeAlike / totalTokens);

      echoism.setMultiEchoismMusicalWordsRatioMinLength3(lineEchoismMinLength3.musicalWords / totalTokens);
      echoism.setMultiEchoismReduplicationRatioMinLength3(lineEchoismMinLength3.reduplication / totalTokens);
      echoism.setMultiEchoismRhymeAlikeRatioMinLength3(lineEchoismMinLength3.rhymeAlike / totalTokens);

      return echoism;
   }

   private void computeMusicalWords() {

      for (final CoreLabel token : lyric.getTokens()) {
         if (isMusicalWord(token)) {
            wordEchoism.musicalWords++;
         }
      }
   }

   private boolean isMusicalWord(final CoreLabel token) {

      final char[] chars = token.word().toCharArray();
      final Set<Character> uniqueChars = new HashSet<>();
      for (char c : chars) {
         uniqueChars.add(c);
      }

      float letterInnovation = uniqueChars.size() / (float) chars.length;

      return letterInnovation < 0.4 || (letterInnovation < 0.5 && !wiktionary.request(token.word()));
   }

   private void computeMultiWordEchoisms() {

      for (final Line l : lyric.getLines()) {

         final List<CoreLabel> t = l.getTokens();
         if (t.size() > 1) {
            final List<CoreLabel> tokens = new ArrayList<>();
            for (int i = 0; i < (t.size() - 1); i++) {
               double distance = edit(t.get(i).word(), t.get(i + 1).word());

               if (distance < 0.5) {
                  if (tokens.isEmpty()) tokens.add(t.get(i));
                  tokens.add(t.get(i + 1));
               }

               boolean lastElement = ((i + 1) >= (t.size() - 1));
               if ((distance >= 0.5 || lastElement) && !tokens.isEmpty()) {
                  final LineEchoism echoism = getLineEchoism(tokens.size() - 1);
                  classifyEchoism(echoism, tokens);
                  tokens.clear();
               }
            }
         }
      }
   }

   private LineEchoism getLineEchoism(final int length) {

      if (length <= 1) {
         return lineEchoismLength1;
      } else if (length <= 2) {
         return lineEchoismLength2;
      } else {
         return lineEchoismMinLength3;
      }
   }

   private void classifyEchoism(final LineEchoism echoism, final List<CoreLabel> tokens) {

      final Set<String> lemmas = new HashSet<>();
      tokens.forEach(t -> lemmas.add(t.lemma()));

      if (lemmas.size() == 1) {
         lemmas.forEach(l -> {
            if (l != null && wiktionary.request(l)) {
               echoism.reduplication++;
            } else {
               echoism.musicalWords++;
            }
         });
      } else if (lemmas.size() > 1) {
         boolean allInWiktionary = true;
         boolean nothingInWiktionary = true;
         for (final String lemma : lemmas) {
            boolean result = lemma != null && wiktionary.request(lemma);
            allInWiktionary &= result;
            nothingInWiktionary &= !result;
         }

         if (allInWiktionary) {
            echoism.rhymeAlike++;
         } else if (nothingInWiktionary) {
            echoism.musicalWords++;
         }
      }
   }

   private double edit(final String token1, final String token2) {

      return (1 / Math.sqrt(token1.length() * token2.length())) * lev.getDistance(token1, token2);
   }

   private class WordEchoism {

      private int musicalWords = 0;
   }

   private class LineEchoism {

      private int musicalWords = 0;
      private int reduplication = 0;
      private int rhymeAlike = 0;
   }
}
