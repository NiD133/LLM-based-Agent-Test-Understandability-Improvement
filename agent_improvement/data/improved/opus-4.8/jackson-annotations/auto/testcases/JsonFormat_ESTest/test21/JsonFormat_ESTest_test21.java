package com.fasterxml.jackson.annotation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test21 extends JsonFormat_ESTest_scaffolding {

    /**
     * A Value built from a concrete shape (BINARY) reports that it has a shape,
     * while leaving the radix untouched (still the default).
     */
    @Test(timeout = 4000)
    public void valueForBinaryShapeHasShapeButDefaultRadix() throws Throwable {
        Value binaryShapeValue = Value.forShape(Shape.BINARY);

        assertTrue("BINARY is a concrete shape, so hasShape() should be true",
                binaryShapeValue.hasShape());
        assertFalse("forShape() does not set a radix, so it stays at the default",
                binaryShapeValue.hasNonDefaultRadix());
    }
}
