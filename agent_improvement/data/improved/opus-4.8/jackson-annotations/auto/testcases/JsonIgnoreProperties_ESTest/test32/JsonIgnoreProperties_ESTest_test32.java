package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test32 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Merging a Value with itself should return that same instance unchanged,
     * because two equal Values produce no new overrides.
     */
    @Test(timeout = 4000)
    public void mergingValueWithItselfReturnsSameInstance() throws Throwable {
        JsonIgnoreProperties.Value ignoreUnknownValue =
                JsonIgnoreProperties.Value.forIgnoreUnknown(true);

        JsonIgnoreProperties.Value merged =
                JsonIgnoreProperties.Value.merge(ignoreUnknownValue, ignoreUnknownValue);

        // Merging a value with itself yields the very same object.
        assertSame(ignoreUnknownValue, merged);

        // The merged value keeps the original settings.
        assertTrue(merged.getIgnoreUnknown());
        assertTrue(merged.getMerge());
        assertFalse(merged.getAllowGetters());
        assertFalse(merged.getAllowSetters());
    }
}
