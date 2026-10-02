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
public class JsonTypeInfo_ESTest_test33 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test33() throws Throwable {
        JsonTypeInfo.Id jsonTypeInfo_Id0 = JsonTypeInfo.Id.NONE;
        JsonTypeInfo.As jsonTypeInfo_As0 = JsonTypeInfo.As.PROPERTY;
        Class<Object> class0 = Object.class;
        Boolean boolean0 = new Boolean(true);
        JsonTypeInfo.Value jsonTypeInfo_Value0 = JsonTypeInfo.Value.construct(jsonTypeInfo_Id0, jsonTypeInfo_As0, "", class0, true, boolean0, boolean0);
        boolean boolean1 = jsonTypeInfo_Value0.getIdVisible();
        assertTrue(boolean1);
    }
}
