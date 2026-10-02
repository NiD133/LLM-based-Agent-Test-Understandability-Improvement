package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test20 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonIgnoreProperties.Value#withoutAllowSetters()} returns a
     * distinct instance with "allowSetters" cleared, while leaving the other settings
     * (allowGetters, ignoreUnknown, merge) untouched.
     */
    @Test(timeout = 4000)
    public void withoutAllowSetters_returnsNewInstanceWithSettersDisabled() throws Throwable {
        // Start from a Value where only "allowSetters" is enabled.
        JsonIgnoreProperties.Value valueWithSetters = JsonIgnoreProperties.Value.construct(
                (Set<String>) null,
                /* ignoreUnknown */ false,
                /* allowGetters  */ false,
                /* allowSetters  */ true,
                /* merge         */ false);

        // Disabling setters must produce a brand-new Value instance.
        JsonIgnoreProperties.Value valueWithoutSetters = valueWithSetters.withoutAllowSetters();
        assertNotSame(valueWithoutSetters, valueWithSetters);

        // The new instance has "allowSetters" turned off...
        assertFalse(valueWithoutSetters.getAllowSetters());

        // ...and the remaining settings are unchanged in both instances.
        assertFalse(valueWithSetters.getAllowGetters());
        assertFalse(valueWithSetters.getIgnoreUnknown());
        assertFalse(valueWithoutSetters.getIgnoreUnknown());
        assertFalse(valueWithoutSetters.getMerge());
    }
}
