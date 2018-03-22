package musicanalysis.model.music;

import java.util.ArrayList;
import java.util.List;

public class Config {

   public final AudioConfig audioConfig = new AudioConfig();
   public final LexicalConfig lexicalConfig = new LexicalConfig();
   public final LinguisticConfig linguisticConfig = new LinguisticConfig();
   public final SemanticConfig semanticConfig = new SemanticConfig();
   public final SyntacticConfig syntacticConfig = new SyntacticConfig();

   public Config() {

   }

   public Config(final boolean defaultValue) {

      audioConfig.enableAudioFeatures = defaultValue;

      lexicalConfig.enableBagOfWordsFeatures = defaultValue;
      lexicalConfig.enableTextStyleFeatures = defaultValue;

      linguisticConfig.enableEchoismFeatures = defaultValue;
      linguisticConfig.enableRepetitiveStructureFeatures = defaultValue;
      linguisticConfig.enableSlangFeatures = defaultValue;
      linguisticConfig.enableRhymeFeatures = defaultValue;

      semanticConfig.enableAFINNFeatures = defaultValue;
      semanticConfig.enableRegressiveImageFeatures = defaultValue;
      semanticConfig.enableOpinionFeatures = defaultValue;
      semanticConfig.enableSentiStrengthFeatures = defaultValue;
      semanticConfig.enableVADERFeatures = defaultValue;

      syntacticConfig.enableSuperChunkTagFeatures = defaultValue;
      syntacticConfig.enablePronounFeatures = defaultValue;
      syntacticConfig.enablePastTenseRatioFeatures = defaultValue;
      syntacticConfig.enablePOSTagFeatures = defaultValue;
   }

   public static Config decode(final int code) {

      final Config config = new Config();
      final String binary = Integer.toBinaryString(code);
      final char[] positions = binary.toCharArray();

      int length = positions.length;

      config.audioConfig.enableAudioFeatures = length > 0 && positions[--length] == '1';

      config.lexicalConfig.enableBagOfWordsFeatures = length > 0 && positions[--length] == '1';
      config.lexicalConfig.enableTextStyleFeatures = length > 0 && positions[--length] == '1';

      config.linguisticConfig.enableEchoismFeatures = length > 0 && positions[--length] == '1';
      config.linguisticConfig.enableRepetitiveStructureFeatures = length > 0 && positions[--length] == '1';
      config.linguisticConfig.enableSlangFeatures = length > 0 && positions[--length] == '1';
      config.linguisticConfig.enableRhymeFeatures = length > 0 && positions[--length] == '1';

      config.semanticConfig.enableAFINNFeatures = length > 0 && positions[--length] == '1';
      config.semanticConfig.enableRegressiveImageFeatures = length > 0 && positions[--length] == '1';
      config.semanticConfig.enableOpinionFeatures = length > 0 && positions[--length] == '1';
      config.semanticConfig.enableSentiStrengthFeatures = length > 0 && positions[--length] == '1';
      config.semanticConfig.enableVADERFeatures = length > 0 && positions[--length] == '1';

      config.syntacticConfig.enableSuperChunkTagFeatures = length > 0 && positions[--length] == '1';
      config.syntacticConfig.enablePronounFeatures = length > 0 && positions[--length] == '1';
      config.syntacticConfig.enablePastTenseRatioFeatures = length > 0 && positions[--length] == '1';
      config.syntacticConfig.enablePOSTagFeatures = length > 0 && positions[--length] == '1';

      return config;
   }

   public static String name(final int code) {

      final List<String> types = new ArrayList<>();

      final Config config = Config.decode(code);

      if (config.audioConfig.enableAudioFeatures) {
         types.add("AU");
      }

      if (config.lexicalConfig.enableBagOfWordsFeatures
            && config.lexicalConfig.enableTextStyleFeatures) {
         types.add("LX");
      } else {
         String name = "LX:";
         if (config.lexicalConfig.enableBagOfWordsFeatures) name = name.concat("B");
         if (config.lexicalConfig.enableTextStyleFeatures) name = name.concat("T");
         if (!name.equalsIgnoreCase("LX:")) types.add(name);
      }

      if (config.linguisticConfig.enableEchoismFeatures &&
            config.linguisticConfig.enableRepetitiveStructureFeatures &&
            config.linguisticConfig.enableSlangFeatures &&
            config.linguisticConfig.enableRhymeFeatures) {
         types.add("LI");
      } else {
         String name = "LI:";
         if (config.linguisticConfig.enableEchoismFeatures) name = name.concat("E");
         if (config.linguisticConfig.enableRepetitiveStructureFeatures) name = name.concat("R");
         if (config.linguisticConfig.enableSlangFeatures) name = name.concat("S");
         if (config.linguisticConfig.enableRhymeFeatures) name = name.concat("Y");
         if (!name.equalsIgnoreCase("LI:")) types.add(name);
      }

      if (config.semanticConfig.enableAFINNFeatures &&
            config.semanticConfig.enableRegressiveImageFeatures &&
            config.semanticConfig.enableOpinionFeatures &&
            config.semanticConfig.enableSentiStrengthFeatures &&
            config.semanticConfig.enableVADERFeatures) {
         types.add("SE");
      } else {
         String name = "SE:";
         if (config.semanticConfig.enableAFINNFeatures) name = name.concat("A");
         if (config.semanticConfig.enableRegressiveImageFeatures) name = name.concat("R");
         if (config.semanticConfig.enableOpinionFeatures) name = name.concat("O");
         if (config.semanticConfig.enableSentiStrengthFeatures) name = name.concat("S");
         if (config.semanticConfig.enableVADERFeatures) name = name.concat("V");
         if (!name.equalsIgnoreCase("SE:")) types.add(name);
      }

      if (config.syntacticConfig.enableSuperChunkTagFeatures &&
            config.syntacticConfig.enablePronounFeatures &&
            config.syntacticConfig.enablePastTenseRatioFeatures &&
            config.syntacticConfig.enablePOSTagFeatures) {
         types.add("SY");
      } else {
         String name = "SY:";
         if (config.syntacticConfig.enableSuperChunkTagFeatures) name = name.concat("C");
         if (config.syntacticConfig.enablePronounFeatures) name = name.concat("N");
         if (config.syntacticConfig.enablePastTenseRatioFeatures) name = name.concat("T");
         if (config.syntacticConfig.enablePOSTagFeatures) name = name.concat("P");
         if (!name.equalsIgnoreCase("SY:")) types.add(name);
      }

      if (types.contains("AU") && types.contains("LX") && types.contains("LI")
            && types.contains("SE") && types.contains("SY")) {
         types.clear();
         types.add("ALL");
      }

      final StringBuilder builder = new StringBuilder();
      types.forEach(t -> builder.append(t).append("+"));
      builder.delete(builder.length() - 1, builder.length());

      return builder.toString();
   }

   public Integer encode() {

      Integer encoding = 0;
      final List<Boolean> values = getValues();
      for (int i = 0; i < values.size(); i++) {
         if (values.get(i)) {
            encoding += (int) java.lang.Math.pow(2, i);
         }
      }
      return encoding;
   }

   private List<Boolean> getValues() {

      final List<Boolean> values = new ArrayList<>();

      values.add(audioConfig.enableAudioFeatures);

      values.add(lexicalConfig.enableBagOfWordsFeatures);
      values.add(lexicalConfig.enableTextStyleFeatures);

      values.add(linguisticConfig.enableEchoismFeatures);
      values.add(linguisticConfig.enableRepetitiveStructureFeatures);
      values.add(linguisticConfig.enableSlangFeatures);
      values.add(linguisticConfig.enableRhymeFeatures);

      values.add(semanticConfig.enableAFINNFeatures);
      values.add(semanticConfig.enableRegressiveImageFeatures);
      values.add(semanticConfig.enableOpinionFeatures);
      values.add(semanticConfig.enableSentiStrengthFeatures);
      values.add(semanticConfig.enableVADERFeatures);

      values.add(syntacticConfig.enableSuperChunkTagFeatures);
      values.add(syntacticConfig.enablePronounFeatures);
      values.add(syntacticConfig.enablePastTenseRatioFeatures);
      values.add(syntacticConfig.enablePOSTagFeatures);

      return values;
   }

   public void setAudioFeatures(final boolean value) {

      audioConfig.enableAudioFeatures = value;
   }

   public void setLexicalFeatures(final boolean value) {

      lexicalConfig.enableBagOfWordsFeatures = value;
      lexicalConfig.enableTextStyleFeatures = value;
   }

   public void setLinguisticFeatures(final boolean value) {

      linguisticConfig.enableEchoismFeatures = value;
      linguisticConfig.enableRepetitiveStructureFeatures = value;
      linguisticConfig.enableSlangFeatures = value;
      linguisticConfig.enableRhymeFeatures = value;
   }

   public void setSemanticFeatures(final boolean value) {

      semanticConfig.enableAFINNFeatures = value;
      semanticConfig.enableRegressiveImageFeatures = value;
      semanticConfig.enableOpinionFeatures = value;
      semanticConfig.enableSentiStrengthFeatures = value;
      semanticConfig.enableVADERFeatures = value;
   }

   public void setSyntacticFeatures(final boolean value) {

      syntacticConfig.enableSuperChunkTagFeatures = value;
      syntacticConfig.enablePronounFeatures = value;
      syntacticConfig.enablePastTenseRatioFeatures = value;
      syntacticConfig.enablePOSTagFeatures = value;
   }

   @Override
   public String toString() {

      return this.encode().toString();
   }

   public class AudioConfig {
      public boolean enableAudioFeatures = true;
   }

   public class LexicalConfig {
      public boolean enableBagOfWordsFeatures = true;
      public boolean enableTextStyleFeatures = true;
   }

   public class LinguisticConfig {
      public boolean enableEchoismFeatures = true;
      public boolean enableRepetitiveStructureFeatures = true;
      public boolean enableSlangFeatures = true;
      public boolean enableRhymeFeatures = true;
   }

   public class SemanticConfig {
      public boolean enableAFINNFeatures = true;
      public boolean enableRegressiveImageFeatures = true;
      public boolean enableOpinionFeatures = true;
      public boolean enableSentiStrengthFeatures = true;
      public boolean enableVADERFeatures = true;
   }

   public class SyntacticConfig {
      public boolean enableSuperChunkTagFeatures = true;
      public boolean enablePronounFeatures = true;
      public boolean enablePastTenseRatioFeatures = true;
      public boolean enablePOSTagFeatures = true;
   }

   public static List<Config> getAllCombinations() {

      final List<Config> configs = new ArrayList<>();

      final int categories = 5;
      for (int i = 1; i < Math.pow(2, categories); i++) {
         final Config config = new Config(false);
         for (int j = 0; j < categories; j++) {
            if ((i & (int) Math.pow(2, j)) != 0) {
               switch (j) {
                  case 0:
                     config.setAudioFeatures(true);
                     break;
                  case 1:
                     config.setLexicalFeatures(true);
                     break;
                  case 2:
                     config.setLinguisticFeatures(true);
                     break;
                  case 3:
                     config.setSemanticFeatures(true);
                     break;
                  case 4:
                     config.setSyntacticFeatures(true);
                     break;
               }
            }
         }

         configs.add(config);
      }

      return configs;
   }
}
