package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test27 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that calling {@link JsonIgnoreProperties.Value#withIgnoreUnknown()}
     * on a Value whose ignoreUnknown flag is already {@code true} is a no-op:
     * it returns the very same instance and leaves all other flags untouched.
     */
    @Test(timeout = 4000)
    public void withIgnoreUnknownReturnsSameInstanceWhenAlreadyEnabled() throws Throwable {
        // Build a Value with: ignoreUnknown=true, allowGetters=true, allowSetters=false, merge=false
        JsonIgnoreProperties.Value original = JsonIgnoreProperties.Value.construct(
                (Set<String>) null, true, true, false, false);

        JsonIgnoreProperties.Value result = original.withIgnoreUnknown();

        // ignoreUnknown was already true, so the same instance is returned unchanged
        assertSame(original, result);

        // The remaining flags are preserved exactly as constructed
        assertFalse("merge should remain false", result.getMerge());
        assertTrue("allowGetters should remain true", result.getAllowGetters());
        assertFalse("allowSetters should remain false", result.getAllowSetters());
    }
}
