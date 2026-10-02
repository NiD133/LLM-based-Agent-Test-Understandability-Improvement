package org.apache.commons.lang3;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test13 extends ArraySorter_ESTest_scaffolding {

    /**
     * Sorting a {@code null} double array should return {@code null}
     * rather than throwing, as documented by {@link ArraySorter#sort(double[])}.
     */
    @Test(timeout = 4000)
    public void sortNullDoubleArrayReturnsNull() throws Throwable {
        final double[] nullDoubleArray = null;

        final double[] sortedResult = ArraySorter.sort(nullDoubleArray);

        assertNull(sortedResult);
    }
}
