package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test09 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Nulls nulls0 = Nulls.FAIL;
        JsonSetter.Value jsonSetter_Value0 = JsonSetter.Value.construct(nulls0, nulls0);
        JsonSetter.Value jsonSetter_Value1 = jsonSetter_Value0.withContentNulls((Nulls) null);
        assertEquals(Nulls.FAIL, jsonSetter_Value0.getContentNulls());
        assertEquals(Nulls.DEFAULT, jsonSetter_Value1.getContentNulls());
        assertEquals(Nulls.FAIL, jsonSetter_Value1.getValueNulls());
    }
}
