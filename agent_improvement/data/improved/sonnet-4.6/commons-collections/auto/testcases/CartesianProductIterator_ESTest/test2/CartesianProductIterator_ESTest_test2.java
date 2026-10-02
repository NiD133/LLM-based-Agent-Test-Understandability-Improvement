package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Array;
import java.sql.SQLNonTransientConnectionException;
import java.util.Comparator;
import java.util.TreeSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CartesianProductIterator_ESTest_test2 extends CartesianProductIterator_ESTest_scaffolding {

    /**
     * Verifies that the CartesianProductIterator constructor completes without error
     * when the first iterable in the array is empty. According to the constructor logic,
     * an empty iterable causes an early break so the remaining null slots (indices 1–3)
     * are never visited, avoiding a NullPointerException.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Create a 4-slot array; only index 0 will be populated
        Iterable<SQLNonTransientConnectionException>[] iterables =
                (Iterable<SQLNonTransientConnectionException>[]) Array.newInstance(Iterable.class, 4);

        // SQLNonTransientConnectionException is not Comparable, so a mock Comparator is
        // required to allow it to be stored in a TreeSet
        Comparator<SQLNonTransientConnectionException> mockComparator =
                (Comparator<SQLNonTransientConnectionException>) mock(Comparator.class, new ViolatedAssumptionAnswer());

        // An empty TreeSet — when the constructor iterates over it, hasNext() returns false
        // immediately, triggering the early-exit branch and leaving indices 1–3 unread
        TreeSet<SQLNonTransientConnectionException> emptySet = new TreeSet<>(mockComparator);
        iterables[0] = (Iterable<SQLNonTransientConnectionException>) emptySet;

        // Construction succeeds: the empty first iterable causes the constructor to break
        // before it encounters the null entries at positions 1, 2, and 3
        CartesianProductIterator<SQLNonTransientConnectionException> cartesianIterator =
                new CartesianProductIterator<>(iterables);
    }
}
