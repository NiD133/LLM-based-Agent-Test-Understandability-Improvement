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
public class JsonSetter_ESTest_test12 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that passing null for both valueNulls and contentNulls to
     * withValueNulls() normalizes them to Nulls.DEFAULT.
     * Starting from an empty Value (which already uses Nulls.DEFAULT for both),
     * the resulting Value should still report Nulls.DEFAULT as its valueNulls.
     */
    @Test(timeout = 4000)
    public void test_withValueNulls_nullArgumentsNormalizedToDefault() throws Throwable {
        JsonSetter.Value emptyValue = JsonSetter.Value.empty();
        JsonSetter.Value valueWithNullsNormalized = emptyValue.withValueNulls((Nulls) null, (Nulls) null);
        assertEquals(Nulls.DEFAULT, valueWithNullsNormalized.getValueNulls());
    }
}
