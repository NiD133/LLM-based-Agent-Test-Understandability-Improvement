package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test16 extends ArraySorter_ESTest_scaffolding {

    /**
     * Sorting a byte array that already contains only zeros leaves every
     * element as zero, and the same (now sorted) array is returned.
     */
    @Test(timeout = 4000)
    public void sortAllZeroByteArrayKeepsAllZeros() throws Throwable {
        byte[] allZeros = new byte[5];

        byte[] sorted = ArraySorter.sort(allZeros);

        assertArrayEquals(new byte[] { 0, 0, 0, 0, 0 }, sorted);
    }
}
