package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test05 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@code withAllowSetters()} produces a new Value that differs from the
     * empty original (so the two are not equal in either direction), while leaving the
     * unrelated flags (ignoreUnknown, allowGetters) unchanged and keeping merge enabled.
     */
    @Test(timeout = 4000)
    public void withAllowSettersCreatesDistinctValue() throws Throwable {
        JsonIgnoreProperties.Value empty = JsonIgnoreProperties.Value.empty();
        JsonIgnoreProperties.Value withAllowSetters = empty.withAllowSetters();

        // Enabling allowSetters only flips that one flag; the others keep their empty defaults.
        assertFalse(withAllowSetters.getIgnoreUnknown());
        assertTrue(withAllowSetters.getMerge());
        assertFalse(withAllowSetters.getAllowGetters());

        // The two instances differ, so equals() is false in both directions.
        assertFalse(empty.equals(withAllowSetters));
        assertFalse(withAllowSetters.equals(empty));
    }
}
