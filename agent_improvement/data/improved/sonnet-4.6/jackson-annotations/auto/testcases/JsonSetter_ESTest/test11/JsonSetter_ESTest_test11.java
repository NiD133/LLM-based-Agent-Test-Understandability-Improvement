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
public class JsonSetter_ESTest_test11 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that withValueNulls(Nulls, Nulls) sets both value-nulls and content-nulls
     * when the source annotation returns null for both fields (which normalises to DEFAULT
     * inside Value.from), and the override uses AS_EMPTY for both positions.
     */
    @Test(timeout = 4000)
    public void test_withValueNulls_setsBothNullStrategiesToAsEmpty() throws Throwable {
        // Build a mock JsonSetter whose nulls()/contentNulls() both return null;
        // Value.from() normalises null → Nulls.DEFAULT, producing the EMPTY instance.
        JsonSetter mockAnnotation = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(mockAnnotation).contentNulls();
        doReturn((Nulls) null).when(mockAnnotation).nulls();
        JsonSetter.Value baseValue = JsonSetter.Value.from(mockAnnotation);

        // Override both null-handling strategies to AS_EMPTY.
        Nulls asEmpty = Nulls.AS_EMPTY;
        JsonSetter.Value updatedValue = baseValue.withValueNulls(asEmpty, asEmpty);

        assertEquals(Nulls.AS_EMPTY, updatedValue.getValueNulls());
        assertEquals(Nulls.AS_EMPTY, updatedValue.getContentNulls());
    }
}
