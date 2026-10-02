package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test79 extends JsonFormat_ESTest_scaffolding {

    /**
     * A default-constructed {@link JsonFormat.Value} should report a non-lenient,
     * default-radix configuration: leniency is unset (so {@code isLenient()} is
     * {@code false}) and no custom radix was specified.
     */
    @Test(timeout = 4000)
    public void defaultValueIsNotLenientAndUsesDefaultRadix() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        assertFalse("default Value should not be lenient", defaultValue.isLenient());
        assertFalse("default Value should use the default radix",
                defaultValue.hasNonDefaultRadix());
    }
}
