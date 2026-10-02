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
public class ArraySorter_ESTest_test10 extends ArraySorter_ESTest_scaffolding {

    /**
     * ArraySorter.sort(float[]) must return the exact same array instance that
     * was passed in (in-place sort, fluent style).
     */
    @Test(timeout = 4000)
    public void test_sortFloatArray_returnsSameInstance() throws Throwable {
        float[] inputArray = new float[1];

        float[] sortedArray = ArraySorter.sort(inputArray);

        assertSame("sort(float[]) should return the original array reference", sortedArray, inputArray);
    }
}
