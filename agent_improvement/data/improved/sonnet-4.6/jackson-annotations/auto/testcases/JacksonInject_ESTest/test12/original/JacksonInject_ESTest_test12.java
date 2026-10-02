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
public class JacksonInject_ESTest_test12 extends JacksonInject_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Object object0 = new Object();
        Boolean boolean0 = Boolean.valueOf(true);
        JacksonInject.Value jacksonInject_Value0 = JacksonInject.Value.construct(object0, boolean0, boolean0);
        JacksonInject.Value jacksonInject_Value1 = jacksonInject_Value0.withOptional(boolean0);
        assertTrue(jacksonInject_Value1.hasId());
        assertSame(jacksonInject_Value1, jacksonInject_Value0);
    }
}
