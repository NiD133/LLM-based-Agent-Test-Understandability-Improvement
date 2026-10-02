package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test00 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonIgnoreProperties.Value#mergeAll} combines several
     * {@code Value} entries (skipping the null array slots) by OR-ing their boolean
     * flags together, and that the merged result is a distinct instance from its inputs.
     */
    @Test(timeout = 4000)
    public void mergeAllCombinesFlagsAndProducesDistinctValue() throws Throwable {
        // A Value with every boolean flag enabled (ignoreUnknown, allowGetters,
        // allowSetters, merge) and no ignored property names.
        Set<String> noIgnoredNames = JsonIgnoreProperties.Value.empty().getIgnored();
        JsonIgnoreProperties.Value allFlagsEnabled =
                new JsonIgnoreProperties.Value(noIgnoredNames, true, true, true, true);

        // A Value built from a property-name array; the resulting Value keeps the
        // default flags (no ignoreUnknown, no allowGetters/Setters) but does enable merge.
        String[] propertyNames = new String[8];
        JsonIgnoreProperties.Value fromProperties =
                JsonIgnoreProperties.Value.forIgnoredProperties(propertyNames);
        assertFalse(fromProperties.getIgnoreUnknown());
        assertTrue(fromProperties.getMerge());
        assertFalse(fromProperties.getAllowSetters());

        // mergeAll ignores the null slots and merges the two Values it finds.
        JsonIgnoreProperties.Value[] toMerge = new JsonIgnoreProperties.Value[6];
        toMerge[3] = fromProperties;
        toMerge[4] = allFlagsEnabled;
        JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.mergeAll(toMerge);

        // The flags from allFlagsEnabled are OR-ed into the merged result.
        assertNotNull(merged);
        assertTrue(merged.getAllowGetters());
        assertTrue(merged.getAllowSetters());
        assertTrue(merged.getIgnoreUnknown());

        // The merge also carries over the ignored property name, so the result is
        // neither equal to nor the same instance as the all-flags input.
        assertFalse(merged.equals((Object) allFlagsEnabled));
        assertNotSame(merged, allFlagsEnabled);
    }
}
