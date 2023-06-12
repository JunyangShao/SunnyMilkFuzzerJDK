import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import java.lang.reflect.Method;
import java.util.*;
import java.lang.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AspectJExpressionPointcutFuzzerMain {

    public static Class<?>[] classes = { Integer.class, String.class, Byte.class, List.class, Map.class, TreeMap.class, BitSet.class, TimeZone.class, Date.class, Calendar.class, Locale.class };

    public static void FuzzOne(String SMFData) {
        Class<?> classMatch = data.pickValue(classes);
        String matchesTestBean = SMFData;
        AspectJExpressionPointcut testBeanPc = new AspectJExpressionPointcut();
        testBeanPc.setExpression(matchesTestBean);
        Method methodName;
        try {
            methodName = classMatch.getMethod("hashCode");
        } catch (NoSuchMethodException ignored) {
            return;
        }
        try {
            testBeanPc.matches(methodName, classMatch);
        } catch (RuntimeException e) {
            if (!(e instanceof IllegalArgumentException) && !e.getMessage().contains("bad")) {
                throw e;
            }
        }
        testBeanPc.toString();
        testBeanPc.hashCode();
        testBeanPc.equals(new Object());
    }

    public static void main(String[] args) {
        File folder = new File("./fuzzerOut");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String content = readFileAsString(file.getAbsolutePath());
                    FuzzOne(content);
                }
            }
        } else {
            System.out.println("The directory is empty or it does not exist.");
        }
    }

    private static String readFileAsString(String fileName) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            return content;
        } catch (IOException e) {
            return "";
        }
    }
}
