package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.Comparator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test00 extends ArraySorter_ESTest_scaffolding {

    /**
     * ArraySorter.sort(T[], Comparator) sorts the array in place and returns
     * the very same array instance it was given.
     */
    @Test(timeout = 4000)
    public void sortWithComparatorReturnsSameArrayInstance() throws Throwable {
        Object[] arrayToSort = new Object[3];

        // A comparator that always reports elements as equal, so sorting leaves
        // the array unchanged while still exercising the comparator path.
        Comparator<Object> equalComparator =
                (Comparator<Object>) mock(Comparator.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0).when(equalComparator).compare(any(), any());

        Object[] sortedArray = ArraySorter.sort(arrayToSort, equalComparator);

        // sort() returns the same array reference (fluent style), not a copy.
        assertSame(arrayToSort, sortedArray);
    }
}
