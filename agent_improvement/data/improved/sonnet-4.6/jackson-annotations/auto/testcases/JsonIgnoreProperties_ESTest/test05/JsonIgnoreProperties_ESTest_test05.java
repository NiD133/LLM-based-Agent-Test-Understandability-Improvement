package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test05 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that withAllowSetters() produces a new Value instance that differs from
     * the empty baseline, and that the new instance preserves all other default
     * properties (ignoreUnknown=false, allowGetters=false, merge=true).
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Obtain the canonical empty Value (all defaults: no ignored fields,
        // ignoreUnknown=false, allowGetters=false, allowSetters=false, merge=true)
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();

        // Create a variant with allowSetters enabled; all other fields remain at their defaults
        JsonIgnoreProperties.Value valueWithSettersAllowed = emptyValue.withAllowSetters();

        // The two instances differ only in allowSetters, so they must not be equal
        boolean areEqual = emptyValue.equals(valueWithSettersAllowed);

        // The new value retains the defaults for every property other than allowSetters
        assertFalse(valueWithSettersAllowed.getIgnoreUnknown());
        assertTrue(valueWithSettersAllowed.getMerge());
        assertFalse(valueWithSettersAllowed.getAllowGetters());

        // Confirm inequality in both directions
        assertFalse(valueWithSettersAllowed.equals((Object) emptyValue));
        assertFalse(areEqual);
    }
}
