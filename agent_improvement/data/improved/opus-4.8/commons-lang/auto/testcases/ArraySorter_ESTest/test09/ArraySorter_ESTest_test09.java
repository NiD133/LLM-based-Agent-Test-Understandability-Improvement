package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test09 extends ArraySorter_ESTest_scaffolding {

    /**
     * Sorting a {@code null} int array should be a no-op that simply returns
     * {@code null}, rather than throwing a {@link NullPointerException}.
     */
    @Test(timeout = 4000)
    public void sortNullIntArrayReturnsNull() throws Throwable {
        int[] sortedArray = ArraySorter.sort((int[]) null);

        assertNull(sortedArray);
    }
}
