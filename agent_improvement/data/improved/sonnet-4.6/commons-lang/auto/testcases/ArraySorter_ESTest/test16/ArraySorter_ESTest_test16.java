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
public class ArraySorter_ESTest_test16 extends ArraySorter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_sortByteArray_allZeros_returnsSameArray() throws Throwable {
        // An array of 5 bytes initialized to zero (Java default) should remain all zeros after sorting
        byte[] allZeros = new byte[5];
        byte[] sorted = ArraySorter.sort(allZeros);
        assertArrayEquals(new byte[] {0, 0, 0, 0, 0}, sorted);
    }
}
