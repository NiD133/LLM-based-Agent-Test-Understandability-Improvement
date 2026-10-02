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
public class JsonTypeInfo_ESTest_test31 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test31() throws Throwable {
        JsonTypeInfo.Id jsonTypeInfo_Id0 = JsonTypeInfo.Id.MINIMAL_CLASS;
        Boolean boolean0 = Boolean.valueOf(false);
        JsonTypeInfo.As jsonTypeInfo_As0 = JsonTypeInfo.As.NOTHING;
        Class<Integer> class0 = Integer.class;
        JsonTypeInfo.Value jsonTypeInfo_Value0 = JsonTypeInfo.Value.construct(jsonTypeInfo_Id0, jsonTypeInfo_As0, (String) null, class0, true, boolean0, (Boolean) null);
        assertEquals("@c", jsonTypeInfo_Value0.getPropertyName());
        assertTrue(jsonTypeInfo_Value0.getIdVisible());
        assertTrue(jsonTypeInfo_Value0.shouldWriteTypeIdForDefaultImpl());
    }
}
