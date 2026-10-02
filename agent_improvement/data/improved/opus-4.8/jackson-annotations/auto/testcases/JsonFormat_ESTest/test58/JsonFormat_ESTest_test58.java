package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test58 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that overriding an empty {@link JsonFormat.Value} with a value
     * that has a feature enabled produces a result equal to that override.
     */
    @Test(timeout = 4000)
    public void withOverridesAdoptsEnabledFeature() throws Throwable {
        JsonFormat.Value emptyValue = new JsonFormat.Value();
        JsonFormat.Value valueWithFeature =
                emptyValue.withFeature(JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

        JsonFormat.Value mergedValue = emptyValue.withOverrides(valueWithFeature);

        // Enabling a feature yields a new, distinct value
        assertFalse(valueWithFeature.equals((Object) emptyValue));
        assertNotSame(mergedValue, emptyValue);

        // Neither value specifies a custom radix
        assertEquals(-1, emptyValue.getRadix());
        assertEquals(-1, valueWithFeature.getRadix());

        // Merging the override onto the empty value reproduces the override
        assertTrue(mergedValue.equals((Object) valueWithFeature));
    }
}
