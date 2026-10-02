package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test10 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        JsonAutoDetect jsonAutoDetect0 = mock(JsonAutoDetect.class, CALLS_REAL_METHODS);
        doReturn((JsonAutoDetect.Visibility) null).when(jsonAutoDetect0).creatorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(jsonAutoDetect0).fieldVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(jsonAutoDetect0).getterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(jsonAutoDetect0).isGetterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(jsonAutoDetect0).scalarConstructorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(jsonAutoDetect0).setterVisibility();
        JsonAutoDetect.Value jsonAutoDetect_Value0 = JsonAutoDetect.Value.from(jsonAutoDetect0);
        JsonAutoDetect.Visibility jsonAutoDetect_Visibility0 = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;
        boolean boolean0 = jsonAutoDetect_Value0.equals(jsonAutoDetect_Visibility0);
        assertFalse(boolean0);
    }
}
