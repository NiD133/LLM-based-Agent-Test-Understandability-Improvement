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
public class JacksonInject_ESTest_test26 extends JacksonInject_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        JacksonInject.Value jacksonInject_Value0 = JacksonInject.Value.EMPTY;
        Boolean boolean0 = Boolean.valueOf(false);
        JacksonInject.Value jacksonInject_Value1 = JacksonInject.Value.construct((Object) jacksonInject_Value0, boolean0, boolean0);
        boolean boolean1 = jacksonInject_Value0.equals(jacksonInject_Value1);
        assertFalse(boolean1);
        assertTrue(jacksonInject_Value1.hasId());
    }
}
