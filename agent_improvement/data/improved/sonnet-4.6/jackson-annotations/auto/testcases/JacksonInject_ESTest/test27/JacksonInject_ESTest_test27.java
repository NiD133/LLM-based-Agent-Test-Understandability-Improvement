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
public class JacksonInject_ESTest_test27 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that the empty Value instance has no 'optional' flag set —
     * getOptional() must return null because EMPTY is constructed with all nulls.
     */
    @Test(timeout = 4000)
    public void test_emptyValue_getOptional_returnsNull() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.empty();
        assertNull(
            "JacksonInject.Value.empty() should have no 'optional' flag (null)",
            emptyValue.getOptional()
        );
    }
}
