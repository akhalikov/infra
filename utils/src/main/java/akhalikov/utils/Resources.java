package akhalikov.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Resources {

  public static String getResourceForClass(String fileName, Class clazz) {
    try {
      Path path = Paths.get(clazz.getResource(fileName).toURI());
      return new String(Files.readAllBytes(path));
    } catch (Exception e) {
      throw new RuntimeException("could not load resource " + fileName + " for class " + clazz, e);
    }
  }
}
