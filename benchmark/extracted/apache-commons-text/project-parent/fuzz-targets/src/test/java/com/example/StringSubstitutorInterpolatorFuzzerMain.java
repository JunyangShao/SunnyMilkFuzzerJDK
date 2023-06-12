package com.example;

import com.code_intelligence.jazzer.junit.FuzzTest;
import java.util.*;
import org.apache.commons.text.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StringSubstitutorInterpolatorFuzzerMain {

    public static void FuzzOne(String SMFData) {
        try {
            final StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
            String out = interpolator.replace(SMFData);
        } catch (IllegalArgumentException e) {
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
