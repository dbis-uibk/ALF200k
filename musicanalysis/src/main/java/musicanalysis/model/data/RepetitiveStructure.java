package musicanalysis.model.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

@Document(collection = "repetitivestructures")
public class RepetitiveStructure extends LyricFeature {

   @Field(Fields.BLOCK_COUNT)
   private int blockCount;
   @Field(Fields.AVERAGE_BLOCK_SIZE)
   private float averageBlockSize;
   @Field(Fields.BLOCKS_PER_LINE)
   private float blocksPerLine;
   @Field(Fields.AVERAGE_ALIGNMENT_SCORE)
   private float averageAlignmentScore;
   @Field(Fields.BLOCK_REDUPLICATION)
   private float blockReduplication;
   @Field(Fields.TYPE_TOKEN_RATIO_LINES)
   private float typeTokenRatioLines;
   @Field(Fields.TYPE_TOKEN_RATIO_INLINES)
   private float typeTokenRatioInlines;
   @Field(Fields.REPETITIVITY)
   private float repetitivity;

   public int getBlockCount() {

      return blockCount;
   }

   public void setBlockCount(int blockCount) {

      this.blockCount = blockCount;
   }

   public float getAverageBlockSize() {

      return averageBlockSize;
   }

   public void setAverageBlockSize(float averageBlockSize) {

      this.averageBlockSize = averageBlockSize;
   }

   public float getBlocksPerLine() {

      return blocksPerLine;
   }

   public void setBlocksPerLine(float blocksPerLine) {

      this.blocksPerLine = blocksPerLine;
   }

   public float getBlockReduplication() {

      return blockReduplication;
   }

   public void setBlockReduplication(float blockReduplication) {

      this.blockReduplication = blockReduplication;
   }

   public float getAverageAlignmentScore() {

      return averageAlignmentScore;
   }

   public void setAverageAlignmentScore(float averageAlignmentScore) {

      this.averageAlignmentScore = averageAlignmentScore;
   }

   public float getTypeTokenRatioLines() {

      return typeTokenRatioLines;
   }

   public void setTypeTokenRatioLines(float typeTokenRatioLines) {

      this.typeTokenRatioLines = typeTokenRatioLines;
   }

   public float getTypeTokenRatioInlines() {

      return typeTokenRatioInlines;
   }

   public void setTypeTokenRatioInlines(float typeTokenRatioInlines) {

      this.typeTokenRatioInlines = typeTokenRatioInlines;
   }

   public float getRepetitivity() {
      return repetitivity;
   }

   public void setRepetitivity(float repetitivity) {
      this.repetitivity = repetitivity;
   }

   @Override
   public Update toUpdate() {

      final Update update = new Update();

      update.set(Fields.LYRIC_ID, getLyricId());
      update.set(Fields.BLOCK_COUNT, blockCount);
      update.set(Fields.AVERAGE_BLOCK_SIZE, averageBlockSize);
      update.set(Fields.BLOCKS_PER_LINE, blocksPerLine);
      update.set(Fields.AVERAGE_ALIGNMENT_SCORE, averageAlignmentScore);
      update.set(Fields.BLOCK_REDUPLICATION, blockReduplication);
      update.set(Fields.TYPE_TOKEN_RATIO_LINES, typeTokenRatioLines);
      update.set(Fields.TYPE_TOKEN_RATIO_INLINES, typeTokenRatioInlines);
      update.set(Fields.REPETITIVITY, repetitivity);

      return update;
   }

   public class Fields {

      public static final String LYRIC_ID = LyricFeature.Fields.LYRIC_ID;
      public static final String BLOCK_COUNT = "block_count";
      public static final String AVERAGE_BLOCK_SIZE = "average_block_size";
      public static final String BLOCKS_PER_LINE = "blocks_per_line";
      public static final String AVERAGE_ALIGNMENT_SCORE = "average_alignment_score";
      public static final String BLOCK_REDUPLICATION = "block_reduplication";
      public static final String TYPE_TOKEN_RATIO_LINES = "type_token_ratio_lines";
      public static final String TYPE_TOKEN_RATIO_INLINES = "type_token_ratio_inlines";
      public static final String REPETITIVITY = "repetitivity";

      private Fields() {

      }
   }
}
