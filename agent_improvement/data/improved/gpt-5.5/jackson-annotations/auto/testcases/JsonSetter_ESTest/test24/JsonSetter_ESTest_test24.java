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
public class JsonSetter_ESTest_test24 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        JsonSetter annotation = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(annotation).contentNulls();
        doReturn((Nulls) null).when(annotation).nulls();

        JsonSetter.Value value = JsonSetter.Value.from(annotation);
        Nulls contentNulls = value.getContentNulls();

        assertEquals("Null content handling should default when the annotation returns null",
                Nulls.DEFAULT, contentNulls);
    }
}
