package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test12 extends ArraySorter_ESTest_scaffolding {

    /**
     * Verifies that sorting a {@code double[]} sorts in place and returns the
     * same array instance that was passed in (fluent style).
     */
    @Test(timeout = 4000)
    public void sortDoubleArrayReturnsSameInstance() throws Throwable {
        double[] inputArray = new double[4];

        double[] sortedArray = ArraySorter.sort(inputArray);

        assertSame(inputArray, sortedArray);
    }
}
