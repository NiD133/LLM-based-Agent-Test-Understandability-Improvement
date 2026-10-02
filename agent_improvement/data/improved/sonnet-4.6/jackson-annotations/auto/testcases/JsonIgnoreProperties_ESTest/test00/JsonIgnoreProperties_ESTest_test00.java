package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test00 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that mergeAll combines values correctly:
     * - null slots in the array are skipped
     * - boolean flags (ignoreUnknown, allowGetters, allowSetters) are OR-ed together
     * - the merged result differs from a single input if the ignored-property sets differ
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Prepare a sparse array; most slots remain null and are skipped by mergeAll
        JsonIgnoreProperties.Value[] valuesToMerge = new JsonIgnoreProperties.Value[6];

        // Build a Value with all boolean flags enabled, using the empty ignored-property set
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
        Set<String> emptyIgnoredSet = emptyValue.getIgnored();
        JsonIgnoreProperties.Value allFlagsEnabled = new JsonIgnoreProperties.Value(emptyIgnoredSet, true, true, true, true);

        // Build a Value from 8 null property names; defaults: ignoreUnknown=false, merge=true, allowSetters=false
        String[] nullPropertyNames = new String[8];
        JsonIgnoreProperties.Value valueFromNullNames = JsonIgnoreProperties.Value.forIgnoredProperties(nullPropertyNames);
        assertFalse(valueFromNullNames.getIgnoreUnknown());
        assertTrue(valueFromNullNames.getMerge());
        assertFalse(valueFromNullNames.getAllowSetters());

        // Place the two non-null values at indices 3 and 4; indices 0-2 and 5 remain null
        valuesToMerge[3] = valueFromNullNames;
        valuesToMerge[4] = allFlagsEnabled;

        // mergeAll skips null slots; it applies valueFromNullNames first, then OR-merges allFlagsEnabled on top
        // Expected: ignoreUnknown=true, allowGetters=true, allowSetters=true (all OR-ed to true)
        JsonIgnoreProperties.Value mergedValue = JsonIgnoreProperties.Value.mergeAll(valuesToMerge);
        assertTrue(mergedValue.getAllowGetters());
        assertTrue(mergedValue.getAllowSetters());
        assertTrue(mergedValue.getIgnoreUnknown());
        assertNotNull(mergedValue);

        // mergedValue carries the null-name ignored set from valueFromNullNames, so it differs from allFlagsEnabled
        assertFalse(mergedValue.equals((Object) allFlagsEnabled));
        assertNotSame(mergedValue, allFlagsEnabled);
    }
}
