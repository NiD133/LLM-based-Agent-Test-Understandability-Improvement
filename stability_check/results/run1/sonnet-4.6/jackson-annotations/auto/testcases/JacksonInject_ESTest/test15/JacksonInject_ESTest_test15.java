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
public class JacksonInject_ESTest_test15 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that calling withUseInput() with the same Boolean value that is already set
     * returns the identical Value instance (no new object created), and that hasId() still
     * returns true because the non-null id is preserved across the no-op mutation.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Object injectionId = new Object();
        Boolean useInputEnabled = Boolean.valueOf(true);

        // Construct a Value with a non-null id, useInput=true, and optional=true
        JacksonInject.Value originalValue = JacksonInject.Value.construct(injectionId, useInputEnabled, useInputEnabled);

        // withUseInput with the same value already set should return the same instance
        JacksonInject.Value valueAfterNoOpMutation = originalValue.withUseInput(useInputEnabled);

        assertTrue(valueAfterNoOpMutation.hasId());
        assertSame(valueAfterNoOpMutation, originalValue);
    }
}
