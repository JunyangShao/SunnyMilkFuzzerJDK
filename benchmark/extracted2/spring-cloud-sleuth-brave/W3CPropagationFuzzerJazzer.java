package org.springframework.cloud.sleuth.brave.bridge;

import java.lang.reflect.*;
import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public class W3CPropagationFuzzerJazzer {

    static Method extractContextFromTraceParent_Method;

    public static void fuzzerInitialize() {
        try {
            extractContextFromTraceParent_Method = W3CPropagation.class.getDeclaredMethod("extractContextFromTraceParent", String.class);
            extractContextFromTraceParent_Method.setAccessible(true);
        } catch (NoSuchMethodException e) {
        } catch (ExceptionInInitializerError e) {
        }
    }

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String content = data.consumeRemainingAsString();
        try {
            extractContextFromTraceParent_Method.invoke(W3CPropagation.class, content);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
    }
}
