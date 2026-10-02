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
     * Verifies that layering a feature-carrying override onto a default
     * {@link JsonFormat.Value} adopts that override's settings, while leaving
     * the original defaults (such as the default radix) untouched.
     */
    @Test(timeout = 4000)
    public void test58() throws Throwable {
        // Start from a default value (no features, default radix of -1).
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // Derive a value that additionally enables one feature.
        JsonFormat.Value valueWithFeature =
                defaultValue.withFeature(JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

        // Enabling the feature must produce a distinct value from the default.
        assertFalse(valueWithFeature.equals((Object) defaultValue));

        // Layer the feature-carrying value on top of the default as overrides.
        JsonFormat.Value mergedValue = defaultValue.withOverrides(valueWithFeature);

        // Merging returns a new instance, not the original default.
        assertNotSame(mergedValue, defaultValue);

        // The merged result matches the override it was given.
        assertTrue(mergedValue.equals((Object) valueWithFeature));

        // Neither the default nor the feature-carrying value changed radix.
        assertEquals(JsonFormat.DEFAULT_RADIX, defaultValue.getRadix());
        assertEquals(JsonFormat.DEFAULT_RADIX, valueWithFeature.getRadix());
    }
}
