package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test74 extends JsonFormat_ESTest_scaffolding {

    /**
     * An empty JsonFormat.Value defaults to Shape.ANY, which is a logical
     * "no preference" marker rather than a concrete numeric shape, so
     * isNumeric() should report false.
     */
    @Test(timeout = 4000)
    public void emptyValueHasNonNumericShape() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();

        JsonFormat.Shape defaultShape = emptyValue.getShape();

        assertFalse(defaultShape.isNumeric());
    }
}
