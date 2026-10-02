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

    /**
     * Verifies that two JsonSetter.Value instances with different Nulls settings are not equal:
     * one constructed explicitly with AS_EMPTY for both nulls fields, the other built from a
     * mocked JsonSetter whose nulls() and contentNulls() return null (which defaults to DEFAULT).
     */
    @Test(timeout = 4000)
    public void test01_valuesWithDifferentNullsSettingsAreNotEqual() throws Throwable {
        // Arrange: build a Value with AS_EMPTY for both value-nulls and content-nulls
        Nulls asEmpty = Nulls.AS_EMPTY;
        JsonSetter.Value valueWithAsEmpty = JsonSetter.Value.construct(asEmpty, asEmpty);

        // Arrange: build a Value from a mock annotation whose nulls methods return null,
        // so construct() will coerce both fields to DEFAULT
        JsonSetter mockAnnotation = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(mockAnnotation).contentNulls();
        doReturn((Nulls) null).when(mockAnnotation).nulls();
        JsonSetter.Value valueWithDefaults = JsonSetter.Value.from(mockAnnotation);

        // Act: compare the two Value instances
        boolean areEqual = valueWithAsEmpty.equals(valueWithDefaults);

        // Assert: AS_EMPTY settings are preserved in the first value, and they differ from DEFAULT
        assertEquals(Nulls.AS_EMPTY, valueWithAsEmpty.getContentNulls());
        assertEquals(Nulls.AS_EMPTY, valueWithAsEmpty.getValueNulls());
        assertFalse(areEqual);
    }
}
