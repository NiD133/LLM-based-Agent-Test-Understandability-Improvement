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
public class JsonSetter_ESTest_test22 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Nulls failOnNullInput = Nulls.FAIL;
        JsonSetter.Value failForValueAndContentNulls =
                JsonSetter.Value.construct(failOnNullInput, failOnNullInput);

        String actualDescription = failForValueAndContentNulls.toString();

        assertEquals("JsonSetter.Value(valueNulls=FAIL,contentNulls=FAIL)", actualDescription);
    }
}
