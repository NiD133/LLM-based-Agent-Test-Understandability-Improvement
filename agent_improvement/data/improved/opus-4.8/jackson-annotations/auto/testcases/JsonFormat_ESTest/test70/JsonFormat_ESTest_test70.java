package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test70 extends JsonFormat_ESTest_scaffolding {

    /**
     * The static {@code Shape.isNumeric(Shape)} helper should report {@code true}
     * for the {@link JsonFormat.Shape#NUMBER} shape, which is one of the numeric shapes.
     */
    @Test(timeout = 4000)
    public void numberShapeIsReportedAsNumeric() throws Throwable {
        boolean numberIsNumeric = JsonFormat.Shape.isNumeric(JsonFormat.Shape.NUMBER);

        assertTrue("NUMBER shape should be recognized as numeric", numberIsNumeric);
    }
}
