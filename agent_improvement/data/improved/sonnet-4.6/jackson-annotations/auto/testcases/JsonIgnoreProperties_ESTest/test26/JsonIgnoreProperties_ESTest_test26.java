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
public class JsonIgnoreProperties_ESTest_test26 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that calling withoutIgnoreUnknown() on a Value that has ignoreUnknown=true
     * produces a new Value with ignoreUnknown=false, while the original Value is unchanged.
     * Both values retain merge=true and have allowGetters/allowSetters as false (defaults).
     */
    @Test(timeout = 4000)
    public void test26() throws Throwable {
        // Create a Value with ignoreUnknown=true (merge defaults to true)
        JsonIgnoreProperties.Value valueWithIgnoreUnknown = JsonIgnoreProperties.Value.forIgnoreUnknown(true);

        // Produce a derived Value with ignoreUnknown turned off
        JsonIgnoreProperties.Value valueWithoutIgnoreUnknown = valueWithIgnoreUnknown.withoutIgnoreUnknown();

        // The derived value should have ignoreUnknown=false and preserve merge=true, allowGetters=false, allowSetters=false
        assertFalse(valueWithoutIgnoreUnknown.getIgnoreUnknown());
        assertTrue(valueWithoutIgnoreUnknown.getMerge());
        assertFalse(valueWithoutIgnoreUnknown.getAllowGetters());
        assertFalse(valueWithoutIgnoreUnknown.getAllowSetters());

        // The original value should still have ignoreUnknown=true and the same defaults
        assertTrue(valueWithIgnoreUnknown.getIgnoreUnknown());
        assertTrue(valueWithIgnoreUnknown.getMerge());
        assertFalse(valueWithIgnoreUnknown.getAllowGetters());
        assertFalse(valueWithIgnoreUnknown.getAllowSetters());
    }
}
