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
public class ArraySorter_ESTest_test02 extends ArraySorter_ESTest_scaffolding {

    /**
     * Sorting an Object[] whose elements are all null should throw NullPointerException,
     * because natural ordering (Comparable) cannot compare null elements.
     */
    @Test(timeout = 4000)
    public void test02_sortObjectArrayWithNullElements_throwsNullPointerException() throws Throwable {
        // All 8 slots are null by default — no element implements Comparable, so sort fails
        Object[] arrayWithNullElements = new Object[8];

        try {
            ArraySorter.sort(arrayWithNullElements);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("java.util.ComparableTimSort", e);
        }
    }
}
