package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test45 extends JsonFormat_ESTest_scaffolding {

    /**
     * Disabling a feature on a default Value produces a distinct Value that is
     * no longer equal to the original (in either direction), while neither
     * Value reports a non-default radix.
     */
    @Test(timeout = 4000)
    public void withoutFeatureProducesUnequalValueAndKeepsDefaultRadix() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        JsonFormat.Value withoutSortedMapEntries =
                defaultValue.withoutFeature(JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES);

        assertFalse("Disabling a feature should change equality",
                withoutSortedMapEntries.equals(defaultValue));
        assertFalse("Inequality should hold in both directions",
                defaultValue.equals(withoutSortedMapEntries));

        assertFalse("Default value keeps the default radix",
                defaultValue.hasNonDefaultRadix());
        assertFalse("Disabling a feature does not change the radix",
                withoutSortedMapEntries.hasNonDefaultRadix());
    }
}
