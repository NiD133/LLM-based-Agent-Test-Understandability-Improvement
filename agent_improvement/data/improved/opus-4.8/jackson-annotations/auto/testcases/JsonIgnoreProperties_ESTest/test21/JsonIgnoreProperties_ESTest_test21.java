package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test21 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@code withAllowSetters()} is idempotent: calling it on a
     * Value that already allows setters returns the very same instance instead
     * of building a new one, while leaving all other settings unchanged.
     */
    @Test(timeout = 4000)
    public void withAllowSettersIsIdempotentWhenAlreadyAllowed() throws Throwable {
        // Start from the default ("empty") settings, then enable allowSetters.
        JsonIgnoreProperties.Value allowSettersEnabled =
                JsonIgnoreProperties.Value.empty().withAllowSetters();

        // Calling withAllowSetters() again has nothing to change.
        JsonIgnoreProperties.Value allowSettersEnabledAgain =
                allowSettersEnabled.withAllowSetters();

        // The flag we toggled is on; every other setting keeps its default.
        assertTrue(allowSettersEnabledAgain.getAllowSetters());
        assertFalse(allowSettersEnabledAgain.getIgnoreUnknown());
        assertFalse(allowSettersEnabledAgain.getAllowGetters());
        assertTrue(allowSettersEnabledAgain.getMerge());

        // Since allowSetters was already enabled, the second call short-circuits
        // and returns the same object rather than allocating a new Value.
        assertSame(allowSettersEnabled, allowSettersEnabledAgain);
    }
}
