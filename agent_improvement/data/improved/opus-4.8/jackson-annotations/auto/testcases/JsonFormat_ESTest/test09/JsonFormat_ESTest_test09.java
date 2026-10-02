package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test09 extends JsonFormat_ESTest_scaffolding {

    /**
     * A default-constructed {@link JsonFormat.Value} should be equal to itself
     * (reflexivity) and should report the default radix (-1, meaning "no custom
     * radix specified").
     */
    @Test(timeout = 4000)
    public void defaultValueEqualsItselfAndUsesDefaultRadix() throws Throwable {
        JsonFormat.Value defaultFormat = new JsonFormat.Value();

        assertTrue("A Value must be equal to itself", defaultFormat.equals(defaultFormat));
        assertEquals("Default radix should be -1 (DEFAULT_RADIX)", -1, defaultFormat.getRadix());
    }
}
