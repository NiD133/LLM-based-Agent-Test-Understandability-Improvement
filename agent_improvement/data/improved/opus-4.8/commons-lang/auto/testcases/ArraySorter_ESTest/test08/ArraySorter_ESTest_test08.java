package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test08 extends ArraySorter_ESTest_scaffolding {

    /**
     * Verifies that sorting an int[] returns the very same array instance that
     * was passed in (the sort happens in place and the original reference is
     * returned), as documented by {@link ArraySorter#sort(int[])}.
     */
    @Test(timeout = 4000)
    public void sortIntArrayReturnsSameArrayInstance() throws Throwable {
        int[] inputArray = new int[3];

        int[] sortedArray = ArraySorter.sort(inputArray);

        assertSame(inputArray, sortedArray);
    }
}
