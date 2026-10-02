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
public class JsonSetter_ESTest_test17 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        Nulls nulls0 = Nulls.DEFAULT;
        JsonSetter.Value jsonSetter_Value0 = JsonSetter.Value.forContentNulls(nulls0);
        Nulls nulls1 = Nulls.SET;
        JsonSetter.Value jsonSetter_Value1 = jsonSetter_Value0.withValueNulls(nulls0, nulls1);
        JsonSetter.Value jsonSetter_Value2 = jsonSetter_Value0.withOverrides(jsonSetter_Value1);
        assertEquals(Nulls.SET, jsonSetter_Value2.getContentNulls());
        assertEquals(Nulls.DEFAULT, jsonSetter_Value2.getValueNulls());
    }
}
