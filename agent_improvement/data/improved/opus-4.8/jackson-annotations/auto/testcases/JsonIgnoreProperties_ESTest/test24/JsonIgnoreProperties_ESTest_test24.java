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
     * Verifies that calling withAllowGetters() on a Value whose allowGetters flag
     * is already true returns the same instance (no copy is made), and that all
     * other flags are preserved.
     */
    @Test(timeout = 4000)
    public void withAllowGetters_whenAlreadyAllowed_returnsSameInstance() throws Throwable {
        Set<String> ignoredProperties = JsonIgnoreProperties.Value.empty().findIgnoredForSerialization();

        // construct(ignored, ignoreUnknown=true, allowGetters=true, allowSetters=false, merge=false)
        JsonIgnoreProperties.Value valueWithAllowGetters =
                JsonIgnoreProperties.Value.construct(ignoredProperties, true, true, false, false);

        // allowGetters is already true, so withAllowGetters() should return the very same object.
        JsonIgnoreProperties.Value result = valueWithAllowGetters.withAllowGetters();

        assertSame(valueWithAllowGetters, result);
        assertTrue(result.getIgnoreUnknown());
        assertFalse(result.getAllowSetters());
        assertFalse(result.getMerge());
    }
}
