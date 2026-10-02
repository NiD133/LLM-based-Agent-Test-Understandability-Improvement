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
public class JsonFormat_ESTest_test53 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that calling withoutFeature() twice with the same feature is idempotent:
     * the first call produces a new Value with the feature explicitly disabled,
     * while the second call returns the exact same object (no new instance needed).
     */
    @Test(timeout = 4000)
    public void test53() throws Throwable {
        // Start with a default Value that has no features set
        JsonFormat.Value defaultValue = new JsonFormat.Value();
        JsonFormat.Feature featureToDisable = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED;

        // First call: feature was not disabled yet, so a new Value is created
        JsonFormat.Value valueAfterFirstDisable = defaultValue.withoutFeature(featureToDisable);

        // Second call: feature is already disabled, so the same object is returned (idempotent)
        JsonFormat.Value valueAfterSecondDisable = valueAfterFirstDisable.withoutFeature(featureToDisable);

        // The first disable created a new object, distinct from the original
        assertNotSame(valueAfterSecondDisable, defaultValue);

        // The default Value uses the default radix (no custom radix configured)
        assertFalse(defaultValue.hasNonDefaultRadix());

        // The second disable had nothing to change, so it returned the same instance
        assertSame(valueAfterSecondDisable, valueAfterFirstDisable);

        // The twice-disabled Value also has no custom radix
        assertFalse(valueAfterSecondDisable.hasNonDefaultRadix());
    }
}
