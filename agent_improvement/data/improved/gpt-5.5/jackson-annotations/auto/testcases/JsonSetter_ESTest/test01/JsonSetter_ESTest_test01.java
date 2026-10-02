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
        Nulls asEmptyNullHandling = Nulls.AS_EMPTY;
        JsonSetter.Value explicitAsEmptyValue = JsonSetter.Value.construct(asEmptyNullHandling, asEmptyNullHandling);

        JsonSetter annotationReturningNulls = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(annotationReturningNulls).contentNulls();
        doReturn((Nulls) null).when(annotationReturningNulls).nulls();

        JsonSetter.Value defaultValueFromAnnotation = JsonSetter.Value.from(annotationReturningNulls);
        boolean valuesAreEqual = explicitAsEmptyValue.equals(defaultValueFromAnnotation);

        assertEquals(Nulls.AS_EMPTY, explicitAsEmptyValue.getContentNulls());
        assertEquals(Nulls.AS_EMPTY, explicitAsEmptyValue.getValueNulls());
        assertFalse(valuesAreEqual);
    }
}
