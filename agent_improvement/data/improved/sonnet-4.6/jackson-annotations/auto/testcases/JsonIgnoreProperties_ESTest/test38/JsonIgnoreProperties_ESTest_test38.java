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
     * Verifies that mergeAll produces a result reflecting the properties of a non-merging Value
     * when the array contains nulls in the remaining slots.
     *
     * The constructed Value has: ignoreUnknown=true, allowGetters=true, allowSetters=false, merge=false.
     * Because merge=false, it acts as a full override rather than merging with earlier entries.
     * Null slots in the array are skipped by mergeAll.
     */
    @Test(timeout = 4000)
    public void test38() throws Throwable {
        // A Value that ignores unknown fields and allows getters, but does not allow setters
        // and has merge=false (meaning it replaces rather than merges with prior values)
        JsonIgnoreProperties.Value nonMergingValue = JsonIgnoreProperties.Value.construct(
                (Set<String>) null, // no explicitly ignored property names
                true,               // ignoreUnknown
                true,               // allowGetters
                false,              // allowSetters
                false               // merge
        );

        // Array of 7 slots: only the first two are populated; the rest are null
        JsonIgnoreProperties.Value[] values = new JsonIgnoreProperties.Value[7];
        values[0] = nonMergingValue;
        values[1] = nonMergingValue;

        // mergeAll iterates the array, skips nulls, and applies withOverrides sequentially.
        // Since nonMergingValue.merge=false, it overrides any prior entry, so the result
        // inherits all properties from nonMergingValue.
        JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.mergeAll(values);

        assertNotNull(merged);
        assertTrue(merged.getIgnoreUnknown());
        assertTrue(merged.getAllowGetters());
        assertFalse(merged.getAllowSetters());
        assertFalse(merged.getMerge());
    }
}
