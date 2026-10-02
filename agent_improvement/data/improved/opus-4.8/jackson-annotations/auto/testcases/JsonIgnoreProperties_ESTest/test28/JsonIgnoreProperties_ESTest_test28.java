package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test28 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonIgnoreProperties.Value#mergeAll} performs a logical OR
     * of the boolean flags across the non-null values it is given.
     *
     * Two values are merged:
     *   - an "all flags on" value (ignoreUnknown / allowGetters / allowSetters all true)
     *   - a value built from ignored property names, which has all those flags off
     * The merged result must therefore have every flag set to true.
     */
    @Test(timeout = 4000)
    public void mergeAllOrsBooleanFlagsAcrossValues() throws Throwable {
        // mergeAll accepts varargs and skips null entries, so a sparse array is fine.
        JsonIgnoreProperties.Value[] valuesToMerge = new JsonIgnoreProperties.Value[6];

        // First value to merge: every boolean flag enabled (ignoreUnknown, allowGetters,
        // allowSetters, merge), with no ignored property names.
        Set<String> noIgnoredProperties = JsonIgnoreProperties.Value.empty().getIgnored();
        JsonIgnoreProperties.Value allFlagsOn =
                new JsonIgnoreProperties.Value(noIgnoredProperties, true, true, true, true);
        valuesToMerge[1] = allFlagsOn;

        // Second value to merge: derived purely from ignored property names, so all
        // behavioural flags default to false.
        String[] ignoredPropertyNames = new String[8];
        JsonIgnoreProperties.Value flagsOff =
                JsonIgnoreProperties.Value.forIgnoredProperties(ignoredPropertyNames);
        assertFalse(flagsOff.getIgnoreUnknown());
        assertFalse(flagsOff.getAllowGetters());
        assertFalse(flagsOff.getAllowSetters());
        valuesToMerge[3] = flagsOff;

        // Merge: flags are OR-ed together, so every flag ends up true.
        JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.mergeAll(valuesToMerge);
        assertNotNull(merged);
        assertTrue(merged.getIgnoreUnknown());
        assertTrue(merged.getAllowGetters());
        assertTrue(merged.getAllowSetters());

        // The merge produced a brand new value distinct from the inputs.
        assertNotSame(merged, allFlagsOn);
        assertFalse(merged.equals((Object) allFlagsOn));
    }
}
