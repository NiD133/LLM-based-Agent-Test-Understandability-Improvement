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
     * Verifies that a Value constructed with explicit AS_EMPTY null-handling is NOT equal
     * to a Value derived from a JsonSetter annotation that returns null for both nulls()
     * and contentNulls() (which maps to DEFAULT null-handling internally).
     */
    @Test(timeout = 4000)
    public void test01_valueWithAsEmptyNullsIsNotEqualToValueDerivedFromAnnotationWithNullReturns() throws Throwable {
        // Build a Value where both value-nulls and content-nulls are AS_EMPTY
        Nulls asEmptyStrategy = Nulls.AS_EMPTY;
        JsonSetter.Value valueWithAsEmpty = JsonSetter.Value.construct(asEmptyStrategy, asEmptyStrategy);

        // Mock a JsonSetter annotation whose nulls() and contentNulls() both return null;
        // Value.from() treats null returns as DEFAULT, so this produces a DEFAULT-based Value
        JsonSetter mockAnnotationWithNullReturns = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(mockAnnotationWithNullReturns).contentNulls();
        doReturn((Nulls) null).when(mockAnnotationWithNullReturns).nulls();
        JsonSetter.Value valueFromNullReturningAnnotation = JsonSetter.Value.from(mockAnnotationWithNullReturns);

        // AS_EMPTY values should not equal DEFAULT values
        boolean valuesAreEqual = valueWithAsEmpty.equals(valueFromNullReturningAnnotation);
        assertFalse(valuesAreEqual);

        // The original AS_EMPTY value should retain its configured null strategies
        assertEquals(Nulls.AS_EMPTY, valueWithAsEmpty.getContentNulls());
        assertEquals(Nulls.AS_EMPTY, valueWithAsEmpty.getValueNulls());
    }
}
