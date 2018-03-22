package musicanalysis.model.nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * The art of tokenization:
 * citing see: http://stanfordnlp.github.io/CoreNLP/
 */
public class Tokenizer {

   private final StanfordCoreNLP pipeline;

   private Tokenizer() {

      final Properties props = new Properties();
      //props.put("ssplit.eolonly", "true");
      //props.put("tokenize.class", "PTBTokenizer");
      props.put("ssplit.newlineIsSentenceBreak", "always");
      props.put("ssplit.boundaryTokenRegex", "\\*NL\\*");
      props.put("ssplit.boundariesToDiscard", "");
      props.put("annotators", "tokenize, ssplit, pos, lemma");    // additional options ner, parse, dcoref
      pipeline = new StanfordCoreNLP(props);
   }

   public List<Line> tokenize(final String text) {

      final List<Line> lines = new ArrayList<>();

      final Annotation document = new Annotation(text);
      pipeline.annotate(document);
      final List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

      String lastLine = null;
      for (final CoreMap s : sentences) {
         final String line = s.toString().trim();

         // do not append successive empty lines
         if (!(lastLine != null && lastLine.isEmpty() && line.isEmpty())) {
            final Line l = new Line();
            l.setText(line);
            l.addTokens(s.get(CoreAnnotations.TokensAnnotation.class).stream().filter(t -> !t.word().equals(PTBTokenizer.getNewlineToken()))
                         .collect(Collectors.toList()));
            lines.add(l);
         }

         lastLine = line;
      }

      return lines;
   }

   public static Tokenizer getInstance() {

      return InstanceHolder.INSTANCE;
   }

   private static class InstanceHolder {

      private static final Tokenizer INSTANCE = new Tokenizer();
   }
}
