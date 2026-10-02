package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test04 extends ArraySorter_ESTest_scaffolding {

    /**
     * ArraySorter.sort(short[]) sorts the array in place and returns the very
     * same array instance that was passed in (fluent style).
     */
    @Test(timeout = 4000)
    public void sortShortArrayReturnsSameInstance() throws Throwable {
        short[] inputArray = new short[4];

        short[] sortedArray = ArraySorter.sort(inputArray);

        assertSame(inputArray, sortedArray);
    }
}
