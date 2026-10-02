package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test66 extends JsonFormat_ESTest_scaffolding {

    /**
     * The static {@link JsonFormat.Shape#isNumeric(JsonFormat.Shape)} helper is
     * null-safe: a {@code null} shape is treated as "not numeric".
     */
    @Test(timeout = 4000)
    public void isNumeric_givenNullShape_returnsFalse() throws Throwable {
        boolean numericForNull = JsonFormat.Shape.isNumeric((JsonFormat.Shape) null);

        assertFalse(numericForNull);
    }
}
