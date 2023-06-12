package com.example;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import java.util.*;
import org.apache.commons.text.*;

public class StringSubstitutorFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("parameter1", SMFData);
        String templateString = "The ${parameter1} was fuzzed";
        StringSubstitutor sub = new StringSubstitutor(valuesMap);
        String resolvedString = sub.replace(templateString);
    }
}
