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
public class JsonIgnoreProperties_ESTest_test01 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that merging a base Value with an override that has ignoreUnknown=true
     * produces a result equal to the override, and that default flags (allowGetters,
     * allowSetters) are false while merge=true on the base.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Create a base Value that ignores 6 (null-named) properties, with merge=true by default
        String[] sixNullPropertyNames = new String[6];
        JsonIgnoreProperties.Value baseValue = JsonIgnoreProperties.Value.forIgnoredProperties(sixNullPropertyNames);

        // Derive an override from the base with ignoreUnknown enabled
        JsonIgnoreProperties.Value overrideWithIgnoreUnknown = baseValue.withIgnoreUnknown();

        // Merging base with the override should yield a Value equal to the override
        JsonIgnoreProperties.Value mergedValue = JsonIgnoreProperties.Value.merge(baseValue, overrideWithIgnoreUnknown);
        assertTrue(mergedValue.equals((Object) overrideWithIgnoreUnknown));

        // The merged result inherits ignoreUnknown=true from the override
        assertTrue(mergedValue.getIgnoreUnknown());

        // Default flags: allowGetters and allowSetters remain false on the merged result
        assertFalse(mergedValue.getAllowGetters());
        assertFalse(mergedValue.getAllowSetters());

        // The base Value retains merge=true (its default) and allowSetters=false
        assertTrue(baseValue.getMerge());
        assertFalse(baseValue.getAllowSetters());

        // The merged result is a distinct object from the override even though they are equal
        assertNotSame(mergedValue, overrideWithIgnoreUnknown);
    }
}
