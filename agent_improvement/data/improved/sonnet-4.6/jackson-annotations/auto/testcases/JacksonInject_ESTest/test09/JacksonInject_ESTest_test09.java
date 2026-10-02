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
public class JacksonInject_ESTest_test09 extends JacksonInject_ESTest_scaffolding {

    /**
     * The EMPTY singleton represents an injection descriptor with no id, no useInput,
     * and no optional flag set. Verifies that hasId() returns false when the id is null.
     */
    @Test(timeout = 4000)
    public void test_emptyValue_hasNoInjectionId() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;

        boolean hasId = emptyValue.hasId();

        assertFalse("EMPTY JacksonInject.Value should report no injection id", hasId);
    }
}
