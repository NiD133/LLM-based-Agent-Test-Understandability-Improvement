package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test04 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that a JsonSetter.Value instance is not equal to an unrelated plain Object,
     * confirming type-safe equality in the equals() implementation.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        JsonSetter.Value valueWithDefaultContentNulls = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);
        Object plainObject = new Object();

        boolean isEqual = valueWithDefaultContentNulls.equals(plainObject);

        assertFalse(isEqual);
    }
}
