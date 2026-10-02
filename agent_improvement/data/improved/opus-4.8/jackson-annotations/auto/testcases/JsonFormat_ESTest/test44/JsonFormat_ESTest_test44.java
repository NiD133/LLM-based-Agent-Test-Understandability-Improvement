package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test44 extends JsonFormat_ESTest_scaffolding {

    /**
     * Two {@link JsonFormat.Features} instances built from the same enabled/disabled
     * feature arrays should be considered equal, since equality is based purely on the
     * resulting enabled/disabled bit masks.
     */
    @Test(timeout = 4000)
    public void constructWithSameFeaturesProducesEqualInstances() throws Throwable {
        JsonFormat.Feature[] features = new JsonFormat.Feature[6];
        for (int i = 0; i < features.length; i++) {
            features[i] = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
        }

        // Use the same array for both the "enabled" and "disabled" parameters.
        JsonFormat.Features first = JsonFormat.Features.construct(features, features);
        JsonFormat.Features second = JsonFormat.Features.construct(features, features);

        assertTrue(second.equals(first));
    }
}
