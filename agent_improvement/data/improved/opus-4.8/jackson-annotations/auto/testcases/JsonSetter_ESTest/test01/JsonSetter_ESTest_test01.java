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
     * Two JsonSetter.Value instances are unequal when one carries explicit
     * AS_EMPTY null-handling and the other resolves to the empty (DEFAULT) value.
     *
     * A Value built from a JsonSetter whose nulls()/contentNulls() both return
     * null is normalised by Value.from(...) to the EMPTY instance (Nulls.DEFAULT
     * for both fields), which does not equal a Value explicitly set to AS_EMPTY.
     */
    @Test(timeout = 4000)
    public void valueWithAsEmptyNullsIsNotEqualToEmptyValue() throws Throwable {
        // Value with both value-nulls and content-nulls set to AS_EMPTY.
        JsonSetter.Value asEmptyValue = JsonSetter.Value.construct(Nulls.AS_EMPTY, Nulls.AS_EMPTY);

        // A JsonSetter annotation whose null-handling accessors both return null;
        // Value.from() will normalise these nulls to Nulls.DEFAULT, yielding EMPTY.
        JsonSetter jsonSetterWithNullNulls = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(jsonSetterWithNullNulls).contentNulls();
        doReturn((Nulls) null).when(jsonSetterWithNullNulls).nulls();
        JsonSetter.Value emptyValue = JsonSetter.Value.from(jsonSetterWithNullNulls);

        boolean valuesAreEqual = asEmptyValue.equals(emptyValue);

        // The explicitly-constructed value retains its AS_EMPTY settings...
        assertEquals(Nulls.AS_EMPTY, asEmptyValue.getContentNulls());
        assertEquals(Nulls.AS_EMPTY, asEmptyValue.getValueNulls());
        // ...so it differs from the normalised (DEFAULT/DEFAULT) empty value.
        assertFalse(valuesAreEqual);
    }
}
