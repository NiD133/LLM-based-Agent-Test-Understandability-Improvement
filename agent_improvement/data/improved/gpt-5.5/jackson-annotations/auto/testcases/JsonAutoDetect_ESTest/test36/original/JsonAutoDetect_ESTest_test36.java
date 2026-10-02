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
public class JsonAutoDetect_ESTest_test36 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test36() throws Throwable {
        JsonAutoDetect.Value jsonAutoDetect_Value0 = JsonAutoDetect.Value.DEFAULT;
        JsonAutoDetect.Visibility jsonAutoDetect_Visibility0 = JsonAutoDetect.Visibility.NON_PRIVATE;
        JsonAutoDetect.Value jsonAutoDetect_Value1 = jsonAutoDetect_Value0.withCreatorVisibility(jsonAutoDetect_Visibility0);
        assertNotNull(jsonAutoDetect_Value1);
        JsonAutoDetect.Value jsonAutoDetect_Value2 = jsonAutoDetect_Value1.withOverrides(jsonAutoDetect_Value0);
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, jsonAutoDetect_Value1.getFieldVisibility());
        assertSame(jsonAutoDetect_Value2, jsonAutoDetect_Value0);
        assertEquals(JsonAutoDetect.Visibility.ANY, jsonAutoDetect_Value1.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, jsonAutoDetect_Value1.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, jsonAutoDetect_Value1.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, jsonAutoDetect_Value1.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, jsonAutoDetect_Value1.getScalarConstructorVisibility());
    }
}
