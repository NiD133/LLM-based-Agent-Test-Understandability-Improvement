package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test52 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that disabling features that were originally enabled produces a
     * brand-new {@link JsonFormat.Features} instance which is no longer equal to
     * the original one (since the same feature is now disabled rather than enabled).
     */
    @Test(timeout = 4000)
    public void disablingEnabledFeaturesYieldsDifferentFeatures() throws Throwable {
        // Build a Features value where WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED is both
        // listed as enabled and disabled (the same feature appears twice in the array).
        JsonFormat.Feature[] features = new JsonFormat.Feature[] {
                JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED,
                JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED
        };
        JsonFormat.Features enabledFeatures = JsonFormat.Features.construct(features, features);

        // Disabling the same feature flips its state, producing a new instance.
        JsonFormat.Features disabledFeatures = enabledFeatures.without(features);

        assertNotSame(disabledFeatures, enabledFeatures);
        assertFalse(disabledFeatures.equals(enabledFeatures));
    }
}
