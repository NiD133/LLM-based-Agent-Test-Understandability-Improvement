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
public class JsonIgnoreProperties_ESTest_test28 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that mergeAll combines Value instances from a sparse array (with nulls),
     * producing a result that OR-merges boolean flags from all non-null entries.
     *
     * Setup:
     *   - valueWithAllFlagsTrue (index 1): ignoreUnknown=true, allowGetters=true, allowSetters=true, merge=true
     *   - valueWithEightNullProperties (index 3): ignoreUnknown=false, allowGetters=false, allowSetters=false
     *   - Indices 0, 2, 4, 5 are null and must be skipped by mergeAll
     *
     * Expected: the merged result inherits the true flags from valueWithAllFlagsTrue,
     * and is a distinct object (not the same reference as valueWithAllFlagsTrue).
     */
    @Test(timeout = 4000)
    public void test28() throws Throwable {
        // Build a sparse array of Value instances; most slots left null
        JsonIgnoreProperties.Value[] valuesArray = new JsonIgnoreProperties.Value[6];

        // Index 1: a Value with all boolean flags set to true
        Set<String> emptyIgnoredSet = JsonIgnoreProperties.Value.empty().getIgnored();
        JsonIgnoreProperties.Value valueWithAllFlagsTrue =
                new JsonIgnoreProperties.Value(emptyIgnoredSet, true, true, true, true);
        valuesArray[1] = valueWithAllFlagsTrue;

        // Index 3: a Value created from 8 property names (all null strings);
        // forIgnoredProperties sets ignoreUnknown/allowGetters/allowSetters to false by default
        String[] eightNullPropertyNames = new String[8];
        JsonIgnoreProperties.Value valueWithEightNullProperties =
                JsonIgnoreProperties.Value.forIgnoredProperties(eightNullPropertyNames);
        assertFalse(valueWithEightNullProperties.getIgnoreUnknown());
        assertFalse(valueWithEightNullProperties.getAllowGetters());
        assertFalse(valueWithEightNullProperties.getAllowSetters());
        valuesArray[3] = valueWithEightNullProperties;

        // Act: mergeAll skips null slots and OR-merges boolean flags across non-null Values
        JsonIgnoreProperties.Value mergedValue = JsonIgnoreProperties.Value.mergeAll(valuesArray);

        // Assert: merged result reflects OR of flags; true flags from valueWithAllFlagsTrue dominate
        assertTrue(mergedValue.getAllowSetters());
        assertNotNull(mergedValue);
        assertNotSame(mergedValue, valueWithAllFlagsTrue);
        assertFalse(mergedValue.equals((Object) valueWithAllFlagsTrue));
        assertTrue(mergedValue.getAllowGetters());
        assertTrue(mergedValue.getIgnoreUnknown());
    }
}
