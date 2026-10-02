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
public class JsonAutoDetect_ESTest_test32 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test32() throws Throwable {
        JsonAutoDetect.Visibility jsonAutoDetect_Visibility0 = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Value jsonAutoDetect_Value0 = JsonAutoDetect.Value.construct(jsonAutoDetect_Visibility0, jsonAutoDetect_Visibility0, jsonAutoDetect_Visibility0, jsonAutoDetect_Visibility0, jsonAutoDetect_Visibility0, jsonAutoDetect_Visibility0);
        jsonAutoDetect_Value0.valueFor();
        assertEquals(JsonAutoDetect.Visibility.NONE, jsonAutoDetect_Value0.getIsGetterVisibility());
    }
}
