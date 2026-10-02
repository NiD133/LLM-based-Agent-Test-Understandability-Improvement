package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test55 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that merging a Value that enables one feature with a Value that
     * disables a different feature produces a distinct, combined result, and that
     * the merge leaves the (default) radix untouched.
     */
    @Test(timeout = 4000)
    public void mergingEnabledAndDisabledFeatures_yieldsDistinctCombinedValue() throws Throwable {
        JsonFormat.Value empty = JsonFormat.Value.empty();

        // Base value: READ_UNKNOWN_ENUM_VALUES_AS_NULL explicitly enabled.
        JsonFormat.Value withEnabledFeature =
                empty.withFeature(JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

        // Override value: READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE explicitly disabled.
        JsonFormat.Value withDisabledFeature =
                empty.withoutFeature(JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);

        // Merge the override onto the base.
        JsonFormat.Value merged = withEnabledFeature.withOverrides(withDisabledFeature);

        // The merged value combines both feature settings, so it differs from each source.
        assertFalse(merged.equals(withDisabledFeature));
        assertNotSame(merged, withDisabledFeature);
        assertFalse(merged.equals(withEnabledFeature));
        assertNotSame(merged, withEnabledFeature);

        // Neither source nor merge touches the radix, which stays at the default (-1).
        assertEquals(JsonFormat.DEFAULT_RADIX, merged.getRadix());
        assertFalse(withDisabledFeature.hasNonDefaultRadix());
    }
}
