package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test24 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that withAllowGetters() returns the same instance when allowGetters is already true,
     * and that the other properties (merge=false, allowSetters=false, ignoreUnknown=true) remain unchanged.
     */
    @Test(timeout = 4000)
    public void test24() throws Throwable {
        // Obtain the ignored-for-serialization set from the default empty Value (no ignored fields)
        Set<String> ignoredProperties = JsonIgnoreProperties.Value.empty().findIgnoredForSerialization();

        // Build a Value with: ignoreUnknown=true, allowGetters=true, allowSetters=false, merge=false
        JsonIgnoreProperties.Value valueWithAllowGettersEnabled = JsonIgnoreProperties.Value.construct(
                ignoredProperties,
                /* ignoreUnknown */ true,
                /* allowGetters  */ true,
                /* allowSetters  */ false,
                /* merge         */ false);

        // withAllowGetters() is a no-op when allowGetters is already true — must return the same instance
        JsonIgnoreProperties.Value result = valueWithAllowGettersEnabled.withAllowGetters();

        assertSame(result, valueWithAllowGettersEnabled);
        assertTrue(result.getIgnoreUnknown());
        assertFalse(result.getAllowSetters());
        assertFalse(result.getMerge());
    }
}
