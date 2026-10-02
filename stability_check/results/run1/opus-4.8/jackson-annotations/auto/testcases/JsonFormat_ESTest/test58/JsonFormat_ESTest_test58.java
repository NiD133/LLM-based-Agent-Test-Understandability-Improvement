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
     * Verifies how {@link JsonFormat.Value#withFeature} and
     * {@link JsonFormat.Value#withOverrides} interact:
     * enabling a feature yields a distinct value, and overriding a plain
     * default value with that feature-carrying value reproduces it.
     */
    @Test(timeout = 4000)
    public void test58() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // Enabling a feature must produce a new, non-equal Value.
        JsonFormat.Value valueWithFeature =
                defaultValue.withFeature(JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        assertFalse(valueWithFeature.equals((Object) defaultValue));

        // Neither value specifies a radix, so both report the default (-1).
        assertEquals(-1, defaultValue.getRadix());
        assertEquals(-1, valueWithFeature.getRadix());

        // Overriding the default value with the feature-carrying value yields a
        // distinct instance whose content matches the overriding value.
        JsonFormat.Value mergedValue = defaultValue.withOverrides(valueWithFeature);
        assertNotSame(mergedValue, defaultValue);
        assertTrue(mergedValue.equals((Object) valueWithFeature));
    }
}
