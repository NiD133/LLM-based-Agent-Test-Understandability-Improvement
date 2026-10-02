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
public class ArraySorter_ESTest_test12 extends ArraySorter_ESTest_scaffolding {

    /**
     * Verifies that ArraySorter.sort(double[]) returns the exact same array
     * instance it was given (i.e. sorts in-place and returns the input reference).
     */
    @Test(timeout = 4000)
    public void test_sortDoubleArray_returnsSameInstance() throws Throwable {
        double[] inputArray = new double[4];
        double[] sortedArray = ArraySorter.sort(inputArray);
        assertSame(sortedArray, inputArray);
    }
}
