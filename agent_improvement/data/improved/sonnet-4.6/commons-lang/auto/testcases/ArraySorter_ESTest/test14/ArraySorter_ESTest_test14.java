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
public class ArraySorter_ESTest_test14 extends ArraySorter_ESTest_scaffolding {

    /**
     * Verifies that ArraySorter.sort(char[]) returns the same array instance
     * it was given, not a new copy — the sort is performed in-place.
     */
    @Test(timeout = 4000)
    public void test_sortCharArray_returnsSameArrayInstance() throws Throwable {
        char[] inputArray = new char[6];

        char[] sortedArray = ArraySorter.sort(inputArray);

        assertSame("sort(char[]) must return the original array reference, not a copy",
                inputArray, sortedArray);
    }
}
