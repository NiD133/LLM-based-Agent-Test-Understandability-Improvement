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
public class JsonSetter_ESTest_test03 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that JsonSetter.Value.equals() is reflexive:
     * a value instance must equal itself.
     */
    @Test(timeout = 4000)
    public void test_equalsIsReflexive_valueCreatedWithDefaultContentNulls() throws Throwable {
        JsonSetter.Value valueWithDefaultContentNulls = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);
        boolean isEqualToItself = valueWithDefaultContentNulls.equals(valueWithDefaultContentNulls);
        assertTrue(isEqualToItself);
    }
}
