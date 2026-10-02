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
public class JacksonInject_ESTest_test20 extends JacksonInject_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        JacksonInject annotation = mock(JacksonInject.class, CALLS_REAL_METHODS);
        doReturn((OptBoolean) null).when(annotation).useInput();
        doReturn((String) null).when(annotation).value();

        try {
            JacksonInject.Value.from(annotation);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException exception) {
            verifyException("com.fasterxml.jackson.annotation.JacksonInject$Value", exception);
        }
    }
}
