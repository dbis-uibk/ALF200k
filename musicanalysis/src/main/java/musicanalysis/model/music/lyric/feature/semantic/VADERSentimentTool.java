package musicanalysis.model.music.lyric.feature.semantic;

import musicanalysis.model.common.Path;
import musicanalysis.model.settings.Settings;
import org.apache.commons.io.FileUtils;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;

class VADERSentimentTool {

   private final PythonInterpreter interpreter;
   private final ISentimentAnalyzer analyzer;

   private VADERSentimentTool() {

      String script = "";
      try {
         script = FileUtils.readFileToString(new File(Path.getInstance().getResourcePath(Settings.VADER_PYTHON_SCRIPT_PATH)), "UTF-8");
         script = script.replaceAll("XXXHEREXXXPATHXXX", Path.getInstance().getResourcePath(Settings.VADER_LEXICON_PATH));
      } catch (Exception e) {
         // should not happen
         e.printStackTrace();
      }

      final Properties properties = new Properties();
      properties.put("python.home", Path.getInstance().getResourcePath("python") + "/");
      properties.put("python.cachedir.skip", "true");
      properties.put("python.console.encoding", "UTF-8");
      properties.put("python.security.respectJavaAccessibility", "false");
      final Properties preproperties = new Properties();

      PythonInterpreter.initialize(preproperties, properties, new String[0]);
      interpreter = new PythonInterpreter();
      interpreter.exec(script);
      analyzer = create();
   }

   public static VADERSentimentTool getInstance() {

      return VADERSentimentTool.InstanceHolder.INSTANCE;
   }

   private ISentimentAnalyzer create() {

      final PyObject function = interpreter.get("sentiment");
      return (ISentimentAnalyzer) function.__tojava__(ISentimentAnalyzer.class);
   }

   public Values getSentiment(final String line) {

      final Values values = new Values();

      final ConcurrentMap<String, Double> result = analyzer.sentiment(line);
      result.forEach((k, v) -> {
         float value = v.floatValue();
         switch (k.toLowerCase()) {
            case "compound":
               values.compound = value;
               break;
            case "neg":
               values.negative = value;
               break;
            case "pos":
               values.positive = value;
               break;
            case "neu":
               values.neutral = value;
               break;
         }
      });

      return values;
   }

   private interface ISentimentAnalyzer {

      ConcurrentMap<String, Double> sentiment(String text);
   }

   private static class InstanceHolder {

      static final VADERSentimentTool INSTANCE = new VADERSentimentTool();
   }

   public class Values {

      private float compound;
      private float negative;
      private float positive;
      private float neutral;

      public float getCompound() {
         return compound;
      }

      public float getNegative() {
         return negative;
      }

      public float getPositive() {
         return positive;
      }

      public float getNeutral() {
         return neutral;
      }
   }
}
