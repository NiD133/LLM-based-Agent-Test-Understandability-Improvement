package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test18 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@code withMerge()} returns a new instance with merge enabled
     * while leaving all other settings (ignoreUnknown, allowGetters, allowSetters)
     * unchanged from the original, non-merging value.
     */
    @Test(timeout = 4000)
    public void withMergeEnablesMergeAndKeepsOtherSettings() throws Throwable {
        // Build a value with: no ignored names, ignoreUnknown=true, allowGetters=true,
        // allowSetters=false, merge=false.
        JsonIgnoreProperties.Value original = JsonIgnoreProperties.Value.construct(
                (Set<String>) null, true, true, false, false);

        JsonIgnoreProperties.Value merged = original.withMerge();

        // withMerge() must produce a distinct instance because merge changed false -> true.
        assertNotSame(merged, original);

        // Original instance keeps its construction-time settings.
        assertTrue(original.getIgnoreUnknown());
        assertTrue(original.getAllowGetters());
        assertFalse(original.getAllowSetters());

        // Merged instance enables merge but carries over the other settings.
        assertTrue(merged.getMerge());
        assertTrue(merged.getIgnoreUnknown());
        assertTrue(merged.getAllowGetters());
        assertFalse(merged.getAllowSetters());
    }
}
