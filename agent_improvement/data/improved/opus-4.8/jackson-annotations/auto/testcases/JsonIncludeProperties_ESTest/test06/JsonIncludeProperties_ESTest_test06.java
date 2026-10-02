package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test06 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonIncludeProperties.Value#equals(Object)} is reflexive:
     * the default "include all" value must be equal to itself.
     */
    @Test(timeout = 4000)
    public void equals_isReflexive_forAllValue() throws Throwable {
        JsonIncludeProperties.Value allValue = JsonIncludeProperties.Value.ALL;

        boolean equalToItself = allValue.equals(allValue);

        assertTrue("Value should be equal to itself", equalToItself);
    }
}
