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
public class JsonTypeInfo_ESTest_test20 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        JsonTypeInfo.Value jsonTypeInfo_Value0 = JsonTypeInfo.Value.EMPTY;
        Boolean boolean0 = Boolean.valueOf(false);
        JsonTypeInfo.Value jsonTypeInfo_Value1 = jsonTypeInfo_Value0.withWriteTypeIdForDefaultImpl(boolean0);
        boolean boolean1 = jsonTypeInfo_Value1.equals(jsonTypeInfo_Value0);
        assertFalse(jsonTypeInfo_Value1.getIdVisible());
        assertFalse(boolean1);
        assertFalse(jsonTypeInfo_Value0.equals((Object) jsonTypeInfo_Value1));
    }
}
