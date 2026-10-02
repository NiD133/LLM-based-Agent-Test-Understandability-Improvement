package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.evosuite.shaded.org.mockito.Mockito.*;
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
     * When the first input iterable is empty, the constructor stops scanning the
     * remaining iterables (which here are still null) and completes without error,
     * because an empty iterable already makes the Cartesian product empty.
     */
    @Test(timeout = 4000)
    public void constructorStopsAtEmptyFirstIterableWithoutTouchingLaterNulls() throws Throwable {
        // Four iterable slots; slots 1..3 are deliberately left null.
        @SuppressWarnings("unchecked")
        Iterable<SQLNonTransientConnectionException>[] inputIterables =
                (Iterable<SQLNonTransientConnectionException>[]) Array.newInstance(Iterable.class, 4);

        // The first iterable is an empty TreeSet (its comparator is never invoked).
        Comparator<SQLNonTransientConnectionException> comparator =
                mock(Comparator.class, new ViolatedAssumptionAnswer());
        TreeSet<SQLNonTransientConnectionException> emptyFirstIterable =
                new TreeSet<SQLNonTransientConnectionException>(comparator);
        inputIterables[0] = emptyFirstIterable;

        // Construction succeeds: the empty first iterable short-circuits the scan
        // before any of the null slots is dereferenced.
        new CartesianProductIterator<SQLNonTransientConnectionException>(inputIterables);
    }
}
