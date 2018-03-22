package musicanalysis.model.music.lyric.feature.syntactic;

import musicanalysis.model.common.Path;
import musicanalysis.model.settings.Settings;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;

import java.io.FileInputStream;
import java.io.InputStream;

class ChunkerTool {

   private ChunkerME chunker;

   private ChunkerTool() {

      try (final InputStream modelIn = new FileInputStream(Path.getInstance().getResourcePath(Settings.CHUNKER_TRAINING_SET_PATH))) {
         final ChunkerModel model = new ChunkerModel(modelIn);
         chunker = new ChunkerME(model);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public static ChunkerTool getInstance() {

      return ChunkerTool.InstanceHolder.INSTANCE;
   }

   public String[] chunk(final String[] sent, final String[] pos) {

      synchronized (chunker) {
         return chunker.chunk(sent, pos);
      }
   }

   private static class InstanceHolder {

      static final ChunkerTool INSTANCE = new ChunkerTool();
   }
}
