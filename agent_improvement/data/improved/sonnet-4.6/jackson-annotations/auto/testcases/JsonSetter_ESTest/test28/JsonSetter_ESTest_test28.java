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
public class JsonSetter_ESTest_test28 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that a Value created via forContentNulls(AS_EMPTY) differs from Value.EMPTY.
     *
     * forContentNulls sets only contentNulls=AS_EMPTY while leaving valueNulls=DEFAULT.
     * Value.EMPTY has both valueNulls=DEFAULT and contentNulls=DEFAULT.
     * The two values share the same valueNulls but differ in contentNulls, so they must not be equal.
     */
    @Test(timeout = 4000)
    public void test28_forContentNullsValue_hasDefaultValueNulls_andDiffersFromEmpty() throws Throwable {
        // Create a Value where only contentNulls is overridden; valueNulls stays DEFAULT
        JsonSetter.Value contentNullsValue = JsonSetter.Value.forContentNulls(Nulls.AS_EMPTY);

        // valueNulls must remain DEFAULT because forContentNulls does not touch it
        assertEquals(Nulls.DEFAULT, contentNullsValue.getValueNulls());

        // Value.EMPTY has contentNulls=DEFAULT, so it must differ from contentNullsValue (which has contentNulls=AS_EMPTY)
        boolean isEqualToEmpty = JsonSetter.Value.EMPTY.equals(contentNullsValue);
        assertFalse(isEqualToEmpty);
    }
}
