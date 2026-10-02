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
public class JsonAutoDetect_ESTest_test13 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        JsonAutoDetect.Visibility jsonAutoDetect_Visibility0 = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Value jsonAutoDetect_Value0 = JsonAutoDetect.Value.construct(jsonAutoDetect_Visibility0, jsonAutoDetect_Visibility0, jsonAutoDetect_Visibility0, jsonAutoDetect_Visibility0, jsonAutoDetect_Visibility0, jsonAutoDetect_Visibility0);
        PropertyAccessor propertyAccessor0 = PropertyAccessor.SCALAR_CONSTRUCTOR;
        JsonAutoDetect.Value jsonAutoDetect_Value1 = JsonAutoDetect.Value.construct(propertyAccessor0, jsonAutoDetect_Visibility0);
        JsonAutoDetect.Value jsonAutoDetect_Value2 = jsonAutoDetect_Value0.withOverrides(jsonAutoDetect_Value1);
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, jsonAutoDetect_Value1.getGetterVisibility());
        assertSame(jsonAutoDetect_Value2, jsonAutoDetect_Value0);
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, jsonAutoDetect_Value1.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, jsonAutoDetect_Value1.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, jsonAutoDetect_Value1.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, jsonAutoDetect_Value1.getScalarConstructorVisibility());
    }
}
