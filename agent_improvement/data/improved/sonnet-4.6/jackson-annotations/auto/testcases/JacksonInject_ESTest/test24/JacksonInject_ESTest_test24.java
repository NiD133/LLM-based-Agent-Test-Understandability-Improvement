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
public class JacksonInject_ESTest_test24 extends JacksonInject_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_constructWithIdAndUseInput_returnsValueWithNonNullUseInputAndHasId() throws Throwable {
        // Given a non-null injection id and useInput=true
        Object injectionId = new Object();
        Boolean useInput = Boolean.TRUE;

        // When constructing a JacksonInject.Value with an explicit id and useInput flag
        JacksonInject.Value injectValue = JacksonInject.Value.construct(injectionId, useInput, useInput);

        // Then getUseInput() should return a non-null Boolean and hasId() should be true
        Boolean retrievedUseInput = injectValue.getUseInput();
        assertNotNull(retrievedUseInput);
        assertTrue(injectValue.hasId());
    }
}
