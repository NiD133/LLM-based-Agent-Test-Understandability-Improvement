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
public class JsonIgnoreProperties_ESTest_test16 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that withoutMerge() is idempotent: calling it twice on a Value
     * that already has merge=true produces the same boolean flags as calling it once.
     * All other flags (ignoreUnknown, allowGetters, allowSetters) remain false.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        // Build a Value with merge=true, all other flags false, and no ignored properties
        Set<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value valueWithMerge = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties,
                /* ignoreUnknown */ false,
                /* allowGetters  */ false,
                /* allowSetters  */ false,
                /* merge         */ true);

        // Disable merging — produces a new Value with merge=false
        JsonIgnoreProperties.Value valueWithoutMerge = valueWithMerge.withoutMerge();

        // Calling withoutMerge() again should be idempotent (merge is already false)
        JsonIgnoreProperties.Value valueWithoutMergeAgain = valueWithoutMerge.withoutMerge();

        // The original value should not have ignoreUnknown set
        assertFalse(valueWithMerge.getIgnoreUnknown());

        // After stripping merge twice, all other flags must remain false
        assertFalse(valueWithoutMergeAgain.getAllowGetters());
        assertFalse(valueWithoutMergeAgain.getMerge());
        assertFalse(valueWithoutMergeAgain.getAllowSetters());
        assertFalse(valueWithoutMergeAgain.getIgnoreUnknown());
    }
}
