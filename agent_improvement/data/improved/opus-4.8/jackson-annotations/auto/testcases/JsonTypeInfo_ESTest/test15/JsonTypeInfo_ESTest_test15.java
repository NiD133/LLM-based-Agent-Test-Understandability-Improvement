package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test15 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonTypeInfo.Value#isEnabled(JsonTypeInfo.Value)}
     * treats a {@code null} value as "polymorphic handling disabled" and
     * returns {@code false} instead of throwing a {@link NullPointerException}.
     */
    @Test(timeout = 4000)
    public void isEnabled_returnsFalse_whenValueIsNull() throws Throwable {
        boolean enabled = JsonTypeInfo.Value.isEnabled((JsonTypeInfo.Value) null);

        assertFalse("A null Value must be reported as not enabled", enabled);
    }
}
