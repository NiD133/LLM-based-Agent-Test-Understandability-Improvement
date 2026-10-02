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
public class JsonTypeInfo_ESTest_test30 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        JsonTypeInfo.Id jsonTypeInfo_Id0 = JsonTypeInfo.Id.CUSTOM;
        JsonTypeInfo.As jsonTypeInfo_As0 = JsonTypeInfo.As.EXTERNAL_PROPERTY;
        Boolean boolean0 = Boolean.TRUE;
        JsonTypeInfo.Value jsonTypeInfo_Value0 = JsonTypeInfo.Value.construct(jsonTypeInfo_Id0, jsonTypeInfo_As0, "Gr9fYPjBd{JN", (Class<?>) null, true, boolean0, boolean0);
        assertTrue(jsonTypeInfo_Value0.getIdVisible());
    }
}
