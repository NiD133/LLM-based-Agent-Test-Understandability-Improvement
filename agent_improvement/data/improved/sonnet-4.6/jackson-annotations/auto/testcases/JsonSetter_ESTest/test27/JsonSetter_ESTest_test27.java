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
public class JsonSetter_ESTest_test27 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that creating a Value via forContentNulls() leaves the value-nulls
     * field at its DEFAULT, i.e. getValueNulls() returns Nulls.DEFAULT.
     */
    @Test(timeout = 4000)
    public void test_forContentNulls_doesNotAffectValueNulls() throws Throwable {
        Nulls contentNullsHandling = Nulls.DEFAULT;
        JsonSetter.Value setterValue = JsonSetter.Value.forContentNulls(contentNullsHandling);
        Nulls valueNullsHandling = setterValue.getValueNulls();
        assertEquals(Nulls.DEFAULT, valueNullsHandling);
    }
}
