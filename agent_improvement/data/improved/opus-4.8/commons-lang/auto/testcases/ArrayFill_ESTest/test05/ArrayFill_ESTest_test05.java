package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test05 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that {@link ArrayFill#fill(short[], short)} assigns the given
     * value to every element of the array and returns that same array.
     */
    @Test(timeout = 4000)
    public void fillShortArray_setsEveryElementToValue() throws Throwable {
        final short fillValue = (short) 2260;
        short[] arrayToFill = new short[2];

        short[] filledArray = ArrayFill.fill(arrayToFill, fillValue);

        assertArrayEquals(new short[] { fillValue, fillValue }, filledArray);
    }
}
