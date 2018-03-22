package musicanalysis.model.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import weka.classifiers.Evaluation;

import java.util.List;

@Document(collection = "classificationresults")
public class ClassificationResult {

   @Id
   @Field(Fields.ID)
   private ObjectId id;
   @Field(Fields.TEST_CASE)
   private ObjectId testCaseId;
   @Field(Fields.CONFIG)
   private Integer config;
   @Field(Fields.CLASSIFIER)
   private String classifier;
   private Double areaUnderROC;
   private Double weightedAreaUnderROC;
   private Double areaUnderPRC;
   private double[][] confusionMatrix;
   private Double weightedAreaUnderPRC;
   private Double coverageOfTestCasesByPredictedRegions;
   private Double sizeOfPredictedRegions;
   private Double incorrect;
   private Double pctIncorrect;
   private Double totalCost;
   private Double avgCost;
   private Double correct;
   private Double pctCorrect;
   private Double unclassified;
   private Double pctUnclassified;
   private Double errorRate;
   private Double kappa;
   private String revision;
   private Double correlationCoefficient;
   private Double meanAbsoluteError;
   private Double meanPriorAbsoluteError;
   private Double relativeAbsoluteError;
   private Double rootMeanSquaredError;
   private Double rootMeanPriorSquaredError;
   private Double rootRelativeSquaredError;
   private Double priorEntropy;
   private Double kbInformation;
   private Double kbMeanInformation;
   private Double kbRelativeInformation;
   private Double sfPriorEntropy;
   private Double sfMeanPriorEntropy;
   private Double sfSchemeEntropy;
   private Double sfMeanSchemeEntropy;
   private Double sfEntropyGain;
   private Double sfMeanEntropyGain;
   private Double numTruePositives;
   private Double truePositiveRate;
   private Double weightedTruePositiveRate;
   private Double numTrueNegatives;
   private Double trueNegativeRate;
   private Double weightedTrueNegativeRate;
   private Double numFalsePositives;
   private Double falsePositiveRate;
   private Double weightedFalsePositiveRate;
   private Double numFalseNegatives;
   private Double falseNegativeRate;
   private Double weightedFalseNegativeRate;
   private Double matthewsCorrelationCoefficient;
   private Double weightedMatthewsCorrelation;
   private Double recall;
   private Double weightedRecall;
   private Double precision;
   private Double weightedPrecision;
   private Double fMeasure;
   private Double weightedFMeasure;
   private Double unweightedMacroFmeasure;
   private Double unweightedMicroFmeasure;
   @Field(Fields.INSTANCES)
   private Double instances;
   @Field(Fields.ATTRIBUTES)
   private List<String> attributes;

   public ObjectId getId() {
      return id;
   }

   public void setId(ObjectId id) {
      this.id = id;
   }

   public ObjectId getTestCaseId() {
      return testCaseId;
   }

   public void setTestCaseId(ObjectId testCaseId) {
      this.testCaseId = testCaseId;
   }

   public void setEvaluation(Evaluation evaluation, int classIndex) {

      instances = evaluation.numInstances();
      areaUnderROC = evaluation.areaUnderROC(classIndex);
      weightedAreaUnderROC = evaluation.weightedAreaUnderROC();
      areaUnderPRC = evaluation.areaUnderPRC(classIndex);
      confusionMatrix = evaluation.confusionMatrix();
      weightedAreaUnderPRC = evaluation.weightedAreaUnderPRC();
      coverageOfTestCasesByPredictedRegions = evaluation.coverageOfTestCasesByPredictedRegions();
      sizeOfPredictedRegions = evaluation.sizeOfPredictedRegions();
      incorrect = evaluation.incorrect();
      pctIncorrect = evaluation.pctIncorrect();
      totalCost = evaluation.totalCost();
      avgCost = evaluation.avgCost();
      correct = evaluation.correct();
      pctCorrect = evaluation.pctCorrect();
      unclassified = evaluation.unclassified();
      pctUnclassified = evaluation.pctUnclassified();
      errorRate = evaluation.errorRate();
      kappa = evaluation.kappa();
      revision = evaluation.getRevision();
      meanAbsoluteError = evaluation.meanAbsoluteError();
      meanPriorAbsoluteError = evaluation.meanPriorAbsoluteError();
      rootMeanSquaredError = evaluation.rootMeanSquaredError();
      rootMeanPriorSquaredError = evaluation.rootMeanPriorSquaredError();
      rootRelativeSquaredError = evaluation.rootRelativeSquaredError();
      sfPriorEntropy = evaluation.SFPriorEntropy();
      sfMeanPriorEntropy = evaluation.SFMeanPriorEntropy();
      sfSchemeEntropy = evaluation.SFSchemeEntropy();
      sfMeanSchemeEntropy = evaluation.SFMeanSchemeEntropy();
      sfEntropyGain = evaluation.SFEntropyGain();
      sfMeanEntropyGain = evaluation.SFMeanEntropyGain();
      numTruePositives = evaluation.numTruePositives(classIndex);
      truePositiveRate = evaluation.truePositiveRate(classIndex);
      weightedTruePositiveRate = evaluation.weightedTruePositiveRate();
      numTrueNegatives = evaluation.numTrueNegatives(classIndex);
      trueNegativeRate = evaluation.trueNegativeRate(classIndex);
      weightedTrueNegativeRate = evaluation.weightedTrueNegativeRate();
      numFalsePositives = evaluation.numFalsePositives(classIndex);
      falsePositiveRate = evaluation.falsePositiveRate(classIndex);
      weightedFalsePositiveRate = evaluation.weightedFalsePositiveRate();
      numFalseNegatives = evaluation.numFalseNegatives(classIndex);
      falseNegativeRate = evaluation.falseNegativeRate(classIndex);
      weightedFalseNegativeRate = evaluation.weightedFalseNegativeRate();
      matthewsCorrelationCoefficient = evaluation.matthewsCorrelationCoefficient(classIndex);
      weightedMatthewsCorrelation = evaluation.weightedMatthewsCorrelation();
      recall = evaluation.recall(classIndex);
      weightedRecall = evaluation.weightedRecall();
      precision = evaluation.precision(classIndex);
      weightedPrecision = evaluation.weightedPrecision();
      fMeasure = evaluation.fMeasure(classIndex);
      weightedFMeasure = evaluation.weightedFMeasure();
      unweightedMacroFmeasure = evaluation.unweightedMacroFmeasure();
      unweightedMicroFmeasure = evaluation.unweightedMicroFmeasure();

      try {
         correlationCoefficient = evaluation.correlationCoefficient();
      } catch (Exception ex) {
         correlationCoefficient = null;
      }
      try {
         relativeAbsoluteError = evaluation.relativeAbsoluteError();
      } catch (Exception ex) {
         relativeAbsoluteError = null;
      }
      try {
         priorEntropy = evaluation.priorEntropy();
      } catch (Exception ex) {
         priorEntropy = null;
      }
      try {
         kbInformation = evaluation.KBInformation();
      } catch (Exception ex) {
         kbInformation = null;
      }
      try {
         kbMeanInformation = evaluation.KBMeanInformation();
      } catch (Exception ex) {
         kbMeanInformation = null;
      }
      try {
         kbRelativeInformation = evaluation.KBRelativeInformation();
      } catch (Exception ex) {
         kbRelativeInformation = null;
      }
   }

   public Double getAreaUnderROC() {
      return areaUnderROC;
   }

   public void setAreaUnderROC(Double areaUnderROC) {
      this.areaUnderROC = areaUnderROC;
   }

   public Double getWeightedAreaUnderROC() {
      return weightedAreaUnderROC;
   }

   public void setWeightedAreaUnderROC(Double weightedAreaUnderROC) {
      this.weightedAreaUnderROC = weightedAreaUnderROC;
   }

   public Double getAreaUnderPRC() {
      return areaUnderPRC;
   }

   public void setAreaUnderPRC(Double areaUnderPRC) {
      this.areaUnderPRC = areaUnderPRC;
   }

   public double[][] getConfusionMatrix() {
      return confusionMatrix;
   }

   public void setConfusionMatrix(double[][] confusionMatrix) {
      this.confusionMatrix = confusionMatrix;
   }

   public Double getWeightedAreaUnderPRC() {
      return weightedAreaUnderPRC;
   }

   public void setWeightedAreaUnderPRC(Double weightedAreaUnderPRC) {
      this.weightedAreaUnderPRC = weightedAreaUnderPRC;
   }

   public Double getCoverageOfTestCasesByPredictedRegions() {
      return coverageOfTestCasesByPredictedRegions;
   }

   public void setCoverageOfTestCasesByPredictedRegions(Double coverageOfTestCasesByPredictedRegions) {
      this.coverageOfTestCasesByPredictedRegions = coverageOfTestCasesByPredictedRegions;
   }

   public Double getSizeOfPredictedRegions() {
      return sizeOfPredictedRegions;
   }

   public void setSizeOfPredictedRegions(Double sizeOfPredictedRegions) {
      this.sizeOfPredictedRegions = sizeOfPredictedRegions;
   }

   public Double getIncorrect() {
      return incorrect;
   }

   public void setIncorrect(Double incorrect) {
      this.incorrect = incorrect;
   }

   public Double getPctIncorrect() {
      return pctIncorrect;
   }

   public void setPctIncorrect(Double pctIncorrect) {
      this.pctIncorrect = pctIncorrect;
   }

   public Double getTotalCost() {
      return totalCost;
   }

   public void setTotalCost(Double totalCost) {
      this.totalCost = totalCost;
   }

   public Double getAvgCost() {
      return avgCost;
   }

   public void setAvgCost(Double avgCost) {
      this.avgCost = avgCost;
   }

   public Double getCorrect() {
      return correct;
   }

   public void setCorrect(Double correct) {
      this.correct = correct;
   }

   public Double getPctCorrect() {
      return pctCorrect;
   }

   public void setPctCorrect(Double pctCorrect) {
      this.pctCorrect = pctCorrect;
   }

   public Double getUnclassified() {
      return unclassified;
   }

   public void setUnclassified(Double unclassified) {
      this.unclassified = unclassified;
   }

   public Double getPctUnclassified() {
      return pctUnclassified;
   }

   public void setPctUnclassified(Double pctUnclassified) {
      this.pctUnclassified = pctUnclassified;
   }

   public Double getErrorRate() {
      return errorRate;
   }

   public void setErrorRate(Double errorRate) {
      this.errorRate = errorRate;
   }

   public Double getKappa() {
      return kappa;
   }

   public void setKappa(Double kappa) {
      this.kappa = kappa;
   }

   public String getRevision() {
      return revision;
   }

   public void setRevision(String revision) {
      this.revision = revision;
   }

   public Double getCorrelationCoefficient() {
      return correlationCoefficient;
   }

   public void setCorrelationCoefficient(Double correlationCoefficient) {
      this.correlationCoefficient = correlationCoefficient;
   }

   public Double getMeanAbsoluteError() {
      return meanAbsoluteError;
   }

   public void setMeanAbsoluteError(Double meanAbsoluteError) {
      this.meanAbsoluteError = meanAbsoluteError;
   }

   public Double getMeanPriorAbsoluteError() {
      return meanPriorAbsoluteError;
   }

   public void setMeanPriorAbsoluteError(Double meanPriorAbsoluteError) {
      this.meanPriorAbsoluteError = meanPriorAbsoluteError;
   }

   public Double getRelativeAbsoluteError() {
      return relativeAbsoluteError;
   }

   public void setRelativeAbsoluteError(Double relativeAbsoluteError) {
      this.relativeAbsoluteError = relativeAbsoluteError;
   }

   public Double getRootMeanSquaredError() {
      return rootMeanSquaredError;
   }

   public void setRootMeanSquaredError(Double rootMeanSquaredError) {
      this.rootMeanSquaredError = rootMeanSquaredError;
   }

   public Double getRootMeanPriorSquaredError() {
      return rootMeanPriorSquaredError;
   }

   public void setRootMeanPriorSquaredError(Double rootMeanPriorSquaredError) {
      this.rootMeanPriorSquaredError = rootMeanPriorSquaredError;
   }

   public Double getRootRelativeSquaredError() {
      return rootRelativeSquaredError;
   }

   public void setRootRelativeSquaredError(Double rootRelativeSquaredError) {
      this.rootRelativeSquaredError = rootRelativeSquaredError;
   }

   public Double getPriorEntropy() {
      return priorEntropy;
   }

   public void setPriorEntropy(Double priorEntropy) {
      this.priorEntropy = priorEntropy;
   }

   public Double getKbInformation() {
      return kbInformation;
   }

   public void setKbInformation(Double kbInformation) {
      this.kbInformation = kbInformation;
   }

   public Double getKbMeanInformation() {
      return kbMeanInformation;
   }

   public void setKbMeanInformation(Double kbMeanInformation) {
      this.kbMeanInformation = kbMeanInformation;
   }

   public Double getKbRelativeInformation() {
      return kbRelativeInformation;
   }

   public void setKbRelativeInformation(Double kbRelativeInformation) {
      this.kbRelativeInformation = kbRelativeInformation;
   }

   public Double getSfPriorEntropy() {
      return sfPriorEntropy;
   }

   public void setSfPriorEntropy(Double sfPriorEntropy) {
      this.sfPriorEntropy = sfPriorEntropy;
   }

   public Double getSfMeanPriorEntropy() {
      return sfMeanPriorEntropy;
   }

   public void setSfMeanPriorEntropy(Double sfMeanPriorEntropy) {
      this.sfMeanPriorEntropy = sfMeanPriorEntropy;
   }

   public Double getSfSchemeEntropy() {
      return sfSchemeEntropy;
   }

   public void setSfSchemeEntropy(Double sfSchemeEntropy) {
      this.sfSchemeEntropy = sfSchemeEntropy;
   }

   public Double getSfMeanSchemeEntropy() {
      return sfMeanSchemeEntropy;
   }

   public void setSfMeanSchemeEntropy(Double sfMeanSchemeEntropy) {
      this.sfMeanSchemeEntropy = sfMeanSchemeEntropy;
   }

   public Double getSfEntropyGain() {
      return sfEntropyGain;
   }

   public void setSfEntropyGain(Double sfEntropyGain) {
      this.sfEntropyGain = sfEntropyGain;
   }

   public Double getSfMeanEntropyGain() {
      return sfMeanEntropyGain;
   }

   public void setSfMeanEntropyGain(Double sfMeanEntropyGain) {
      this.sfMeanEntropyGain = sfMeanEntropyGain;
   }

   public Double getNumTruePositives() {
      return numTruePositives;
   }

   public void setNumTruePositives(Double numTruePositives) {
      this.numTruePositives = numTruePositives;
   }

   public Double getTruePositiveRate() {
      return truePositiveRate;
   }

   public void setTruePositiveRate(Double truePositiveRate) {
      this.truePositiveRate = truePositiveRate;
   }

   public Double getWeightedTruePositiveRate() {
      return weightedTruePositiveRate;
   }

   public void setWeightedTruePositiveRate(Double weightedTruePositiveRate) {
      this.weightedTruePositiveRate = weightedTruePositiveRate;
   }

   public Double getNumTrueNegatives() {
      return numTrueNegatives;
   }

   public void setNumTrueNegatives(Double numTrueNegatives) {
      this.numTrueNegatives = numTrueNegatives;
   }

   public Double getTrueNegativeRate() {
      return trueNegativeRate;
   }

   public void setTrueNegativeRate(Double trueNegativeRate) {
      this.trueNegativeRate = trueNegativeRate;
   }

   public Double getWeightedTrueNegativeRate() {
      return weightedTrueNegativeRate;
   }

   public void setWeightedTrueNegativeRate(Double weightedTrueNegativeRate) {
      this.weightedTrueNegativeRate = weightedTrueNegativeRate;
   }

   public Double getNumFalsePositives() {
      return numFalsePositives;
   }

   public void setNumFalsePositives(Double numFalsePositives) {
      this.numFalsePositives = numFalsePositives;
   }

   public Double getFalsePositiveRate() {
      return falsePositiveRate;
   }

   public void setFalsePositiveRate(Double falsePositiveRate) {
      this.falsePositiveRate = falsePositiveRate;
   }

   public Double getWeightedFalsePositiveRate() {
      return weightedFalsePositiveRate;
   }

   public void setWeightedFalsePositiveRate(Double weightedFalsePositiveRate) {
      this.weightedFalsePositiveRate = weightedFalsePositiveRate;
   }

   public Double getNumFalseNegatives() {
      return numFalseNegatives;
   }

   public void setNumFalseNegatives(Double numFalseNegatives) {
      this.numFalseNegatives = numFalseNegatives;
   }

   public Double getFalseNegativeRate() {
      return falseNegativeRate;
   }

   public void setFalseNegativeRate(Double falseNegativeRate) {
      this.falseNegativeRate = falseNegativeRate;
   }

   public Double getWeightedFalseNegativeRate() {
      return weightedFalseNegativeRate;
   }

   public void setWeightedFalseNegativeRate(Double weightedFalseNegativeRate) {
      this.weightedFalseNegativeRate = weightedFalseNegativeRate;
   }

   public Double getMatthewsCorrelationCoefficient() {
      return matthewsCorrelationCoefficient;
   }

   public void setMatthewsCorrelationCoefficient(Double matthewsCorrelationCoefficient) {
      this.matthewsCorrelationCoefficient = matthewsCorrelationCoefficient;
   }

   public Double getWeightedMatthewsCorrelation() {
      return weightedMatthewsCorrelation;
   }

   public void setWeightedMatthewsCorrelation(Double weightedMatthewsCorrelation) {
      this.weightedMatthewsCorrelation = weightedMatthewsCorrelation;
   }

   public Double getRecall() {
      return recall;
   }

   public void setRecall(Double recall) {
      this.recall = recall;
   }

   public Double getWeightedRecall() {
      return weightedRecall;
   }

   public void setWeightedRecall(Double weightedRecall) {
      this.weightedRecall = weightedRecall;
   }

   public Double getPrecision() {
      return precision;
   }

   public void setPrecision(Double precision) {
      this.precision = precision;
   }

   public Double getWeightedPrecision() {
      return weightedPrecision;
   }

   public void setWeightedPrecision(Double weightedPrecision) {
      this.weightedPrecision = weightedPrecision;
   }

   public Double getfMeasure() {
      return fMeasure;
   }

   public void setfMeasure(Double fMeasure) {
      this.fMeasure = fMeasure;
   }

   public Double getWeightedFMeasure() {
      return weightedFMeasure;
   }

   public void setWeightedFMeasure(Double weightedFMeasure) {
      this.weightedFMeasure = weightedFMeasure;
   }

   public Double getUnweightedMacroFmeasure() {
      return unweightedMacroFmeasure;
   }

   public void setUnweightedMacroFmeasure(Double unweightedMacroFmeasure) {
      this.unweightedMacroFmeasure = unweightedMacroFmeasure;
   }

   public Double getUnweightedMicroFmeasure() {
      return unweightedMicroFmeasure;
   }

   public void setUnweightedMicroFmeasure(Double unweightedMicroFmeasure) {
      this.unweightedMicroFmeasure = unweightedMicroFmeasure;
   }

   public Integer getConfig() {
      return config;
   }

   public void setConfig(Integer config) {
      this.config = config;
   }

   public String getClassifier() {
      return classifier;
   }

   public void setClassifier(String classifier) {
      this.classifier = classifier;
   }

   public Double getInstances() {
      return instances;
   }

   public void setInstances(Double instances) {
      this.instances = instances;
   }

   public List<String> getAttributes() {
      return attributes;
   }

   public void setAttributes(List<String> attributes) {
      this.attributes = attributes;
   }

   public class Fields {

      public static final String ID = "_id";
      public static final String TEST_CASE = "test_case";
      public static final String CONFIG = "config";
      public static final String CLASSIFIER = "classifier";
      public static final String INSTANCES = "instances";
      public static final String ATTRIBUTES = "attributes";
      public static final String FILTERED = "filtered";

      private Fields() {

      }
   }
}
