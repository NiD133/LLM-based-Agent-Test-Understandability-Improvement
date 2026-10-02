package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test03 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that merging an override Value onto the EMPTY base Value (obtained
     * via {@code from(null)}) yields a new Value carrying the override's settings.
     */
    @Test(timeout = 4000)
    public void withOverridesMergesOverrideSettingsOntoEmptyBase() throws Throwable {
        // from(null) returns the EMPTY default Value (no ignored props, merge enabled).
        JsonIgnoreProperties.Value baseValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        // Override: no ignored properties, ignoreUnknown=false, allowGetters=true,
        // allowSetters=true, merge=true.
        LinkedHashSet<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value overrideValue = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties, false, true, true, true);

        JsonIgnoreProperties.Value mergedValue = baseValue.withOverrides(overrideValue);

        // The merge produces a distinct instance whose settings match the override.
        assertNotSame(mergedValue, overrideValue);
        assertTrue(mergedValue.equals((Object) overrideValue));
        assertTrue(mergedValue.getAllowGetters());
        assertFalse(mergedValue.getIgnoreUnknown());
        assertTrue(overrideValue.getAllowSetters());
    }
}
