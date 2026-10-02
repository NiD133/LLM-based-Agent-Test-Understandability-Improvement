package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test09 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonTypeInfo.Value#equals(Object)} is reflexive:
     * a Value instance must be equal to itself.
     */
    @Test(timeout = 4000)
    public void valueEqualsItself() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        boolean isEqualToItself = emptyValue.equals(emptyValue);

        assertTrue("A Value instance should be equal to itself", isEqualToItself);
    }
}
