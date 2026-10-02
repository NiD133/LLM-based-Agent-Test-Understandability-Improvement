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
public class JacksonInject_ESTest_test23 extends JacksonInject_ESTest_scaffolding {

    /**
     * EMPTY is the sentinel "no configuration" value; its optional field should be null
     * because no optional setting has been specified.
     */
    @Test(timeout = 4000)
    public void test_emptyValue_getOptional_returnsNull() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;

        Boolean optional = emptyValue.getOptional();

        assertNull(optional);
    }
}
