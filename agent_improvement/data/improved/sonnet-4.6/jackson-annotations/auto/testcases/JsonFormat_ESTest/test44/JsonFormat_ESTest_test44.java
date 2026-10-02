package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test44 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test44() throws Throwable {
        // Build a 6-element array where every slot holds the same feature,
        // matching the original mix of direct assignment and index-based copy.
        JsonFormat.Feature readUnknownEnumFeature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
        JsonFormat.Feature[] enabledFeatures = new JsonFormat.Feature[6];
        enabledFeatures[0] = readUnknownEnumFeature;
        enabledFeatures[1] = readUnknownEnumFeature;
        enabledFeatures[2] = enabledFeatures[0];
        enabledFeatures[3] = enabledFeatures[0];
        enabledFeatures[4] = readUnknownEnumFeature;
        enabledFeatures[5] = readUnknownEnumFeature;

        // Construct two Features objects from identical enabled/disabled arrays.
        JsonFormat.Features featuresA = JsonFormat.Features.construct(enabledFeatures, enabledFeatures);
        JsonFormat.Features featuresB = JsonFormat.Features.construct(enabledFeatures, enabledFeatures);

        // Two independently constructed Features with identical bit masks must be equal.
        boolean areEqual = featuresB.equals(featuresA);
        assertTrue(areEqual);
    }
}
