package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Comparator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test01 extends ArraySorter_ESTest_scaffolding {

    /**
     * Sorting a {@code null} array with a {@code null} comparator should simply
     * return {@code null} rather than throwing, since the method treats a null
     * array as a no-op.
     */
    @Test(timeout = 4000)
    public void sortNullArrayWithNullComparatorReturnsNull() throws Throwable {
        Integer[] nullArray = null;
        Comparator<? super Integer> nullComparator = null;

        Integer[] result = ArraySorter.sort(nullArray, nullComparator);

        assertNull(result);
    }
}
