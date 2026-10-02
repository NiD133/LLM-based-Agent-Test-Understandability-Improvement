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
public class JsonTypeInfo_ESTest_test04 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        JsonTypeInfo.Value jsonTypeInfo_Value0 = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.As jsonTypeInfo_As0 = JsonTypeInfo.As.WRAPPER_ARRAY;
        JsonTypeInfo.Value jsonTypeInfo_Value1 = jsonTypeInfo_Value0.withInclusionType(jsonTypeInfo_As0);
        boolean boolean0 = jsonTypeInfo_Value1.equals(jsonTypeInfo_Value0);
        assertFalse(jsonTypeInfo_Value1.getIdVisible());
        assertFalse(jsonTypeInfo_Value0.equals((Object) jsonTypeInfo_Value1));
        assertFalse(boolean0);
    }
}
