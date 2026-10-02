package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test07 extends ArraySorter_ESTest_scaffolding {

    /**
     * Sorting a {@code null} long[] should be a no-op that simply returns
     * {@code null}, as documented by {@link ArraySorter#sort(long[])}.
     */
    @Test(timeout = 4000)
    public void sortNullLongArrayReturnsNull() throws Throwable {
        long[] nullLongArray = null;

        long[] sortedResult = ArraySorter.sort(nullLongArray);

        assertNull(sortedResult);
    }
}
