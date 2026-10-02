package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test19 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * When allowSetters is already false, withoutAllowSetters() has nothing to
     * change and returns the same instance, leaving all other settings intact.
     */
    @Test(timeout = 4000)
    public void withoutAllowSettersReturnsSameInstanceWhenAlreadyDisabled() throws Throwable {
        // Build a Value with: no ignored properties, ignoreUnknown=true,
        // allowGetters=true, allowSetters=false, merge=false.
        JsonIgnoreProperties.Value original = JsonIgnoreProperties.Value.construct(
                (Set<String>) null, true, true, false, false);

        JsonIgnoreProperties.Value result = original.withoutAllowSetters();

        // allowSetters was already false, so the same instance is returned unchanged.
        assertSame(original, result);
        assertFalse(result.getMerge());
        assertTrue(result.getAllowGetters());
        assertTrue(result.getIgnoreUnknown());
    }
}
