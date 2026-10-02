package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test30 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonFormat.Value#withLenient(Boolean)} produces a new
     * value whose leniency is enabled, while leaving the radix at its default
     * (so {@code hasNonDefaultRadix()} stays false on both the original and the copy).
     */
    @Test(timeout = 4000)
    public void withLenientTrue_enablesLeniencyAndKeepsDefaultRadix() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        JsonFormat.Value lenientValue = defaultValue.withLenient(Boolean.TRUE);

        assertTrue("withLenient(TRUE) should enable leniency", lenientValue.isLenient());
        assertFalse("radix should remain at its default on the lenient copy",
                lenientValue.hasNonDefaultRadix());
        assertFalse("radix should remain at its default on the original value",
                defaultValue.hasNonDefaultRadix());
    }
}
