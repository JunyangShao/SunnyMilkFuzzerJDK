package com.example;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import java.util.*;
import org.apache.commons.text.*;

public class StringSubstitutorInterpolatorFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        try {
            final StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
            String out = interpolator.replace(SMFData);
        } catch (IllegalArgumentException e) {
        }
    }
}
