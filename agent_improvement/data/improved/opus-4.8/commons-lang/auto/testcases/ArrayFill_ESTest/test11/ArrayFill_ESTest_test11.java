package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test11 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that filling a float array with 0.0F leaves every element at 0.0F
     * and that the same array instance is returned.
     */
    @Test(timeout = 4000)
    public void fillFloatArrayWithZero_setsAllElementsToZero() throws Throwable {
        float[] arrayToFill = new float[1];
        float fillValue = 0.0F;

        float[] filledArray = ArrayFill.fill(arrayToFill, fillValue);

        float delta = 0.01F;
        assertArrayEquals(new float[] { 0.0F }, filledArray, delta);
    }
}
