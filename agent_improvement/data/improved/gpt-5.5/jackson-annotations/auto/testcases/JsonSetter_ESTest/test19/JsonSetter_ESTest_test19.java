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
public class JsonSetter_ESTest_test19 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        Nulls failNullHandling = Nulls.FAIL;
        JsonSetter.Value baseSetterValue = JsonSetter.Value.construct(failNullHandling, failNullHandling);

        JsonSetter.Value mergedSetterValue = baseSetterValue.withOverrides((JsonSetter.Value) null);

        assertEquals(Nulls.FAIL, mergedSetterValue.getValueNulls());
        assertEquals(Nulls.FAIL, mergedSetterValue.getContentNulls());
    }
}
