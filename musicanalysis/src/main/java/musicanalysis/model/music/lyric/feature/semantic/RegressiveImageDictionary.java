package musicanalysis.model.music.lyric.feature.semantic;

import musicanalysis.model.common.Path;
import musicanalysis.model.data.RegressiveImage;
import musicanalysis.model.settings.Settings;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.mutable.MutableInt;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * See: http://www.kovcomp.co.uk/wordstat/RID.html
 */
class RegressiveImageDictionary {

   private Document elements;
   private Set<String> excludedTokens;

   private RegressiveImageDictionary() {

      initialize();
   }

   public static RegressiveImageDictionary getInstance() {

      return InstanceHolder.INSTANCE;
   }

   private void initialize() {

      try {

         final String xml = parseCategoriesToXML();
         final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
         final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
         elements = docBuilder.parse(new InputSource(new StringReader(xml)));

         excludedTokens = convertExcludedTokens();

      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   private Set<String> convertExcludedTokens()
         throws IOException {

      final Set<String> excluded = new HashSet<>();

      final List<String> lines = FileUtils.readLines(new File(Path.getInstance().getResourcePath(Settings.RID_EXCLUDED_PATH)), Charset.forName("UTF-8"));
      lines.forEach(l -> {
         String item = "^" + l.trim();
         item = item.endsWith("*") ? item.replace("*", ".*$") : item.concat("$");
         excluded.add(item);
      });

      return excluded;
   }

   private String parseCategoriesToXML()
         throws IOException, URISyntaxException {

      final StringBuilder xml = new StringBuilder();
      final List<String> lines = FileUtils.readLines(new File(Path.getInstance().getResourcePath(Settings.RID_CATEGORY_PATH)), Charset.forName("UTF-8"));
      final Stack<Integer> levels = new Stack<>();
      int id = 0;

      append(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      append(xml, "<groups>");
      for (int i = 0; i < lines.size(); i++) {
         final String line = lines.get(i);

         if (!lines.get(i).endsWith(")")) {

            final int newLevel = getLevel(line);
            while (levels.size() > 0 && newLevel <= levels.peek()) {
               levels.pop();
               append(xml, "</group>");
            }
            levels.add(newLevel);

            append(xml, "<group name=\"" + line.trim() + "\">");
         } else {
            String item = "^" + line.replace("(1)", "").trim();
            item = item.endsWith("*") ? item.replace("*", ".*$") : item.concat("$");
            append(xml, "<item id=\"" + ++id + "\">" + item + "</item>");
         }
      }

      while (levels.size() > 0) {
         levels.pop();
         append(xml, "</group>");
      }

      append(xml, "</groups>");

      return xml.toString();
   }

   private void append(final StringBuilder builder, final String line) {

      builder.append(line);
      builder.append(System.lineSeparator());
   }

   private int getLevel(String line) {

      int tabs = 0;

      while (line.startsWith("\t")) {
         tabs++;
         line = line.replaceAll("^\t", "");
      }

      return tabs;
   }

   public synchronized RegressiveImage getRegressiveImage(final List<String> words) {

      final RegressiveImage image = new RegressiveImage();
      final Map<String, Integer> topLevelCategories = new HashMap<>();

      try {

         final List<String> validTokens = excludeInvalidTokens(words);
         final Map<String, MutableInt> tokenStatistics = countTokens(validTokens);
         final Map<String, MutableInt> categoryStatistics = countCategories(tokenStatistics);

         for (final String category : getCategoryNames()) {
            int value = 0;
            if (categoryStatistics.containsKey(category)) {
               value = categoryStatistics.get(category).intValue();
            }
            topLevelCategories.put(category, value);
         }

      } catch (XPathExpressionException e) {
         e.printStackTrace();
      }

      image.setWordCount(words.size());
      image.setPrimary(topLevelCategories.get(Categories.PRIMARY));
      image.setPrimaryNeed(topLevelCategories.get(Categories.PRIMARY_NEED));
      image.setPrimaryNeedOrality(topLevelCategories.get(Categories.PRIMARY_NEED_ORALITY));
      image.setPrimaryNeedAnality(topLevelCategories.get(Categories.PRIMARY_NEED_ANALITY));
      image.setPrimaryNeedSex(topLevelCategories.get(Categories.PRIMARY_NEED_SEX));
      image.setPrimarySensation(topLevelCategories.get(Categories.PRIMARY_SENSATION));
      image.setPrimarySensationGeneralSensation(topLevelCategories.get(Categories.PRIMARY_SENSATION_GENERAL_SENSATION));
      image.setPrimarySensationTouch(topLevelCategories.get(Categories.PRIMARY_SENSATION_TOUCH));
      image.setPrimarySensationTaste(topLevelCategories.get(Categories.PRIMARY_SENSATION_TASTE));
      image.setPrimarySensationOdor(topLevelCategories.get(Categories.PRIMARY_SENSATION_ODOR));
      image.setPrimarySensationSound(topLevelCategories.get(Categories.PRIMARY_SENSATION_SOUND));
      image.setPrimarySensationVision(topLevelCategories.get(Categories.PRIMARY_SENSATION_VISION));
      image.setPrimarySensationCold(topLevelCategories.get(Categories.PRIMARY_SENSATION_COLD));
      image.setPrimarySensationHard(topLevelCategories.get(Categories.PRIMARY_SENSATION_HARD));
      image.setPrimarySensationSoft(topLevelCategories.get(Categories.PRIMARY_SENSATION_SOFT));
      image.setPrimaryDefensiveSymbol(topLevelCategories.get(Categories.PRIMARY_DEFENSIVE_SYMBOL));
      image.setPrimaryDefensiveSymbolPassivity(topLevelCategories.get(Categories.PRIMARY_DEFENSIVE_SYMBOL_PASSIVITY));
      image.setPrimaryDefensiveSymbolVoyage(topLevelCategories.get(Categories.PRIMARY_DEFENSIVE_SYMBOL_VOYAGE));
      image.setPrimaryDefensiveSymbolRandomMovement(topLevelCategories.get(Categories.PRIMARY_DEFENSIVE_SYMBOL_RANDOM_MOVEMENT));
      image.setPrimaryDefensiveSymbolDiffusion(topLevelCategories.get(Categories.PRIMARY_DEFENSIVE_SYMBOL_DIFFUSION));
      image.setPrimaryDefensiveSymbolChaos(topLevelCategories.get(Categories.PRIMARY_DEFENSIVE_SYMBOL_CHAOS));
      image.setPrimaryRegressiveCognition(topLevelCategories.get(Categories.PRIMARY_REGRESSIVE_COGNITION));
      image.setPrimaryRegressiveCognitionUnknown(topLevelCategories.get(Categories.PRIMARY_REGRESSIVE_COGNITION_UNKNOWN));
      image.setPrimaryRegressiveCognitionTimelessness(topLevelCategories.get(Categories.PRIMARY_REGRESSIVE_COGNITION_TIMELESSNESS));
      image.setPrimaryRegressiveCognitionConsciousnessAlternation(topLevelCategories
            .get(Categories.PRIMARY_REGRESSIVE_COGNITION_CONSCIOUSNESS_ALTERNATION));
      image.setPrimaryRegressiveCognitionBrinkPassage(topLevelCategories.get(Categories.PRIMARY_REGRESSIVE_COGNITION_BRINK_PASSAGE));
      image.setPrimaryRegressiveCognitionNarcissism(topLevelCategories.get(Categories.PRIMARY_REGRESSIVE_COGNITION_NARCISSISM));
      image.setPrimaryRegressiveCognitionConcreteness(topLevelCategories.get(Categories.PRIMARY_REGRESSIVE_COGNITION_CONCRETENESS));
      image.setPrimaryIcarianImagery(topLevelCategories.get(Categories.PRIMARY_ICARIAN_IMAGERY));
      image.setPrimaryIcarianImageryAscend(topLevelCategories.get(Categories.PRIMARY_ICARIAN_IMAGERY_ASCEND));
      image.setPrimaryIcarianImageryHeight(topLevelCategories.get(Categories.PRIMARY_ICARIAN_IMAGERY_HEIGHT));
      image.setPrimaryIcarianImageryDescent(topLevelCategories.get(Categories.PRIMARY_ICARIAN_IMAGERY_DESCENT));
      image.setPrimaryIcarianImageryDepth(topLevelCategories.get(Categories.PRIMARY_ICARIAN_IMAGERY_DEPTH));
      image.setPrimaryIcarianImageryFire(topLevelCategories.get(Categories.PRIMARY_ICARIAN_IMAGERY_FIRE));
      image.setPrimaryIcarianImageryWater(topLevelCategories.get(Categories.PRIMARY_ICARIAN_IMAGERY_WATER));
      image.setSecondary(topLevelCategories.get(Categories.SECONDARY));
      image.setSecondaryAbstraction(topLevelCategories.get(Categories.SECONDARY_ABSTRACTION));
      image.setSecondarySocialBehavior(topLevelCategories.get(Categories.SECONDARY_SOCIAL_BEHAVIOR));
      image.setSecondaryInstrumentalBehavior(topLevelCategories.get(Categories.SECONDARY_INSTRUMENTAL_BEHAVIOR));
      image.setSecondaryRestraint(topLevelCategories.get(Categories.SECONDARY_RESTRAINT));
      image.setSecondaryOrder(topLevelCategories.get(Categories.SECONDARY_ORDER));
      image.setSecondaryTemporalReferences(topLevelCategories.get(Categories.SECONDARY_TEMPORAL_REFERENCES));
      image.setSecondaryMoralImperative(topLevelCategories.get(Categories.SECONDARY_MORAL_IMPERATIVE));
      image.setEmotions(topLevelCategories.get(Categories.EMOTIONS));
      image.setEmotionsPositiveAffect(topLevelCategories.get(Categories.EMOTIONS_POSITIVE_AFFECT));
      image.setEmotionsAnxiety(topLevelCategories.get(Categories.EMOTIONS_ANXIETY));
      image.setEmotionsSadness(topLevelCategories.get(Categories.EMOTIONS_SADNESS));
      image.setEmotionsAffection(topLevelCategories.get(Categories.EMOTIONS_AFFECTION));
      image.setEmotionsAggression(topLevelCategories.get(Categories.EMOTIONS_AGGRESSION));
      image.setEmotionsExpressiveBehavior(topLevelCategories.get(Categories.EMOTIONS_EXPRESSIVE_BEHAVIOR));
      image.setEmotionsGlory(topLevelCategories.get(Categories.EMOTIONS_GLORY));

      return image;
   }

   private Map<String, MutableInt> countTokens(final List<String> words)
         throws XPathExpressionException {

      final Map<String, MutableInt> counts = new HashMap<>();

      final XPath xPath = XPathFactory.newInstance().newXPath();
      final NodeList nodes = (NodeList) xPath.compile("//item").evaluate(elements, XPathConstants.NODESET);

      for (final String word : words) {
         Node categoryNode = null;
         for (int i = 0; i < nodes.getLength(); i++) {
            final Node node = nodes.item(i);
            // do not count words twice within a category group
            if (categoryNode == null || !categoryNode.equals(node.getParentNode())) {
               final Pattern pattern = Pattern.compile(node.getTextContent(), Pattern.CASE_INSENSITIVE);
               final Matcher matcher = pattern.matcher(word);
               if (matcher.matches()) {
                  final String id = node.getAttributes().getNamedItem("id").getNodeValue();
                  if (!counts.containsKey(id)) {
                     counts.put(id, new MutableInt(1));
                  } else {
                     counts.get(id).increment();
                  }

                  // do not count words twice within a category group
                  categoryNode = node.getParentNode();
               }
            }
         }
      }

      return counts;
   }

   private Map<String, MutableInt> countCategories(final Map<String, MutableInt> tokenCount)
         throws XPathExpressionException {

      final Map<String, MutableInt> categories = new HashMap<>();

      final XPath xPath = XPathFactory.newInstance().newXPath();
      for (final String id : tokenCount.keySet()) {
         final Node item = (Node) xPath.compile(String.format("//item[@id='%s']", id)).evaluate(elements, XPathConstants.NODE);
         Node subcategory = item.getParentNode();
         while (subcategory != null && subcategory.hasAttributes()) {
            final String name = subcategory.getAttributes().getNamedItem("name").getNodeValue();
            if (!categories.containsKey(name)) {
               categories.put(name, new MutableInt(tokenCount.get(id)));
            } else {
               categories.get(name).add(tokenCount.get(id));
            }
            subcategory = subcategory.getParentNode();
         }
      }

      return categories;
   }

   private List<String> excludeInvalidTokens(final List<String> words) {

      final List<String> validTokens = new ArrayList<>(words);

      for (final String excluded : excludedTokens) {
         final Pattern pattern = Pattern.compile(excluded, Pattern.CASE_INSENSITIVE);
         for (final String word : words) {
            final Matcher matcher = pattern.matcher(word);
            if (matcher.matches()) {
               validTokens.remove(word);
               break;
            }
         }
      }

      return validTokens;
   }

   private List<String> getCategoryNames() {

      final List<String> categoryNames = new ArrayList<>();

      categoryNames.add(Categories.PRIMARY);

      categoryNames.add(Categories.PRIMARY_NEED);
      categoryNames.add(Categories.PRIMARY_NEED_ORALITY);
      categoryNames.add(Categories.PRIMARY_NEED_ANALITY);
      categoryNames.add(Categories.PRIMARY_NEED_SEX);

      categoryNames.add(Categories.PRIMARY_SENSATION);
      categoryNames.add(Categories.PRIMARY_SENSATION_TOUCH);
      categoryNames.add(Categories.PRIMARY_SENSATION_TASTE);
      categoryNames.add(Categories.PRIMARY_SENSATION_ODOR);
      categoryNames.add(Categories.PRIMARY_SENSATION_GENERAL_SENSATION);
      categoryNames.add(Categories.PRIMARY_SENSATION_SOUND);
      categoryNames.add(Categories.PRIMARY_SENSATION_VISION);
      categoryNames.add(Categories.PRIMARY_SENSATION_COLD);
      categoryNames.add(Categories.PRIMARY_SENSATION_HARD);
      categoryNames.add(Categories.PRIMARY_SENSATION_SOFT);

      categoryNames.add(Categories.PRIMARY_DEFENSIVE_SYMBOL);
      categoryNames.add(Categories.PRIMARY_DEFENSIVE_SYMBOL_PASSIVITY);
      categoryNames.add(Categories.PRIMARY_DEFENSIVE_SYMBOL_VOYAGE);
      categoryNames.add(Categories.PRIMARY_DEFENSIVE_SYMBOL_RANDOM_MOVEMENT);
      categoryNames.add(Categories.PRIMARY_DEFENSIVE_SYMBOL_DIFFUSION);
      categoryNames.add(Categories.PRIMARY_DEFENSIVE_SYMBOL_CHAOS);

      categoryNames.add(Categories.PRIMARY_REGRESSIVE_COGNITION);
      categoryNames.add(Categories.PRIMARY_REGRESSIVE_COGNITION_UNKNOWN);
      categoryNames.add(Categories.PRIMARY_REGRESSIVE_COGNITION_TIMELESSNESS);
      categoryNames.add(Categories.PRIMARY_REGRESSIVE_COGNITION_CONSCIOUSNESS_ALTERNATION);
      categoryNames.add(Categories.PRIMARY_REGRESSIVE_COGNITION_BRINK_PASSAGE);
      categoryNames.add(Categories.PRIMARY_REGRESSIVE_COGNITION_NARCISSISM);
      categoryNames.add(Categories.PRIMARY_REGRESSIVE_COGNITION_CONCRETENESS);

      categoryNames.add(Categories.PRIMARY_ICARIAN_IMAGERY);
      categoryNames.add(Categories.PRIMARY_ICARIAN_IMAGERY_ASCEND);
      categoryNames.add(Categories.PRIMARY_ICARIAN_IMAGERY_HEIGHT);
      categoryNames.add(Categories.PRIMARY_ICARIAN_IMAGERY_DESCENT);
      categoryNames.add(Categories.PRIMARY_ICARIAN_IMAGERY_DEPTH);
      categoryNames.add(Categories.PRIMARY_ICARIAN_IMAGERY_FIRE);
      categoryNames.add(Categories.PRIMARY_ICARIAN_IMAGERY_WATER);

      categoryNames.add(Categories.SECONDARY);

      categoryNames.add(Categories.SECONDARY_ABSTRACTION);
      categoryNames.add(Categories.SECONDARY_SOCIAL_BEHAVIOR);
      categoryNames.add(Categories.SECONDARY_INSTRUMENTAL_BEHAVIOR);
      categoryNames.add(Categories.SECONDARY_RESTRAINT);
      categoryNames.add(Categories.SECONDARY_ORDER);
      categoryNames.add(Categories.SECONDARY_TEMPORAL_REFERENCES);
      categoryNames.add(Categories.SECONDARY_MORAL_IMPERATIVE);

      categoryNames.add(Categories.EMOTIONS);

      categoryNames.add(Categories.EMOTIONS_POSITIVE_AFFECT);
      categoryNames.add(Categories.EMOTIONS_ANXIETY);
      categoryNames.add(Categories.EMOTIONS_SADNESS);
      categoryNames.add(Categories.EMOTIONS_AFFECTION);
      categoryNames.add(Categories.EMOTIONS_AGGRESSION);
      categoryNames.add(Categories.EMOTIONS_EXPRESSIVE_BEHAVIOR);
      categoryNames.add(Categories.EMOTIONS_GLORY);

      return categoryNames;
   }

   private static class InstanceHolder {

      static final RegressiveImageDictionary INSTANCE = new RegressiveImageDictionary();
   }

   public class Categories {

      public static final String PRIMARY = "PRIMARY";

      public static final String PRIMARY_NEED = "NEED";
      public static final String PRIMARY_NEED_ORALITY = "ORALITY";
      public static final String PRIMARY_NEED_ANALITY = "ANALITY";
      public static final String PRIMARY_NEED_SEX = "SEX";

      public static final String PRIMARY_SENSATION = "SENSATION";
      public static final String PRIMARY_SENSATION_TOUCH = "TOUCH";
      public static final String PRIMARY_SENSATION_TASTE = "TASTE";
      public static final String PRIMARY_SENSATION_ODOR = "ODOR";
      public static final String PRIMARY_SENSATION_GENERAL_SENSATION = "GEN_SENSATION";
      public static final String PRIMARY_SENSATION_SOUND = "SOUND";
      public static final String PRIMARY_SENSATION_VISION = "VISION";
      public static final String PRIMARY_SENSATION_COLD = "COLD";
      public static final String PRIMARY_SENSATION_HARD = "HARD";
      public static final String PRIMARY_SENSATION_SOFT = "SOFT";

      public static final String PRIMARY_DEFENSIVE_SYMBOL = "DEFENSIVE_SYMBOL";
      public static final String PRIMARY_DEFENSIVE_SYMBOL_PASSIVITY = "PASSIVITY";
      public static final String PRIMARY_DEFENSIVE_SYMBOL_VOYAGE = "VOYAGE";
      public static final String PRIMARY_DEFENSIVE_SYMBOL_RANDOM_MOVEMENT = "RANDOM MOVEMENT";
      public static final String PRIMARY_DEFENSIVE_SYMBOL_DIFFUSION = "DIFFUSION";
      public static final String PRIMARY_DEFENSIVE_SYMBOL_CHAOS = "CHAOS";

      public static final String PRIMARY_REGRESSIVE_COGNITION = "REGR_KNOL";
      public static final String PRIMARY_REGRESSIVE_COGNITION_UNKNOWN = "UNKNOW";
      public static final String PRIMARY_REGRESSIVE_COGNITION_TIMELESSNESS = "TIMELESSNES";
      public static final String PRIMARY_REGRESSIVE_COGNITION_CONSCIOUSNESS_ALTERNATION = "COUNSCIOUS";
      public static final String PRIMARY_REGRESSIVE_COGNITION_BRINK_PASSAGE = "BRINK-PASSAGE";
      public static final String PRIMARY_REGRESSIVE_COGNITION_NARCISSISM = "NARCISSISM";
      public static final String PRIMARY_REGRESSIVE_COGNITION_CONCRETENESS = "CONCRETENESS";

      public static final String PRIMARY_ICARIAN_IMAGERY = "ICARIAN_IM";
      public static final String PRIMARY_ICARIAN_IMAGERY_ASCEND = "ASCEND";
      public static final String PRIMARY_ICARIAN_IMAGERY_HEIGHT = "HEIGHT";
      public static final String PRIMARY_ICARIAN_IMAGERY_DESCENT = "DESCENT";
      public static final String PRIMARY_ICARIAN_IMAGERY_DEPTH = "DEPTH";
      public static final String PRIMARY_ICARIAN_IMAGERY_FIRE = "FIRE";
      public static final String PRIMARY_ICARIAN_IMAGERY_WATER = "WATER";

      public static final String SECONDARY = "SECONDARY";

      public static final String SECONDARY_ABSTRACTION = "ABSTRACT_TOUGHT";
      public static final String SECONDARY_SOCIAL_BEHAVIOR = "SOCIAL_BEHAVIOR";
      public static final String SECONDARY_INSTRUMENTAL_BEHAVIOR = "INSTRU_BEHAVIOR";
      public static final String SECONDARY_RESTRAINT = "RESTRAINT";
      public static final String SECONDARY_ORDER = "ORDER";
      public static final String SECONDARY_TEMPORAL_REFERENCES = "TEMPORAL_REPERE";
      public static final String SECONDARY_MORAL_IMPERATIVE = "MORAL_IMPERATIVE";

      public static final String EMOTIONS = "EMOTIONS";

      public static final String EMOTIONS_POSITIVE_AFFECT = "POSITIVE_AFFECT";
      public static final String EMOTIONS_ANXIETY = "ANXIETY";
      public static final String EMOTIONS_SADNESS = "SADNESS";
      public static final String EMOTIONS_AFFECTION = "AFFECTION";
      public static final String EMOTIONS_AGGRESSION = "AGGRESSION";
      public static final String EMOTIONS_EXPRESSIVE_BEHAVIOR = "EXPRESSIVE_BEH";
      public static final String EMOTIONS_GLORY = "GLORY";

      private Categories() {

      }
   }
}
