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
public class JsonSetter_ESTest_test23 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        JsonSetter.Value jsonSetter_Value0 = JsonSetter.Value.empty();
        JsonSetter.Value jsonSetter_Value1 = (JsonSetter.Value) jsonSetter_Value0.readResolve();
        assertEquals(Nulls.DEFAULT, jsonSetter_Value1.getContentNulls());
    }
}
