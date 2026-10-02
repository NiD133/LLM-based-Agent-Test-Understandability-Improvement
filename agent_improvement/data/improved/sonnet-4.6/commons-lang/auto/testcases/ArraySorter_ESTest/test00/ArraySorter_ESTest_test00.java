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
public class ArraySorter_ESTest_test00 extends ArraySorter_ESTest_scaffolding {

    /**
     * Verifies that ArraySorter.sort(T[], Comparator) returns the same array instance it was given,
     * i.e. the sort is performed in-place and the original reference is returned unchanged.
     */
    @Test(timeout = 4000)
    public void test_sortWithComparator_returnsSameArrayInstance() throws Throwable {
        Object[] inputArray = new Object[3];

        // Comparator that always considers elements equal (returns 0), stubbed for two comparisons
        Comparator<Object> alwaysEqualComparator = (Comparator<Object>) mock(Comparator.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0).when(alwaysEqualComparator).compare(any(), any());

        Object[] sortedArray = ArraySorter.sort(inputArray, (Comparator<? super Object>) alwaysEqualComparator);

        // sort() must return the exact same array reference, not a new copy
        assertSame(inputArray, sortedArray);
    }
}
