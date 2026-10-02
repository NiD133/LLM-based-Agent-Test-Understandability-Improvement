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
        JsonFormat.Feature enumDefaultFeature =
                JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
        JsonFormat.Feature[] repeatedFeatureSettings = new JsonFormat.Feature[] {
                enumDefaultFeature,
                enumDefaultFeature,
                enumDefaultFeature,
                enumDefaultFeature,
                enumDefaultFeature,
                enumDefaultFeature
        };

        JsonFormat.Features firstFeatures =
                JsonFormat.Features.construct(repeatedFeatureSettings, repeatedFeatureSettings);
        JsonFormat.Features secondFeatures =
                JsonFormat.Features.construct(repeatedFeatureSettings, repeatedFeatureSettings);

        assertTrue(secondFeatures.equals(firstFeatures));
    }
}
