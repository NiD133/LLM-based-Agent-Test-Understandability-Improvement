package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test07 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that when useInput is explicitly set to TRUE on a Value,
     * willUseInput() returns true even when the supplied default is false.
     * The explicit setting takes precedence over the default argument.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Start from the empty Value (no id, no useInput, no optional)
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;

        // Produce a new Value that explicitly enables useInput
        JacksonInject.Value valueWithUseInputEnabled = emptyValue.withUseInput(Boolean.TRUE);

        // willUseInput(false): the default is false, but the stored useInput=true overrides it
        boolean actuallyUsesInput = valueWithUseInputEnabled.willUseInput(false);

        assertTrue(actuallyUsesInput);
    }
}
