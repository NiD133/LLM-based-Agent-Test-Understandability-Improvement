package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test56 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies how {@link JsonFormat.Value#withOverrides} combines a value that has a
     * feature enabled on top of one that has the same feature disabled: the override
     * (enabled) wins, so the merged value becomes equal to the "enabled" value.
     */
    @Test(timeout = 4000)
    public void withOverrides_appliesEnabledFeatureFromOverride() throws Throwable {
        JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;

        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        JsonFormat.Value featureEnabled = emptyValue.withFeature(feature);
        JsonFormat.Value featureDisabled = emptyValue.withoutFeature(feature);

        // Override the "disabled" value with the "enabled" value.
        JsonFormat.Value merged = featureDisabled.withOverrides(featureEnabled);

        // Disabling a feature produces a value distinct from the empty default.
        assertFalse(featureDisabled.equals((Object) emptyValue));

        // The override's enabled feature takes precedence, so the merged value
        // equals the "enabled" value, though it is a freshly created instance.
        assertTrue(merged.equals((Object) featureEnabled));
        assertNotSame(merged, featureEnabled);

        // Radix was never set, so it stays at the default (-1).
        assertEquals(-1, featureEnabled.getRadix());
    }
}
