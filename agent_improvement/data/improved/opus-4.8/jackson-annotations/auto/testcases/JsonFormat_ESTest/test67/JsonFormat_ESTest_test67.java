package com.fasterxml.jackson.annotation;

import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test67 extends JsonFormat_ESTest_scaffolding {

    /**
     * The static {@link Shape#isNumeric(Shape)} helper should report {@code true}
     * for floating-point shapes such as {@link Shape#NUMBER_FLOAT}.
     */
    @Test(timeout = 4000)
    public void isNumericReturnsTrueForNumberFloatShape() throws Throwable {
        boolean numeric = Shape.isNumeric(Shape.NUMBER_FLOAT);

        assertTrue("NUMBER_FLOAT is expected to be recognized as a numeric shape", numeric);
    }
}
