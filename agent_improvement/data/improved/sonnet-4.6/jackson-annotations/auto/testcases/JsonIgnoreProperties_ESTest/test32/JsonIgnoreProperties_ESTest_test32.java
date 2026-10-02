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
public class JsonIgnoreProperties_ESTest_test32 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that merging a Value with itself returns the same instance unchanged.
     *
     * When both base and override are identical (same object), withOverrides() short-circuits
     * via the _equals() check and returns `this`. The resulting Value retains ignoreUnknown=true
     * and the default allowGetters=false, allowSetters=false, merge=true settings.
     */
    @Test(timeout = 4000)
    public void test32_mergeSameInstanceReturnsSelf() throws Throwable {
        // Create a Value that ignores unknown properties, with all other flags at defaults
        JsonIgnoreProperties.Value ignoreUnknownValue = JsonIgnoreProperties.Value.forIgnoreUnknown(true);

        // Merging a Value with itself should return the exact same instance (short-circuit optimization)
        JsonIgnoreProperties.Value mergedValue = JsonIgnoreProperties.Value.merge(ignoreUnknownValue, ignoreUnknownValue);

        assertSame(mergedValue, ignoreUnknownValue);
        assertTrue(mergedValue.getIgnoreUnknown());
        assertTrue(mergedValue.getMerge());
        assertFalse(mergedValue.getAllowGetters());
        assertFalse(mergedValue.getAllowSetters());
    }
}
