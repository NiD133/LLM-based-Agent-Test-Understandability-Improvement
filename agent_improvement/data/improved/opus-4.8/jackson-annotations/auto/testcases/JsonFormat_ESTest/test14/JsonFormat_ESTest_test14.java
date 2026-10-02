package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test14 extends JsonFormat_ESTest_scaffolding {

    /**
     * A default-constructed {@link JsonFormat.Value} should carry no explicit
     * settings: leniency is unset and the radix falls back to the documented
     * default ({@link JsonFormat#DEFAULT_RADIX}, which is -1).
     */
    @Test(timeout = 4000)
    public void defaultValueHasNoLeniencyAndDefaultRadix() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        assertFalse("default Value should not have leniency set", defaultValue.hasLenient());
        assertEquals("default Value should use the default radix",
                JsonFormat.DEFAULT_RADIX, defaultValue.getRadix());
    }
}
