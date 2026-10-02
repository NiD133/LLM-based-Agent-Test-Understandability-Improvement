package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test15 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Applying a null set of overrides to the "no overrides" Value must leave it
     * unchanged, so the field visibility stays at the default DEFAULT level.
     */
    @Test(timeout = 4000)
    public void withNullOverridesKeepsDefaultFieldVisibility() throws Throwable {
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.noOverrides();

        JsonAutoDetect.Value result = noOverrides.withOverrides((JsonAutoDetect.Value) null);

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, result.getFieldVisibility());
    }
}
