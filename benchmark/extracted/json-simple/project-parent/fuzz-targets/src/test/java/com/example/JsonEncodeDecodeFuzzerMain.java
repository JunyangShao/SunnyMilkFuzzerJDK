package com.example;

import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import com.code_intelligence.jazzer.junit.FuzzTest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonEncodeDecodeFuzzerMain {

    public static void FuzzOne(String SMFData) {
        try {
            String fuzzingString = SMFData;
            JSONObject srcObj = new JSONObject();
            srcObj.put("item1", fuzzingString);
            StringWriter out = new StringWriter();
            srcObj.writeJSONString(out);
            String jsonText = out.toString();
            StringReader in = new StringReader(jsonText);
            JSONParser parser = new JSONParser();
            JSONObject destObj = (JSONObject) parser.parse(in);
            if (!destObj.equals(srcObj)) {
                throw new IllegalStateException("Decoded object: " + destObj.toString() + " doesn't match original object: " + srcObj.toString());
            }
        } catch (IOException | ParseException e) {
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
