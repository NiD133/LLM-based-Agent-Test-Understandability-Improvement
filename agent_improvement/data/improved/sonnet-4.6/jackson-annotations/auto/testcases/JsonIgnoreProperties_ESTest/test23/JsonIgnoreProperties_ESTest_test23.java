package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test23 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@code withoutAllowGetters()} returns a new {@link JsonIgnoreProperties.Value}
     * instance with getter access disabled, while leaving all other settings unchanged.
     */
    @Test(timeout = 4000)
    public void test23() throws Throwable {
        // The empty Value has no ignored properties and allowGetters=false,
        // so findIgnoredForSerialization() returns an empty set.
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
        Set<String> ignoredForSerialization = emptyValue.findIgnoredForSerialization();

        // Build a Value that: ignores unknown properties, allows getters,
        // but does NOT allow setters and does NOT merge with other configurations.
        JsonIgnoreProperties.Value valueWithGettersAllowed = JsonIgnoreProperties.Value.construct(
                ignoredForSerialization,
                /*ignoreUnknown=*/ true,
                /*allowGetters=*/  true,
                /*allowSetters=*/  false,
                /*merge=*/         false);

        // Disabling allowGetters must produce a new instance (since it was previously enabled).
        JsonIgnoreProperties.Value valueWithGettersDisabled = valueWithGettersAllowed.withoutAllowGetters();
        assertNotSame(valueWithGettersDisabled, valueWithGettersAllowed);

        // The original value retains its configured settings.
        assertTrue(valueWithGettersAllowed.getIgnoreUnknown());
        assertFalse(valueWithGettersAllowed.getAllowSetters());
        assertFalse(valueWithGettersAllowed.getMerge());

        // After withoutAllowGetters(), only allowGetters changes; all other settings are preserved.
        assertFalse(valueWithGettersDisabled.getAllowGetters());
        assertTrue(valueWithGettersDisabled.getIgnoreUnknown());
        assertFalse(valueWithGettersDisabled.getAllowSetters());
        assertFalse(valueWithGettersDisabled.getMerge());
    }
}
