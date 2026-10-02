package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Array;
import java.sql.ClientInfoStatus;
import java.util.EnumSet;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CartesianProductIterator_ESTest_test0 extends CartesianProductIterator_ESTest_scaffolding {

    /**
     * Builds a Cartesian product over three iterables and verifies that the
     * first two tuples are non-empty and differ from each other.
     */
    @Test(timeout = 4000)
    public void next_returnsDistinctNonEmptyTuples() throws Throwable {
        // All four ClientInfoStatus constants, reused as the first two dimensions.
        EnumSet<ClientInfoStatus> allStatuses = EnumSet.allOf(ClientInfoStatus.class);
        // A single-element set used as the third (innermost) dimension.
        EnumSet<ClientInfoStatus> singleStatus =
                EnumSet.of(ClientInfoStatus.REASON_UNKNOWN_PROPERTY);

        // The constructor takes Iterable<? extends E>... so build a typed array.
        @SuppressWarnings("unchecked")
        Iterable<ClientInfoStatus>[] dimensions =
                (Iterable<ClientInfoStatus>[]) Array.newInstance(Iterable.class, 3);
        dimensions[0] = allStatuses;
        dimensions[1] = allStatuses;
        dimensions[2] = singleStatus;

        CartesianProductIterator<Object> iterator =
                new CartesianProductIterator<Object>(dimensions);

        // First tuple: one element drawn from each of the three dimensions.
        List<Object> firstTuple = iterator.next();
        assertFalse(firstTuple.isEmpty());

        // Second tuple must differ from the first as the iterator advances.
        List<Object> secondTuple = iterator.next();
        assertFalse(secondTuple.equals((Object) firstTuple));
    }
}
