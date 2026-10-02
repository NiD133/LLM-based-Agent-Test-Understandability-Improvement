package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test06 extends ArraySorter_ESTest_scaffolding {

    /**
     * Verifies that {@link ArraySorter#sort(long[])} sorts the array in place
     * and returns the very same array instance that was passed in.
     */
    @Test(timeout = 4000)
    public void sortLongArrayReturnsSameInstance() throws Throwable {
        long[] inputArray = new long[2];

        long[] sortedArray = ArraySorter.sort(inputArray);

        assertSame(inputArray, sortedArray);
    }
}
