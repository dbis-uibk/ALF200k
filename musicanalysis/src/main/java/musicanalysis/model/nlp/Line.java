package musicanalysis.model.nlp;

import edu.stanford.nlp.ling.CoreLabel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Line {

   private List<CoreLabel> tokens;
   private String text;

   public Line() {

      tokens = new ArrayList<>();
   }

   protected void addTokens(final List<CoreLabel> tokens) {

      this.tokens.addAll(tokens);
   }

   protected void addToken(final CoreLabel token) {

      tokens.add(token);
   }

   public List<CoreLabel> getTokens() {

      return Collections.unmodifiableList(tokens);
   }

   public String getText() {

      return text;
   }

   protected void setText(final String text) {

      this.text = text;
   }

   @Override
   public String toString() {
      return "Line{" +
            "text='" + text + '\'' +
            '}';
   }
}
