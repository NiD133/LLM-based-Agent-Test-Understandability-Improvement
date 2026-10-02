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
     * Verifies that calling withAllowGetters() on a Value that already allows
     * getters is a no-op: it returns the very same instance and leaves all
     * other settings (ignoreUnknown, allowSetters, merge) unchanged.
     */
    @Test(timeout = 4000)
    public void withAllowGetters_whenGettersAlreadyAllowed_returnsSameInstance() throws Throwable {
        Set<String> ignoredProperties = JsonIgnoreProperties.Value.empty()
                .findIgnoredForSerialization();

        // Build a Value with: ignoreUnknown=true, allowGetters=true, allowSetters=false, merge=false
        JsonIgnoreProperties.Value valueAllowingGetters = JsonIgnoreProperties.Value.construct(
                ignoredProperties, true, true, false, false);

        // Getters are already allowed, so this should return the same instance untouched.
        JsonIgnoreProperties.Value result = valueAllowingGetters.withAllowGetters();

        assertSame(valueAllowingGetters, result);
        assertTrue(result.getIgnoreUnknown());
        assertFalse(result.getAllowSetters());
        assertFalse(result.getMerge());
    }
}
