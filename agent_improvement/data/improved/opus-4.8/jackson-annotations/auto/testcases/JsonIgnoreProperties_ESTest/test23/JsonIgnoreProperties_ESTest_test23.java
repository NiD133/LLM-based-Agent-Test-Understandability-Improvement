package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test23 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that withoutAllowGetters() returns a new Value with allowGetters
     * cleared, while leaving all other settings (ignoreUnknown, allowSetters,
     * merge) untouched.
     */
    @Test(timeout = 4000)
    public void withoutAllowGetters_clearsOnlyAllowGetters() throws Throwable {
        // Build a Value with: ignoreUnknown=true, allowGetters=true, allowSetters=false, merge=false.
        Set<String> noIgnoredProperties = JsonIgnoreProperties.Value.empty().findIgnoredForSerialization();
        JsonIgnoreProperties.Value original = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties, true, true, false, false);

        // Clearing allowGetters must produce a distinct instance.
        JsonIgnoreProperties.Value withoutGetters = original.withoutAllowGetters();
        assertNotSame(withoutGetters, original);

        // Original is unchanged.
        assertTrue(original.getIgnoreUnknown());
        assertFalse(original.getAllowSetters());
        assertFalse(original.getMerge());

        // Result has allowGetters cleared; everything else carried over.
        assertFalse(withoutGetters.getAllowGetters());
        assertTrue(withoutGetters.getIgnoreUnknown());
        assertFalse(withoutGetters.getAllowSetters());
        assertFalse(withoutGetters.getMerge());
    }
}
