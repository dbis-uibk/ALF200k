package musicanalysis.model.music.lyric.feature;

import musicanalysis.model.nlp.Line;

import java.util.ArrayList;
import java.util.List;

public class Verse {

   private List<Line> lines;

   public Verse() {

      lines = new ArrayList<>();
   }

   public void addLine(final Line line) {

      lines.add(line);
   }

   public void addLines(final List<Line> lines) {

      lines.addAll(lines);
   }

   public List<Line> getLines() {

      return lines;
   }
}