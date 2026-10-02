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
public class JsonSetter_ESTest_test00 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Nulls failingNullHandling = Nulls.FAIL;
        JsonSetter.Value factoryValue = JsonSetter.Value.construct(failingNullHandling, failingNullHandling);
        JsonSetter.Value constructorValue = new JsonSetter.Value(failingNullHandling, failingNullHandling);

        boolean valuesAreEqual = constructorValue.equals(factoryValue);

        assertTrue(valuesAreEqual);
    }
}
