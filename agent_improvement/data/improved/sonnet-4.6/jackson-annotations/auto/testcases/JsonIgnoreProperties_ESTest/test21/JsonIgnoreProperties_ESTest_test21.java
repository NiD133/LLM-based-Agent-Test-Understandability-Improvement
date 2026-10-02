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
     * Verifies that withAllowSetters() is idempotent: calling it a second time when
     * allowSetters is already enabled returns the exact same instance, and that
     * all other properties (ignoreUnknown, allowGetters, merge) retain their defaults.
     */
    @Test(timeout = 4000)
    public void test21() throws Throwable {
        // Start with the canonical empty Value: no ignored fields, ignoreUnknown=false,
        // allowGetters=false, allowSetters=false, merge=true
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();

        // Enable allowSetters — produces a new instance with allowSetters=true
        JsonIgnoreProperties.Value valueWithSetters = emptyValue.withAllowSetters();

        // Calling withAllowSetters() again when already enabled must return the same instance (idempotent)
        JsonIgnoreProperties.Value valueWithSettersIdempotent = valueWithSetters.withAllowSetters();

        assertFalse("ignoreUnknown should remain false (default)", valueWithSettersIdempotent.getIgnoreUnknown());
        assertTrue("allowSetters should be true after withAllowSetters()", valueWithSettersIdempotent.getAllowSetters());
        assertFalse("allowGetters should remain false (default)", valueWithSettersIdempotent.getAllowGetters());
        assertTrue("merge should remain true (default from empty)", valueWithSettersIdempotent.getMerge());
        assertSame("second withAllowSetters() call should return the same instance",
                valueWithSettersIdempotent, valueWithSetters);
    }
}
