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
public class JsonTypeInfo_ESTest_test06 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        JsonTypeInfo.Id jsonTypeInfo_Id0 = JsonTypeInfo.Id.NONE;
        JsonTypeInfo.As jsonTypeInfo_As0 = JsonTypeInfo.As.WRAPPER_OBJECT;
        Class<Integer> class0 = Integer.class;
        Boolean boolean0 = Boolean.valueOf("-0VzDY5^*");
        JsonTypeInfo.Value jsonTypeInfo_Value0 = new JsonTypeInfo.Value(jsonTypeInfo_Id0, jsonTypeInfo_As0, "-0VzDY5^*", class0, false, boolean0, boolean0);
        Boolean boolean1 = new Boolean(false);
        JsonTypeInfo.Value jsonTypeInfo_Value1 = jsonTypeInfo_Value0.withWriteTypeIdForDefaultImpl(boolean1);
        boolean boolean2 = jsonTypeInfo_Value0.equals(jsonTypeInfo_Value1);
        assertTrue(boolean2);
        assertNotSame(jsonTypeInfo_Value1, jsonTypeInfo_Value0);
        assertFalse(jsonTypeInfo_Value1.shouldWriteTypeIdForDefaultImpl());
    }
}
