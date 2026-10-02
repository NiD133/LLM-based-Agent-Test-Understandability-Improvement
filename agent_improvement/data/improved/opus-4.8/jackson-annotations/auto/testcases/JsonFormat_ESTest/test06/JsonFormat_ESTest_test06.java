package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test06 extends JsonFormat_ESTest_scaffolding {

    /**
     * A Value built only from a Shape and a Value built only from a leniency flag
     * differ in every relevant setting, so they must not be considered equal.
     * Also verifies the default characteristics of each Value.
     */
    @Test(timeout = 4000)
    public void shapeOnlyValueIsNotEqualToLeniencyOnlyValue() throws Throwable {
        JsonFormat.Value shapeOnlyValue = JsonFormat.Value.forShape(JsonFormat.Shape.ARRAY);
        JsonFormat.Value strictLeniencyValue = JsonFormat.Value.forLeniency(false);

        boolean valuesAreEqual = shapeOnlyValue.equals(strictLeniencyValue);
        assertFalse(valuesAreEqual);

        // The leniency-only value is explicitly strict, carries no shape and uses the default radix.
        assertFalse(strictLeniencyValue.isLenient());
        assertFalse(strictLeniencyValue.hasShape());
        assertEquals(-1, strictLeniencyValue.getRadix());

        // The shape-only value still uses the default radix.
        assertFalse(shapeOnlyValue.hasNonDefaultRadix());
    }
}
