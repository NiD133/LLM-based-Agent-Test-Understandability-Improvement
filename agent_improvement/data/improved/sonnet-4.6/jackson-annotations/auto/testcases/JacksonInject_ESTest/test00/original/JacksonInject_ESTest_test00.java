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
public class JacksonInject_ESTest_test00 extends JacksonInject_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Boolean boolean0 = Boolean.valueOf(true);
        JacksonInject.Value jacksonInject_Value0 = JacksonInject.Value.construct((Object) null, boolean0, boolean0);
        assertTrue(jacksonInject_Value0.getUseInput());
    }
}
