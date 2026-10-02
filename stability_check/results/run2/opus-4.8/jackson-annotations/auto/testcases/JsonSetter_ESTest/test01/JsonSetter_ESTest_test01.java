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
     * Two JsonSetter.Value instances should not be considered equal when their
     * null-handling settings differ.
     *
     * <p>The first value is built explicitly with AS_EMPTY for both the value and
     * content null handling. The second is derived from a JsonSetter whose nulls()
     * and contentNulls() both return null; Value.from() normalises those nulls to
     * Nulls.DEFAULT, so the two values carry different settings and are not equal.
     */
    @Test(timeout = 4000)
    public void valuesWithDifferentNullHandlingAreNotEqual() throws Throwable {
        // Value with explicit AS_EMPTY settings for both value and content nulls.
        JsonSetter.Value asEmptyValue = JsonSetter.Value.construct(Nulls.AS_EMPTY, Nulls.AS_EMPTY);

        // A JsonSetter whose null-handling accessors both report null.
        JsonSetter setterWithNullSettings = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(setterWithNullSettings).contentNulls();
        doReturn((Nulls) null).when(setterWithNullSettings).nulls();

        // from() maps the null settings to Nulls.DEFAULT, yielding a different value.
        JsonSetter.Value defaultValue = JsonSetter.Value.from(setterWithNullSettings);

        boolean areEqual = asEmptyValue.equals(defaultValue);

        assertEquals(Nulls.AS_EMPTY, asEmptyValue.getContentNulls());
        assertEquals(Nulls.AS_EMPTY, asEmptyValue.getValueNulls());
        assertFalse(areEqual);
    }
}
