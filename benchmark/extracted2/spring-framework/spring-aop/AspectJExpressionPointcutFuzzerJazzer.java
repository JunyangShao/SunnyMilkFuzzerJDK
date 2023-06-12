import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import java.lang.reflect.Method;
import java.util.*;
import java.lang.*;

public class AspectJExpressionPointcutFuzzerJazzer {

    public static Class<?>[] classes = { Integer.class, String.class, Byte.class, List.class, Map.class, TreeMap.class, BitSet.class, TimeZone.class, Date.class, Calendar.class, Locale.class };

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        Class<?> classMatch = data.pickValue(classes);
        String matchesTestBean = data.consumeRemainingAsString();
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
}
