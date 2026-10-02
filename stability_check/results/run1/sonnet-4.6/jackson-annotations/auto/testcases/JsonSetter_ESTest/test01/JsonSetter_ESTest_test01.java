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
     * Verifies that two JsonSetter.Value instances are not equal when one is
     * constructed with AS_EMPTY null handling and the other is built from a
     * JsonSetter annotation mock that returns null (defaulting to DEFAULT) for
     * both nulls() and contentNulls().
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Construct a Value explicitly configured with AS_EMPTY for both null strategies
        Nulls asEmpty = Nulls.AS_EMPTY;
        JsonSetter.Value valueWithAsEmpty = JsonSetter.Value.construct(asEmpty, asEmpty);

        // Build a Value from a mock annotation whose nulls() and contentNulls() return null,
        // which causes construct() to substitute Nulls.DEFAULT for both fields
        JsonSetter mockAnnotation = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(mockAnnotation).contentNulls();
        doReturn((Nulls) null).when(mockAnnotation).nulls();
        JsonSetter.Value valueFromNullAnnotation = JsonSetter.Value.from(mockAnnotation);

        // The two Values differ (AS_EMPTY vs DEFAULT), so equals() must return false
        boolean areEqual = valueWithAsEmpty.equals(valueFromNullAnnotation);

        assertEquals(Nulls.AS_EMPTY, valueWithAsEmpty.getContentNulls());
        assertEquals(Nulls.AS_EMPTY, valueWithAsEmpty.getValueNulls());
        assertFalse(areEqual);
    }
}
