package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Arrays;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test09 extends ArrayFill_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        final int arrayLength = 4;
        final int fillValue = 1;
        final int[] expectedFilledArray = new int[] { 1, 1, 1, 1 };

        final int[] arrayToFill = new int[arrayLength];
        final int[] filledArray = ArrayFill.fill(arrayToFill, fillValue);

        assertArrayEquals(expectedFilledArray, filledArray);
    }
}
