package org.springframework.cloud.sleuth.brave.bridge;

import java.lang.reflect.*;

public class W3CPropagationFuzzerSMF {

    static Method extractContextFromTraceParent_Method;

    public static void fuzzerInitialize() {
        try {
            extractContextFromTraceParent_Method = W3CPropagation.class.getDeclaredMethod("extractContextFromTraceParent", String.class);
            extractContextFromTraceParent_Method.setAccessible(true);
        } catch (NoSuchMethodException e) {
        } catch (ExceptionInInitializerError e) {
        }
    }

    public static void FuzzOne(String SMFData) {
        String content = SMFData;
        try {
            extractContextFromTraceParent_Method.invoke(W3CPropagation.class, content);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
    }
}
