package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test67 extends JsonFormat_ESTest_scaffolding {

    /**
     * The static {@link JsonFormat.Shape#isNumeric(JsonFormat.Shape)} helper should
     * report NUMBER_FLOAT as a numeric shape.
     */
    @Test(timeout = 4000)
    public void isNumericReturnsTrueForNumberFloatShape() throws Throwable {
        boolean numberFloatIsNumeric = JsonFormat.Shape.isNumeric(JsonFormat.Shape.NUMBER_FLOAT);

        assertTrue(numberFloatIsNumeric);
    }
}
