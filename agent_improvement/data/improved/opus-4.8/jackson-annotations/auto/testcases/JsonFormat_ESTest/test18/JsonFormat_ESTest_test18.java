package com.fasterxml.jackson.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test18 extends JsonFormat_ESTest_scaffolding {

    /**
     * A default-constructed {@link JsonFormat.Value} should carry no locale and
     * should report the default radix (-1).
     */
    @Test(timeout = 4000)
    public void defaultValueHasNoLocaleAndDefaultRadix() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        assertFalse("a default Value should not have a locale", defaultValue.hasLocale());
        assertEquals("a default Value should report the default radix",
                JsonFormat.DEFAULT_RADIX, defaultValue.getRadix());
    }
}
