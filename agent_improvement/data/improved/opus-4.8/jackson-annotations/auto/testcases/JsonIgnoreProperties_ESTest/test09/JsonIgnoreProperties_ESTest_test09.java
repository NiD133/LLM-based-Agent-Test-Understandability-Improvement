package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test09 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonIgnoreProperties.Value#withAllowSetters()} produces a Value
     * that has "allowSetters" enabled while leaving the other flags from the original Value
     * unchanged.
     */
    @Test(timeout = 4000)
    public void withAllowSetters_enablesAllowSettersAndKeepsOtherFlags() throws Throwable {
        // Start from a Value with: no ignored names, ignoreUnknown=false,
        // allowGetters=false, allowSetters=false, merge=true.
        Set<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value baseValue = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties, false, false, false, true);

        // Derive a new Value that additionally allows setters.
        JsonIgnoreProperties.Value valueWithAllowSetters = baseValue.withAllowSetters();

        // Only the allowSetters flag should now be true; the rest stay as in the base value.
        assertFalse(valueWithAllowSetters.getIgnoreUnknown());
        assertFalse(valueWithAllowSetters.getAllowGetters());
        assertTrue(valueWithAllowSetters.getAllowSetters());
        assertTrue(valueWithAllowSetters.getMerge());
    }
}
