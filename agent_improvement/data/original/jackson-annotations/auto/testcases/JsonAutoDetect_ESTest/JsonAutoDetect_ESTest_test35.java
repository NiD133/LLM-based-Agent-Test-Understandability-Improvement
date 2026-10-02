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
public class JsonAutoDetect_ESTest_test35 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test35() throws Throwable {
        JsonAutoDetect.Value jsonAutoDetect_Value0 = JsonAutoDetect.Value.DEFAULT;
        JsonAutoDetect.Visibility jsonAutoDetect_Visibility0 = JsonAutoDetect.Visibility.NON_PRIVATE;
        JsonAutoDetect.Value jsonAutoDetect_Value1 = jsonAutoDetect_Value0.withScalarConstructorVisibility(jsonAutoDetect_Visibility0);
        assertSame(jsonAutoDetect_Value1, jsonAutoDetect_Value0);
    }
}
