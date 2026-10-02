package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test03 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonSetter.Value#equals(Object)} is reflexive:
     * a Value instance must be equal to itself.
     */
    @Test(timeout = 4000)
    public void equalsIsReflexive() throws Throwable {
        JsonSetter.Value value = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);

        assertTrue(value.equals(value));
    }
}
