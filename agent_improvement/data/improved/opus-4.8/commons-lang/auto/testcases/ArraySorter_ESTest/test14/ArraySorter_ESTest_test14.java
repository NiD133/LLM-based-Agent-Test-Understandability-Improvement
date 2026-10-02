package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test14 extends ArraySorter_ESTest_scaffolding {

    /**
     * Verifies that sorting a char array returns the very same array instance
     * (the sort is performed in place), as documented by {@link ArraySorter#sort(char[])}.
     */
    @Test(timeout = 4000)
    public void sortCharArrayReturnsSameInstance() throws Throwable {
        char[] inputArray = new char[6];

        char[] sortedArray = ArraySorter.sort(inputArray);

        assertSame(inputArray, sortedArray);
    }
}
