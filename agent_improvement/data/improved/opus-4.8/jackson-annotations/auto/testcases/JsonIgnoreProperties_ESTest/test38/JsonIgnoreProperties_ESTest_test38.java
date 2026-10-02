package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test38 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonIgnoreProperties.Value#mergeAll} skips the {@code null}
     * entries in the array and preserves the settings of the non-null Value(s) it merges.
     */
    @Test(timeout = 4000)
    public void mergeAllSkipsNullsAndKeepsSettings() throws Throwable {
        // ignoreUnknown=true, allowGetters=true, allowSetters=false, merge=false
        JsonIgnoreProperties.Value value =
                JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, false, false);

        // An array with the same Value in the first two slots and five trailing nulls.
        JsonIgnoreProperties.Value[] valuesToMerge = new JsonIgnoreProperties.Value[7];
        valuesToMerge[0] = value;
        valuesToMerge[1] = value;

        JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.mergeAll(valuesToMerge);

        // The trailing nulls are ignored and the original settings survive the merge.
        assertNotNull(merged);
        assertTrue(merged.getIgnoreUnknown());
        assertTrue(merged.getAllowGetters());
        assertFalse(merged.getAllowSetters());
        assertFalse(merged.getMerge());
    }
}
