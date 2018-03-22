package musicanalysis.model.music.lyric.feature.linguistic;

import edu.stanford.nlp.ling.CoreLabel;
import musicanalysis.model.data.SlangUse;
import musicanalysis.model.music.lyric.feature.PreparedLyric;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SlangUseEvaluator {

   private final Wiktionary wiktionary;
   private final UrbanDictionary urbanDictionary;
   private final PreparedLyric lyric;

   public SlangUseEvaluator(final PreparedLyric lyric) {

      this.lyric = lyric;
      this.wiktionary = Wiktionary.getInstance();
      this.urbanDictionary = UrbanDictionary.getInstance();
   }

   public SlangUse evaluate() {

      final SlangUse slangUse = new SlangUse();

      final List<CoreLabel> tokens = filterTokens();

      slangUse.setLyricId(lyric.getId());
      slangUse.setSlangWordsRatio(getSlangWordsRatio(tokens));
      slangUse.setUncommonWordsRatio(getUncommonWordsRatio(tokens));
      slangUse.setUniqueUncommonWordsRatio(getUniqueUncommonWordsRatio(tokens));
      slangUse.setLemmasRatio(getLemmasRatio(tokens));

      return slangUse;
   }

   private List<CoreLabel> filterTokens() {

      // special characters like ". ! ?" are not uncommon words that's why they are excluded
      final Pattern pattern = Pattern.compile("^[a-zA-Z]+");
      return lyric.getTokens().stream().filter(t -> pattern.matcher(t.word()).find()).collect(Collectors.toList());
   }

   private float getUniqueUncommonWordsRatio(final List<CoreLabel> tokens) {

      int uncommonWords = 0;

      final Set<String> handledTokens = new HashSet<>();
      for (final CoreLabel label : tokens) {
         final String token = label.word().toLowerCase();
         if (!handledTokens.contains(token)) {
            handledTokens.add(token);
            if (!wiktionary.request(label.word())) {
               uncommonWords++;
            }
         }
      }

      return uncommonWords / (float) tokens.size();
   }

   private float getUncommonWordsRatio(final List<CoreLabel> tokens) {

      int uncommonWords = 0;

      for (final CoreLabel label : tokens) {
         if (!wiktionary.request(label.word())) {
            uncommonWords++;
         }
      }

      return uncommonWords / (float) tokens.size();
   }

   private float getSlangWordsRatio(final List<CoreLabel> tokens) {

      int slangWords = 0;

      for (final CoreLabel label : tokens) {
         final String token = label.word();
         if (!wiktionary.request(token)) {
            if (urbanDictionary.request(token)) {
               slangWords++;
            }
         }
      }

      return slangWords / (float) tokens.size();
   }

   private float getLemmasRatio(final List<CoreLabel> tokens) {

      int lemmas = 0;

      final Set<String> handledTokens = new HashSet<>();

      for (final CoreLabel label : tokens) {
         final String token = label.word().toLowerCase();
         if (!handledTokens.contains(token)) {
            handledTokens.add(token);
            if (label.lemma().equalsIgnoreCase(token)) {
               lemmas++;
            }
         }
      }

      return lemmas / (float) handledTokens.size();
   }
}
