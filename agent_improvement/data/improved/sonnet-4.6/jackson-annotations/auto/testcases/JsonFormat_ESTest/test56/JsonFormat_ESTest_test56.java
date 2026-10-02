package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test56 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that withOverrides() merges feature states correctly:
     * a value with a feature explicitly disabled, when overridden by a value
     * that has the same feature explicitly enabled, should become equal to
     * that enabling value.
     */
    @Test(timeout = 4000)
    public void test56() throws Throwable {
        // Baseline: an empty Value with no features configured
        JsonFormat.Value baseValue = JsonFormat.Value.empty();

        JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;

        // valueWithFeatureEnabled has READ_UNKNOWN_ENUM_VALUES_AS_NULL explicitly enabled
        JsonFormat.Value valueWithFeatureEnabled = baseValue.withFeature(feature);

        // valueWithFeatureDisabled has READ_UNKNOWN_ENUM_VALUES_AS_NULL explicitly disabled
        JsonFormat.Value valueWithFeatureDisabled = baseValue.withoutFeature(feature);

        // Applying valueWithFeatureEnabled as overrides to valueWithFeatureDisabled
        // should yield a result equivalent to valueWithFeatureEnabled
        JsonFormat.Value merged = valueWithFeatureDisabled.withOverrides(valueWithFeatureEnabled);

        // Explicitly disabling a feature changes the Value, so it is not equal to the base empty Value
        assertFalse(valueWithFeatureDisabled.equals((Object) baseValue));

        // The merged result should match the override (the enabled state wins)
        assertTrue(merged.equals((Object) valueWithFeatureEnabled));

        // The merged result is a newly constructed instance, not the same reference
        assertNotSame(merged, valueWithFeatureEnabled);

        // withFeature() does not affect radix; it should remain at the default sentinel (-1)
        assertEquals((-1), valueWithFeatureEnabled.getRadix());
    }
}
