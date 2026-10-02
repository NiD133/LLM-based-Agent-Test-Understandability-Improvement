package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test19 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that withoutAllowSetters() is a no-op when allowSetters is already false,
     * returning the same Value instance unchanged.
     *
     * The constructed Value has: allowSetters=false, allowGetters=true, ignoreUnknown=true, merge=false.
     * Calling withoutAllowSetters() on an already-false allowSetters should return 'this'.
     */
    @Test(timeout = 4000)
    public void test_withoutAllowSetters_isNoOpWhenAllowSettersAlreadyFalse() throws Throwable {
        // Build a Value with allowSetters=false (so withoutAllowSetters should be a no-op)
        JsonIgnoreProperties.Value valueWithAllowSettersFalse = JsonIgnoreProperties.Value.construct(
                (Set<String>) null,
                /* ignoreUnknown */ true,
                /* allowGetters  */ true,
                /* allowSetters  */ false,
                /* merge         */ false);

        // withoutAllowSetters() should return the same instance since allowSetters is already false
        JsonIgnoreProperties.Value resultAfterWithoutAllowSetters = valueWithAllowSettersFalse.withoutAllowSetters();

        assertSame("withoutAllowSetters() should return the same instance when allowSetters is already false",
                valueWithAllowSettersFalse, resultAfterWithoutAllowSetters);
        assertFalse("merge should remain false", resultAfterWithoutAllowSetters.getMerge());
        assertTrue("allowGetters should remain true", resultAfterWithoutAllowSetters.getAllowGetters());
        assertTrue("ignoreUnknown should remain true", resultAfterWithoutAllowSetters.getIgnoreUnknown());
    }
}
