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
public class JsonSetter_ESTest_test04 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Nulls defaultContentNullHandling = Nulls.DEFAULT;
        JsonSetter.Value setterValue = JsonSetter.Value.forContentNulls(defaultContentNullHandling);
        Object unrelatedObject = new Object();

        boolean equalsUnrelatedObject = setterValue.equals(unrelatedObject);

        assertFalse(equalsUnrelatedObject);
    }
}
