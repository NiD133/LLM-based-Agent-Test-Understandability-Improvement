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
public class JsonTypeInfo_ESTest_test12 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        JsonTypeInfo.Id jsonTypeInfo_Id0 = JsonTypeInfo.Id.MINIMAL_CLASS;
        JsonTypeInfo.As jsonTypeInfo_As0 = JsonTypeInfo.As.WRAPPER_ARRAY;
        Class<Object> class0 = Object.class;
        Boolean boolean0 = Boolean.TRUE;
        JsonTypeInfo.Value jsonTypeInfo_Value0 = JsonTypeInfo.Value.construct(jsonTypeInfo_Id0, jsonTypeInfo_As0, "-HHcn77L=.C2", class0, false, boolean0, boolean0);
        boolean boolean1 = JsonTypeInfo.Value.isEnabled(jsonTypeInfo_Value0);
        assertEquals("-HHcn77L=.C2", jsonTypeInfo_Value0.getPropertyName());
        assertTrue(boolean1);
        assertFalse(jsonTypeInfo_Value0.getIdVisible());
    }
}
