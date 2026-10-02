package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test30 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonIgnoreProperties.Value#mergeAll} ignores null entries
     * in the array and merges the remaining values via {@code withOverrides}.
     *
     * The array holds two values surrounded by nulls. Because the second value enables
     * merging, merging the first (the base) with the second (the overrides) yields a
     * result equal to the second value.
     */
    @Test(timeout = 4000)
    public void mergeAllSkipsNullsAndMergesRemainingValues() throws Throwable {
        // Base value: ignoreUnknown=true, allowGetters=true, allowSetters=false, merge=false
        JsonIgnoreProperties.Value baseValue =
                JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, false, false);
        assertTrue(baseValue.getIgnoreUnknown());
        assertTrue(baseValue.getAllowGetters());
        assertFalse(baseValue.getAllowSetters());
        assertFalse(baseValue.getMerge());

        // Override value: every flag enabled, including merge so withOverrides actually merges.
        JsonIgnoreProperties.Value overrideValue =
                JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, true, true);

        // Place the two values in an array of length 7, leaving the rest as nulls
        // that mergeAll must skip.
        JsonIgnoreProperties.Value[] values = new JsonIgnoreProperties.Value[7];
        values[0] = baseValue;
        values[6] = overrideValue;

        JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.mergeAll(values);

        // The override enables merging, so the merged result equals the override value.
        assertNotNull(merged);
        assertTrue(merged.equals(overrideValue));
        assertTrue(merged.getAllowSetters());
        assertTrue(merged.getMerge());
    }
}
