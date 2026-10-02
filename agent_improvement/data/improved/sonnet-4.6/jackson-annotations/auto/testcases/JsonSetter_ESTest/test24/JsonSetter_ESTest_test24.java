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

    // When a JsonSetter annotation returns null for both nulls() and contentNulls(),
    // Value.from() should normalize those nulls to Nulls.DEFAULT.
    @Test(timeout = 4000)
    public void test24() throws Throwable {
        JsonSetter annotationWithNullHandlingValues = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(annotationWithNullHandlingValues).contentNulls();
        doReturn((Nulls) null).when(annotationWithNullHandlingValues).nulls();

        JsonSetter.Value valueFromAnnotation = JsonSetter.Value.from(annotationWithNullHandlingValues);

        Nulls contentNulls = valueFromAnnotation.getContentNulls();
        assertEquals(Nulls.DEFAULT, contentNulls);
    }
}
