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
public class JsonAutoDetect_ESTest_test23 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        JsonAutoDetect.Visibility jsonAutoDetect_Visibility0 = JsonAutoDetect.Visibility.DEFAULT;
        JsonAutoDetect.Visibility jsonAutoDetect_Visibility1 = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;
        JsonAutoDetect.Value jsonAutoDetect_Value0 = JsonAutoDetect.Value.construct(jsonAutoDetect_Visibility0, jsonAutoDetect_Visibility0, jsonAutoDetect_Visibility1, jsonAutoDetect_Visibility0, jsonAutoDetect_Visibility1, jsonAutoDetect_Visibility1);
        assertNotNull(jsonAutoDetect_Value0);
        JsonAutoDetect.Visibility jsonAutoDetect_Visibility2 = jsonAutoDetect_Value0.getIsGetterVisibility();
        Member member0 = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn(2327).when(member0).getModifiers();
        boolean boolean0 = jsonAutoDetect_Visibility2.isVisible(member0);
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, jsonAutoDetect_Value0.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, jsonAutoDetect_Value0.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, jsonAutoDetect_Value0.getFieldVisibility());
        assertTrue(boolean0);
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, jsonAutoDetect_Value0.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, jsonAutoDetect_Value0.getCreatorVisibility());
    }
}
