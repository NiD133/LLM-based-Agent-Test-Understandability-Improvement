package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test34 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies how {@link JsonFormat.Value#withOverrides} behaves when the base value
     * is the shared EMPTY singleton: it short-circuits and returns the override
     * instance itself. Since the override is a freshly built default Value, it is also
     * content-equal to EMPTY.
     */
    @Test(timeout = 4000)
    public void withOverridesOnEmptyBaseReturnsOverrideInstance() throws Throwable {
        JsonFormat.Value emptyBase = JsonFormat.Value.empty();
        JsonFormat.Value defaultOverride = new JsonFormat.Value();

        JsonFormat.Value merged = emptyBase.withOverrides(defaultOverride);

        // EMPTY base means the override is returned unchanged (same reference).
        assertSame(defaultOverride, merged);
        // A default Value still carries the same content as the EMPTY singleton.
        assertTrue(merged.equals(emptyBase));
    }
}
