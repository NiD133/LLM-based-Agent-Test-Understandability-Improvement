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
public class JacksonInject_ESTest_test16 extends JacksonInject_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        Object injectionId = new Object();
        JacksonInject.Value valueWithId = JacksonInject.Value.forId(injectionId);

        JacksonInject.Value valueAfterSameId = valueWithId.withId(injectionId);

        assertSame(valueAfterSameId, valueWithId);
    }
}
