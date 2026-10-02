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
public class JsonIgnoreProperties_ESTest_test18 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        // Construct a Value with no ignored properties, ignoreUnknown=true, allowGetters=true,
        // allowSetters=false, and merge=false
        JsonIgnoreProperties.Value valueWithoutMerge = JsonIgnoreProperties.Value.construct(
                (Set<String>) null,
                /* ignoreUnknown */ true,
                /* allowGetters  */ true,
                /* allowSetters  */ false,
                /* merge         */ false);

        // withMerge() returns a new instance identical except merge becomes true
        JsonIgnoreProperties.Value valueWithMerge = valueWithoutMerge.withMerge();

        // The two instances must be distinct objects because merge flag changed
        assertNotSame(valueWithMerge, valueWithoutMerge);

        // Verify original instance retains its constructed flag values
        assertTrue(valueWithoutMerge.getAllowGetters());
        assertFalse(valueWithoutMerge.getAllowSetters());
        assertTrue(valueWithoutMerge.getIgnoreUnknown());

        // Verify withMerge() copy has merge=true and inherits all other flags unchanged
        assertTrue(valueWithMerge.getMerge());
        assertTrue(valueWithMerge.getIgnoreUnknown());
        assertTrue(valueWithMerge.getAllowGetters());
        assertFalse(valueWithMerge.getAllowSetters());
    }
}
