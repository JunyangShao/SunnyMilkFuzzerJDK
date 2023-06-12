package com.example;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import java.util.*;
import org.apache.commons.text.*;

public class StringSubstitutorFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("parameter1", data.consumeRemainingAsString());
        String templateString = "The ${parameter1} was fuzzed";
        StringSubstitutor sub = new StringSubstitutor(valuesMap);
        String resolvedString = sub.replace(templateString);
    }
}
