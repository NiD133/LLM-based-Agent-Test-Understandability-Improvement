package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test24 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * When allowGetters is already enabled, calling withAllowGetters() should be a no-op
     * and return the same instance, leaving all other settings unchanged.
     */
    @Test(timeout = 4000)
    public void withAllowGettersOnAlreadyAllowingValueReturnsSameInstance() throws Throwable {
        Set<String> noIgnoredProperties = JsonIgnoreProperties.Value.empty().findIgnoredForSerialization();

        // ignoreUnknown=true, allowGetters=true, allowSetters=false, merge=false
        JsonIgnoreProperties.Value valueAllowingGetters =
                JsonIgnoreProperties.Value.construct(noIgnoredProperties, true, true, false, false);

        JsonIgnoreProperties.Value result = valueAllowingGetters.withAllowGetters();

        // Getters are already allowed, so the same instance is returned unchanged.
        assertSame(valueAllowingGetters, result);
        assertTrue(result.getIgnoreUnknown());
        assertFalse(result.getAllowSetters());
        assertFalse(result.getMerge());
    }
}
