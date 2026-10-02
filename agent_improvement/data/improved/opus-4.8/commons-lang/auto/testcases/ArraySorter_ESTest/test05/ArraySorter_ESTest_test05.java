package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test05 extends ArraySorter_ESTest_scaffolding {

    /**
     * Sorting a null short[] should return null rather than throwing,
     * because ArraySorter.sort() leaves null inputs untouched.
     */
    @Test(timeout = 4000)
    public void sortNullShortArrayReturnsNull() throws Throwable {
        short[] nullShortArray = null;

        short[] sortedResult = ArraySorter.sort(nullShortArray);

        assertNull(sortedResult);
    }
}
