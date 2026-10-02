package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test49 extends JsonFormat_ESTest_scaffolding {

    /**
     * Enabling a feature produces a new Value that is no longer equal to the
     * original default Value, and equality stays symmetric. Toggling a feature
     * does not affect the radix setting, which remains the default for both.
     */
    @Test(timeout = 4000)
    public void enablingFeatureBreaksEqualityWithDefaultValue() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        JsonFormat.Value valueWithFeature =
                defaultValue.withFeature(JsonFormat.Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

        // The feature-enabled value differs from the default value (both directions).
        assertFalse(valueWithFeature.equals(defaultValue));
        assertFalse(defaultValue.equals(valueWithFeature));

        // Neither value uses a non-default radix; enabling a feature leaves radix untouched.
        assertFalse(valueWithFeature.hasNonDefaultRadix());
        assertFalse(defaultValue.hasNonDefaultRadix());
    }
}
