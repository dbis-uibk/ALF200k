package musicanalysis.model.common;

import java.nio.file.Paths;

public class Path {

   private String path;

   private Path() {

   }

   public static Path getInstance() {

      return Path.InstanceHolder.INSTANCE;
   }

   public String getExecutablePath() {

      return path;
   }

   public void setExecutablePath(final String path) {

      this.path = path;
   }

   public String getResourcePath(final String path) {

      return Paths.get(this.path, "resources/", path).toAbsolutePath().toString();
   }

   private static class InstanceHolder {

      static final Path INSTANCE = new Path();
   }
}
