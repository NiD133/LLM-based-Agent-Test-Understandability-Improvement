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
public class JsonIgnoreProperties_ESTest_test37 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test37() throws Throwable {
        JsonIgnoreProperties annotation = mock(JsonIgnoreProperties.class, CALLS_REAL_METHODS);
        doReturn(false).when(annotation).allowGetters();
        doReturn(false).when(annotation).allowSetters();
        doReturn(false).when(annotation).ignoreUnknown();
        doReturn((String[]) null).when(annotation).value();

        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.from(annotation);
        Object unrelatedObject = new Object();
        boolean equalsUnrelatedObject = value.equals(unrelatedObject);

        assertFalse(equalsUnrelatedObject);
        assertFalse(value.getMerge());
        assertFalse(value.getIgnoreUnknown());
        assertFalse(value.getAllowGetters());
        assertFalse(value.getAllowSetters());
    }
}
