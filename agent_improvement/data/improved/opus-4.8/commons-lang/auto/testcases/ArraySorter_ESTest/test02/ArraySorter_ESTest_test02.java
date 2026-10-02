package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test02 extends ArraySorter_ESTest_scaffolding {

    /**
     * Sorting an Object[] whose elements are all {@code null} must fail: with no
     * comparator, {@link ArraySorter#sort(Object[])} relies on the elements'
     * natural ordering, and comparing {@code null} values throws a
     * NullPointerException from the underlying TimSort implementation.
     */
    @Test(timeout = 4000)
    public void sortObjectArrayOfNullsThrowsNullPointerException() throws Throwable {
        Object[] arrayOfNulls = new Object[8];

        try {
            ArraySorter.sort(arrayOfNulls);
            fail("Expected a NullPointerException when sorting null elements by natural ordering");
        } catch (NullPointerException e) {
            // Thrown while TimSort compares the null elements against each other.
            verifyException("java.util.ComparableTimSort", e);
        }
    }
}
