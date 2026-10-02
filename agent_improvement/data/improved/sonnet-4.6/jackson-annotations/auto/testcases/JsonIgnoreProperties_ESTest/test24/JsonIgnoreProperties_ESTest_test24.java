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
public class JsonIgnoreProperties_ESTest_test24 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Tests that withAllowGetters() is a no-op when allowGetters is already true.
     *
     * A Value constructed with allowGetters=true should return itself (same reference)
     * when withAllowGetters() is called, since the flag is already set.
     * The other properties (ignoreUnknown=true, allowSetters=false, merge=false)
     * must remain unchanged after the call.
     */
    @Test(timeout = 4000)
    public void test24() throws Throwable {
        // Get the ignored-for-serialization set from the empty Value (yields an empty set)
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
        Set<String> ignoredForSerialization = emptyValue.findIgnoredForSerialization();

        // Construct a Value with allowGetters=true already set
        JsonIgnoreProperties.Value valueWithAllowGetters = JsonIgnoreProperties.Value.construct(
                ignoredForSerialization,
                /* ignoreUnknown */ true,
                /* allowGetters  */ true,
                /* allowSetters  */ false,
                /* merge         */ false);

        // withAllowGetters() should be a no-op and return the same instance
        JsonIgnoreProperties.Value afterWithAllowGetters = valueWithAllowGetters.withAllowGetters();
        assertSame(afterWithAllowGetters, valueWithAllowGetters);

        // The remaining properties should be unchanged
        assertTrue(afterWithAllowGetters.getIgnoreUnknown());
        assertFalse(afterWithAllowGetters.getAllowSetters());
        assertFalse(afterWithAllowGetters.getMerge());
    }
}
