package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test61 extends JsonFormat_ESTest_scaffolding {

    /**
     * The static helper {@link JsonFormat.Shape#isStructured(JsonFormat.Shape)}
     * is null-safe: passing {@code null} must return {@code false} rather than
     * throwing a {@link NullPointerException}.
     */
    @Test(timeout = 4000)
    public void isStructured_withNullShape_returnsFalse() throws Throwable {
        boolean structured = JsonFormat.Shape.isStructured((JsonFormat.Shape) null);

        assertFalse(structured);
    }
}
