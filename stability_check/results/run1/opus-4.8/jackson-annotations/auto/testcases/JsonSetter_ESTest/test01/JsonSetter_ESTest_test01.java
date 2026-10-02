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
     * Two JsonSetter.Value instances are equal only when both their value-nulls
     * and content-nulls match. Here we compare:
     *   - a Value explicitly built with AS_EMPTY for both settings, against
     *   - a Value derived from a JsonSetter whose nulls()/contentNulls() are null;
     *     null settings collapse to Nulls.DEFAULT (the "empty" Value).
     * Since AS_EMPTY differs from DEFAULT, the two Values must not be equal.
     */
    @Test(timeout = 4000)
    public void equalsIsFalseWhenNullSettingsDifferFromAsEmpty() throws Throwable {
        // Value with AS_EMPTY for both value-nulls and content-nulls.
        JsonSetter.Value asEmptyValue = JsonSetter.Value.construct(Nulls.AS_EMPTY, Nulls.AS_EMPTY);

        // JsonSetter that reports null for both nulls() and contentNulls();
        // Value.from(...) turns those nulls into Nulls.DEFAULT.
        JsonSetter setterWithNullSettings = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(setterWithNullSettings).contentNulls();
        doReturn((Nulls) null).when(setterWithNullSettings).nulls();
        JsonSetter.Value defaultValue = JsonSetter.Value.from(setterWithNullSettings);

        boolean valuesAreEqual = asEmptyValue.equals(defaultValue);

        assertEquals(Nulls.AS_EMPTY, asEmptyValue.getContentNulls());
        assertEquals(Nulls.AS_EMPTY, asEmptyValue.getValueNulls());
        assertFalse(valuesAreEqual);
    }
}
