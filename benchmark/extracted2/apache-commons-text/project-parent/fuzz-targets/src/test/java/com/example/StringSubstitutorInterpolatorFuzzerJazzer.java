package com.example;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import java.util.*;
import org.apache.commons.text.*;

public class StringSubstitutorInterpolatorFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        try {
            final StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
            String out = interpolator.replace(data.consumeRemainingAsString());
        } catch (IllegalArgumentException e) {
        }
    }
}
