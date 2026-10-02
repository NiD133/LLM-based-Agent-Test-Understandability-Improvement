package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test25 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@code withAllowGetters()} produces a Value that allows getters
     * while preserving every other setting from the original instance.
     */
    @Test(timeout = 4000)
    public void withAllowGetters_enablesGettersAndKeepsOtherSettings() throws Throwable {
        // Build a Value with: no ignored names, ignoreUnknown=false, allowGetters=false,
        // allowSetters=false, merge=true.
        LinkedHashSet<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value original = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties, false, false, false, true);

        // Derive a new Value that allows getters.
        JsonIgnoreProperties.Value withGetters = original.withAllowGetters();

        // The derived Value now allows getters...
        assertTrue(withGetters.getAllowGetters());
        // ...while all other settings remain unchanged.
        assertFalse(withGetters.getAllowSetters());
        assertTrue(withGetters.getMerge());
        assertFalse(withGetters.getIgnoreUnknown());

        // The original instance is unaffected.
        assertFalse(original.getIgnoreUnknown());
    }
}
