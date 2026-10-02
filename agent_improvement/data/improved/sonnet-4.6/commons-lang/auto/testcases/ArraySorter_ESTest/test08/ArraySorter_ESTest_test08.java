package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Comparator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test08 extends ArraySorter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08_sortIntArray_returnsSameArrayInstance() throws Throwable {
        // ArraySorter.sort(int[]) must sort in-place and return the exact same array reference
        int[] inputArray = new int[3];
        int[] sortedArray = ArraySorter.sort(inputArray);
        assertSame(inputArray, sortedArray);
    }
}
