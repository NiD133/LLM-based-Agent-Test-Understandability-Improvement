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
public class JacksonInject_ESTest_test02 extends JacksonInject_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;
        Boolean useInput = Boolean.TRUE;

        JacksonInject.Value valueUsingInput = emptyValue.withUseInput(useInput);

        boolean emptyEqualsValueUsingInput = emptyValue.equals(valueUsingInput);
        assertFalse("EMPTY should not equal a value with useInput set to TRUE",
                emptyEqualsValueUsingInput);
        assertFalse("A value with useInput set to TRUE should not equal EMPTY",
                valueUsingInput.equals((Object) emptyValue));
    }
}
