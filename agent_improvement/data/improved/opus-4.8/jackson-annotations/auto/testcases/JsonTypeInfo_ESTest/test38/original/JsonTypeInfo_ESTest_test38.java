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
public class JsonTypeInfo_ESTest_test38 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test38() throws Throwable {
        JsonTypeInfo.Value jsonTypeInfo_Value0 = JsonTypeInfo.Value.EMPTY;
        Class<JsonTypeInfo> class0 = jsonTypeInfo_Value0.valueFor();
        JsonTypeInfo.Id jsonTypeInfo_Id0 = JsonTypeInfo.Id.NONE;
        JsonTypeInfo.As jsonTypeInfo_As0 = JsonTypeInfo.As.WRAPPER_ARRAY;
        Boolean boolean0 = Boolean.FALSE;
        JsonTypeInfo.Value jsonTypeInfo_Value1 = JsonTypeInfo.Value.construct(jsonTypeInfo_Id0, jsonTypeInfo_As0, "", class0, true, boolean0, boolean0);
        assertTrue(jsonTypeInfo_Value1.getIdVisible());
        assertFalse(jsonTypeInfo_Value1.shouldWriteTypeIdForDefaultImpl());
    }
}
