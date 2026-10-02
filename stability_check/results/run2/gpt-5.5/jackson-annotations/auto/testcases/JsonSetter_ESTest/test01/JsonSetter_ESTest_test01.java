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
        JsonSetter.Value valueWithExplicitNullHandling = JsonSetter.Value.construct(
                explicitNullHandling,
                explicitNullHandling);

        JsonSetter annotationWithNullSettings = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(annotationWithNullSettings).contentNulls();
        doReturn((Nulls) null).when(annotationWithNullSettings).nulls();

        // Null annotation settings are normalized to DEFAULT, so they differ from AS_EMPTY.
        JsonSetter.Value valueFromNullAnnotationSettings = JsonSetter.Value.from(annotationWithNullSettings);
        boolean valuesAreEqual = valueWithExplicitNullHandling.equals(valueFromNullAnnotationSettings);

        assertEquals(Nulls.AS_EMPTY, valueWithExplicitNullHandling.getContentNulls());
        assertEquals(Nulls.AS_EMPTY, valueWithExplicitNullHandling.getValueNulls());
        assertFalse(valuesAreEqual);
    }
}
