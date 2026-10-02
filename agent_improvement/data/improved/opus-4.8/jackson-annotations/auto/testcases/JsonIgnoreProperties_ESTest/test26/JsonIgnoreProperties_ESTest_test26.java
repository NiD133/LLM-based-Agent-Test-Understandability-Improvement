package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test26 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@code withoutIgnoreUnknown()} clears the "ignore unknown" flag
     * while leaving every other setting untouched, and that the original Value is
     * not mutated (the Value type is immutable, so a new instance is returned).
     */
    @Test(timeout = 4000)
    public void withoutIgnoreUnknownClearsOnlyTheIgnoreUnknownFlag() throws Throwable {
        // Start from a Value that only has "ignore unknown" enabled.
        JsonIgnoreProperties.Value ignoreUnknownEnabled =
                JsonIgnoreProperties.Value.forIgnoreUnknown(true);

        // Derive a copy with "ignore unknown" turned back off.
        JsonIgnoreProperties.Value ignoreUnknownDisabled =
                ignoreUnknownEnabled.withoutIgnoreUnknown();

        // The derived Value differs only in the ignoreUnknown flag.
        assertFalse(ignoreUnknownDisabled.getIgnoreUnknown());
        assertTrue(ignoreUnknownDisabled.getMerge());
        assertFalse(ignoreUnknownDisabled.getAllowGetters());
        assertFalse(ignoreUnknownDisabled.getAllowSetters());

        // The original Value is unchanged: ignoreUnknown is still set.
        assertTrue(ignoreUnknownEnabled.getIgnoreUnknown());
        assertTrue(ignoreUnknownEnabled.getMerge());
        assertFalse(ignoreUnknownEnabled.getAllowGetters());
        assertFalse(ignoreUnknownEnabled.getAllowSetters());
    }
}
