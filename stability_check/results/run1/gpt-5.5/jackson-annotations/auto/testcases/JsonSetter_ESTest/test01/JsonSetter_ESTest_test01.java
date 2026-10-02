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
public class JsonSetter_ESTest_test01 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Nulls explicitNullHandling = Nulls.AS_EMPTY;
        JsonSetter.Value explicitValue = JsonSetter.Value.construct(
                explicitNullHandling, explicitNullHandling);

        JsonSetter annotationWithDefaultedNullHandling = mock(JsonSetter.class, CALLS_REAL_METHODS);
        // JsonSetter.Value.from(...) converts null annotation settings to Nulls.DEFAULT.
        doReturn((Nulls) null).when(annotationWithDefaultedNullHandling).contentNulls();
        doReturn((Nulls) null).when(annotationWithDefaultedNullHandling).nulls();

        JsonSetter.Value defaultedValue = JsonSetter.Value.from(annotationWithDefaultedNullHandling);
        boolean valuesAreEqual = explicitValue.equals(defaultedValue);

        assertEquals(Nulls.AS_EMPTY, explicitValue.getContentNulls());
        assertEquals(Nulls.AS_EMPTY, explicitValue.getValueNulls());
        assertFalse(valuesAreEqual);
    }
}
