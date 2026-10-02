package com.fasterxml.jackson.annotation;

import static org.junit.Assert.assertFalse;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test69 extends JsonFormat_ESTest_scaffolding {

    /**
     * The SCALAR shape indicates a non-structural value but does not commit to a
     * numeric representation, so {@link Shape#isNumeric(Shape)} must report it as
     * non-numeric.
     */
    @Test(timeout = 4000)
    public void isNumeric_returnsFalse_forScalarShape() throws Throwable {
        boolean scalarIsNumeric = JsonFormat.Shape.isNumeric(JsonFormat.Shape.SCALAR);

        assertFalse(scalarIsNumeric);
    }
}
