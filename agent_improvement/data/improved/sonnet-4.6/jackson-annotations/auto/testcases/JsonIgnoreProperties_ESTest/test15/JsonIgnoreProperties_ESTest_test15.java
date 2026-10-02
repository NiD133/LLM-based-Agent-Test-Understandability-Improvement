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
public class JsonIgnoreProperties_ESTest_test15 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that readResolve() returns a Value with the same settings when
     * the value is not equivalent to EMPTY (here ignoreUnknown=true prevents it
     * from being treated as empty). The resolved value must preserve
     * ignoreUnknown=true, allowGetters=true, allowSetters=false, and merge=true.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // No specific property names to ignore
        LinkedHashSet<String> noIgnoredProperties = new LinkedHashSet<String>();

        // Construct a non-empty Value: ignoreUnknown=true keeps it from resolving to EMPTY
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties,
                /* ignoreUnknown */ true,
                /* allowGetters  */ true,
                /* allowSetters  */ false,
                /* merge         */ true);

        // readResolve() is called during deserialization; since ignoreUnknown=true,
        // the value is not empty and should be returned as-is (not replaced by EMPTY)
        JsonIgnoreProperties.Value resolvedValue = (JsonIgnoreProperties.Value) value.readResolve();

        assertTrue(resolvedValue.getIgnoreUnknown());
        assertFalse(resolvedValue.getAllowSetters());
        assertTrue(resolvedValue.getMerge());
        assertTrue(resolvedValue.getAllowGetters());
    }
}
