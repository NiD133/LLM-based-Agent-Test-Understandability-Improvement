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
public class JsonIgnoreProperties_ESTest_test30 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that mergeAll() with a sparse array (null slots between two non-null Values)
     * applies each non-null Value in order: the second Value (with merge=true) acts as an
     * override on top of the first (with merge=false), producing a result equal to the second.
     */
    @Test(timeout = 4000)
    public void test30() throws Throwable {
        // First Value: ignoreUnknown=true, allowGetters=true, allowSetters=false, merge=false
        // merge=false means this Value does not participate in merging — it replaces base settings outright
        JsonIgnoreProperties.Value nonMergeValue = JsonIgnoreProperties.Value.construct(
                (Set<String>) null, true, true, false, false);
        assertTrue(nonMergeValue.getIgnoreUnknown());
        assertTrue(nonMergeValue.getAllowGetters());
        assertFalse(nonMergeValue.getAllowSetters());
        assertFalse(nonMergeValue.getMerge());

        // Build a sparse array with nonMergeValue at index 0 and a fully-enabled Value at index 6;
        // the five null slots in between are skipped by mergeAll
        JsonIgnoreProperties.Value[] values = new JsonIgnoreProperties.Value[7];
        values[0] = nonMergeValue;

        // Second Value: all flags true, including merge=true
        JsonIgnoreProperties.Value allEnabledValue = JsonIgnoreProperties.Value.construct(
                (Set<String>) null, true, true, true, true);
        values[6] = allEnabledValue;

        // mergeAll iterates the array; nonMergeValue becomes the seed, then allEnabledValue (merge=true)
        // is applied as an override — the merged outcome should equal allEnabledValue
        JsonIgnoreProperties.Value mergedResult = JsonIgnoreProperties.Value.mergeAll(values);
        assertTrue(mergedResult.equals((Object) allEnabledValue));
        assertNotNull(mergedResult);
        assertTrue(mergedResult.getAllowSetters());
        assertTrue(mergedResult.getMerge());
    }
}
