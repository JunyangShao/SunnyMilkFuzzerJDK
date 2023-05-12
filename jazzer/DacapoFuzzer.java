import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.jar.JarFile;
import java.security.Permission;
import java.lang.reflect.InvocationTargetException;

public class DacapoFuzzer {
  private static int cnt = 0;

  private static String getVersion() {
    String version = System.getProperty("java.version");
    if(version.startsWith("1.")) {
      version = version.substring(2, 3);
    } else {
      int dot = version.indexOf(".");
      if(dot != -1) { version = version.substring(0, dot); }
    } return version;
  }

  public static void fuzzerTestOneInput(FuzzedDataProvider data) {
    System.out.print("Current JAVA version is: ");
    System.out.println(getVersion()); 
    cnt++;
    if (cnt == 2) {
	    throw new FuzzerSecurityIssueMedium("mustNeverBeCalled has been called");
    }
    try {
        // Path to the JAR file
        String jarPath = "/home/junyangshao/Desktop/playground/" +
                         "research/benchmark/dacapo-9.12-MR1-bach.jar";

        // Load the JAR file using a URLClassLoader
        URL jarUrl = new URL("file:" + jarPath);
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jarUrl});
        Thread.currentThread().setContextClassLoader(urlClassLoader);

        // Read the JAR manifest to obtain the main class name
        JarFile jarFile = new JarFile(jarPath);
        Manifest manifest = jarFile.getManifest();
        Attributes mainAttributes = manifest.getMainAttributes();
        String mainClassName = mainAttributes.getValue(Attributes.Name.MAIN_CLASS);

        // Load the main class using the URLClassLoader
        Class<?> mainClass = urlClassLoader.loadClass(mainClassName);

        // Get the main method
        Method mainMethod = mainClass.getMethod("main", String[].class);

        // Save the original security manager
        SecurityManager originalSecurityManager = System.getSecurityManager();

        // Invoke the main method with the arguments you want to pass
        String[] mainArgs = new String[]{"avrora", "fop", "h2",
                                        "jython", "luindex", "lusearch", "pmd", "sunflow",
                                        "xalan"};
        // String[] mainArgs = new String[]{"h2"};
        mainMethod.invoke(null, (Object) mainArgs);
    } catch (Exception ignored) {
      ignored.printStackTrace();
      return;
    }
  }
}
