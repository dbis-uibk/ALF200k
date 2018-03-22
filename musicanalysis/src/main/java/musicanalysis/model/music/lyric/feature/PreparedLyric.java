package musicanalysis.model.music.lyric.feature;

import edu.stanford.nlp.ling.CoreLabel;
import musicanalysis.model.data.Lyric;
import musicanalysis.model.nlp.Line;
import musicanalysis.model.nlp.Tokenizer;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class PreparedLyric {

   private final Tokenizer tokenizer;
   private final Lyric lyric;
   private List<CoreLabel> tokens = null;
   private List<Line> lines = null;

   public PreparedLyric(final Lyric l) {

      lyric = l;
      tokenizer = Tokenizer.getInstance();
   }

   public ObjectId getId() {

      return lyric.getId();
   }

   public List<CoreLabel> getTokens() {

      if (tokens == null) {
         tokens = new ArrayList<>();
         getLines().forEach(l -> tokens.addAll(l.getTokens()));
      }

      return tokens;
   }

   public List<Line> getLines() {

      if (lines == null) {
         lines = tokenizer.tokenize(lyric.getText());
      }

      return lines;
   }

   public String getText() {

      return lyric.getText();
   }

   public List<Verse> getVerses() {

      final List<Verse> verses = new ArrayList<>();

      Verse verse = new Verse();
      for (final Line line : getLines()) {
         if (line.getText().trim().isEmpty()) {
            verses.add(verse);
            verse = new Verse();
         } else {
            verse.addLine(line);
         }
      }

      if (!verses.contains(verse) && verse.getLines().size() > 0) {
         verses.add(verse);
      }

      return verses;
   }
}
