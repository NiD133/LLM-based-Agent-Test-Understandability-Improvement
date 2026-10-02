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
public class JsonAutoDetect_ESTest_test03 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        JsonAutoDetect.Value jsonAutoDetect_Value0 = JsonAutoDetect.Value.noOverrides();
        PropertyAccessor propertyAccessor0 = PropertyAccessor.GETTER;
        JsonAutoDetect.Visibility jsonAutoDetect_Visibility0 = JsonAutoDetect.Visibility.NON_PRIVATE;
        JsonAutoDetect.Value jsonAutoDetect_Value1 = JsonAutoDetect.Value.construct(propertyAccessor0, jsonAutoDetect_Visibility0);
        JsonAutoDetect.Value jsonAutoDetect_Value2 = JsonAutoDetect.Value.merge(jsonAutoDetect_Value0, jsonAutoDetect_Value1);
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, jsonAutoDetect_Value2.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, jsonAutoDetect_Value2.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, jsonAutoDetect_Value2.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, jsonAutoDetect_Value2.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, jsonAutoDetect_Value2.getScalarConstructorVisibility());
    }
}
