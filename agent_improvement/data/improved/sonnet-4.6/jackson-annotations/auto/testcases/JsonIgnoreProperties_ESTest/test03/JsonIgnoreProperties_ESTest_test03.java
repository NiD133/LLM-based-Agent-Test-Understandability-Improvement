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
public class JsonIgnoreProperties_ESTest_test03 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that withOverrides() merges allowGetters and allowSetters from the override
     * into the base value, and that the resulting merged value is equal (but not identical)
     * to the override when both have the same effective settings.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Base value: created from null annotation, which produces the EMPTY default
        // (ignoreUnknown=false, allowGetters=false, allowSetters=false, merge=true)
        JsonIgnoreProperties.Value baseValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        // Override value: no ignored properties, allowGetters=true, allowSetters=true, merge=true
        LinkedHashSet<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value overrideValue = JsonIgnoreProperties.Value.construct(noIgnoredProperties, false, true, true, true);

        // Merge: OR-combines the boolean flags, so allowGetters and allowSetters become true
        JsonIgnoreProperties.Value mergedValue = baseValue.withOverrides(overrideValue);

        // The merged result should allow getters (base=false OR override=true => true)
        assertTrue(mergedValue.getAllowGetters());

        // The merged result is a new instance, not the same object as the override
        assertNotSame(mergedValue, overrideValue);

        // The merged result is logically equal to the override (same flags and empty ignored set)
        assertTrue(mergedValue.equals((Object) overrideValue));

        // ignoreUnknown was false in both base and override, so it stays false after merging
        assertFalse(mergedValue.getIgnoreUnknown());

        // The override value itself still has allowSetters=true
        assertTrue(overrideValue.getAllowSetters());
    }
}
