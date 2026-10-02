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
public class JsonIgnoreProperties_ESTest_test25 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@code withAllowGetters()} returns a new {@code Value} with
     * {@code allowGetters=true}, while the original value remains unchanged and all
     * other flags (allowSetters, ignoreUnknown, merge) are preserved.
     */
    @Test(timeout = 4000)
    public void test25() throws Throwable {
        // Build a base Value: no ignored properties, ignoreUnknown=false,
        // allowGetters=false, allowSetters=false, merge=true
        LinkedHashSet<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value baseValue = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties,
                /* ignoreUnknown */ false,
                /* allowGetters  */ false,
                /* allowSetters  */ false,
                /* merge         */ true);

        // withAllowGetters() should return a new Value with allowGetters flipped to true
        JsonIgnoreProperties.Value valueWithGettersAllowed = baseValue.withAllowGetters();

        // The new value should have allowGetters enabled ...
        assertTrue(valueWithGettersAllowed.getAllowGetters());

        // ... while allowSetters and ignoreUnknown remain false
        assertFalse(valueWithGettersAllowed.getAllowSetters());
        assertFalse(valueWithGettersAllowed.getIgnoreUnknown());

        // merge flag should be preserved
        assertTrue(valueWithGettersAllowed.getMerge());

        // The original value must not be mutated by withAllowGetters()
        assertFalse(baseValue.getIgnoreUnknown());
    }
}
