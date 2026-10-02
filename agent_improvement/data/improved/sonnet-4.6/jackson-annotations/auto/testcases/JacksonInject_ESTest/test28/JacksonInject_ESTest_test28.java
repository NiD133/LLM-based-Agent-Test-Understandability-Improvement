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
public class JacksonInject_ESTest_test28 extends JacksonInject_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_forId_withCustomObject_hasIdReturnsTrue() throws Throwable {
        Object injectionId = new Object();
        JacksonInject.Value injectValue = JacksonInject.Value.forId(injectionId);

        // valueFor() should return the annotation type without throwing
        Class<JacksonInject> annotationType = injectValue.valueFor();

        assertTrue(injectValue.hasId());
    }
}
