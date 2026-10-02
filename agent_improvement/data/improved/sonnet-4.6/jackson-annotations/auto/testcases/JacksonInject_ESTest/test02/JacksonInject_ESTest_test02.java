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
    public void test02_emptyValueNotEqualToValueWithUseInputTrue() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;
        JacksonInject.Value valueWithUseInputTrue = emptyValue.withUseInput(Boolean.TRUE);

        // EMPTY has useInput=null; valueWithUseInputTrue has useInput=TRUE — they must differ
        assertFalse(emptyValue.equals(valueWithUseInputTrue));
        assertFalse(valueWithUseInputTrue.equals((Object) emptyValue));
    }
}
