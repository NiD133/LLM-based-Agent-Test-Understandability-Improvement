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
public class ArraySorter_ESTest_test04 extends ArraySorter_ESTest_scaffolding {

    /**
     * Verifies that sorting a short array returns the same array instance,
     * not a new copy — i.e. the sort is performed in-place.
     */
    @Test(timeout = 4000)
    public void test_sortShortArray_returnsSameArrayInstance() throws Throwable {
        short[] inputArray = new short[4];
        short[] sortedArray = ArraySorter.sort(inputArray);
        assertSame("sort(short[]) must return the original array, not a copy", inputArray, sortedArray);
    }
}
