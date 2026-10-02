package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test27 extends JsonFormat_ESTest_scaffolding {

    /**
     * A freshly created Value already uses the default radix
     * ({@link JsonFormat#DEFAULT_RADIX}, which is -1). Calling withRadix with
     * that same value is a no-op, so it must return the very same instance
     * rather than allocating a new one.
     */
    @Test(timeout = 4000)
    public void withRadix_defaultValue_returnsSameInstance() throws Throwable {
        JsonFormat.Value defaultFormat = new JsonFormat.Value();

        JsonFormat.Value result = defaultFormat.withRadix(JsonFormat.DEFAULT_RADIX);

        assertSame(defaultFormat, result);
    }
}
