package org.springframework.cloud.sleuth.brave.bridge;

import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class W3CPropagationFuzzerMain {

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
