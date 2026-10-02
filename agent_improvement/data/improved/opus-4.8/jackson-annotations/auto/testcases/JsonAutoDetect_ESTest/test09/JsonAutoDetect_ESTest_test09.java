package com.fasterxml.jackson.annotation;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test09 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonAutoDetect.Value#equals(Object)} is reflexive:
     * a Value instance must be considered equal to itself.
     */
    @Test(timeout = 4000)
    public void valueEqualsItself() throws Throwable {
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.NO_OVERRIDES;

        boolean isEqualToItself = noOverrides.equals(noOverrides);

        assertTrue("A Value should be equal to itself", isEqualToItself);
    }
}
