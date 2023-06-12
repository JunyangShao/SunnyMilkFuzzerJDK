package com.example;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParserFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(data.consumeRemainingAsString());
        } catch (ParseException | ClassCastException | NumberFormatException e) {
        }
    }
}
