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
public class JacksonInject_ESTest_test21 extends JacksonInject_ESTest_scaffolding {

    /**
     * When a Value is created via forId(null), the useInput field is not set,
     * so getUseInput() should return null (no preference configured).
     */
    @Test(timeout = 4000)
    public void test_forId_withNullId_useInputIsNull() throws Throwable {
        JacksonInject.Value valueWithNullId = JacksonInject.Value.forId((Object) null);

        assertNull(valueWithNullId.getUseInput());
    }
}
