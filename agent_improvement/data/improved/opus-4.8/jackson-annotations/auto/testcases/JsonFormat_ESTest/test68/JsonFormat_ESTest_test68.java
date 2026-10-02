package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test68 extends JsonFormat_ESTest_scaffolding {

    /**
     * The static {@link JsonFormat.Shape#isNumeric(JsonFormat.Shape)} helper should
     * report {@code true} for NUMBER_INT, since it is one of the numeric shapes
     * (NUMBER, NUMBER_INT, NUMBER_FLOAT).
     */
    @Test(timeout = 4000)
    public void isNumeric_returnsTrue_forNumberIntShape() throws Throwable {
        boolean numberIntIsNumeric = JsonFormat.Shape.isNumeric(JsonFormat.Shape.NUMBER_INT);

        assertTrue(numberIntIsNumeric);
    }
}
