package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test84 extends JsonFormat_ESTest_scaffolding {

    /**
     * A default JsonFormat.Value has no per-feature overrides and no custom
     * radix. Verify that querying any feature returns null ("no override")
     * and that the default radix is reported as unchanged.
     */
    @Test(timeout = 4000)
    public void defaultValueHasNoFeatureOverrideAndDefaultRadix() throws Throwable {
        JsonFormat.Value defaultFormat = new JsonFormat.Value();

        Boolean featureOverride =
                defaultFormat.getFeature(JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);

        assertNull("default value should not override any feature", featureOverride);
        assertFalse("default value should use the default radix",
                defaultFormat.hasNonDefaultRadix());
    }
}
