package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Array;
import java.sql.ClientInfoStatus;
import java.sql.SQLNonTransientConnectionException;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CartesianProductIterator_ESTest_test0 extends CartesianProductIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // Build three iterables for a Cartesian product:
        // slot 0 and slot 1 each hold all ClientInfoStatus enum values;
        // slot 2 holds only REASON_UNKNOWN_PROPERTY.
        Class<ClientInfoStatus> clientInfoStatusClass = ClientInfoStatus.class;
        EnumSet<ClientInfoStatus> allClientInfoStatuses = EnumSet.allOf(clientInfoStatusClass);

        Iterable<ClientInfoStatus>[] iterables = (Iterable<ClientInfoStatus>[]) Array.newInstance(Iterable.class, 3);
        iterables[0] = (Iterable<ClientInfoStatus>) allClientInfoStatuses;
        iterables[1] = (Iterable<ClientInfoStatus>) allClientInfoStatuses;

        ClientInfoStatus unknownPropertyStatus = ClientInfoStatus.REASON_UNKNOWN_PROPERTY;
        EnumSet<ClientInfoStatus> singleStatusSet = EnumSet.of(unknownPropertyStatus);
        iterables[2] = (Iterable<ClientInfoStatus>) singleStatusSet;

        // Create the Cartesian product iterator and advance it twice.
        CartesianProductIterator<Object> iterator = new CartesianProductIterator<Object>(iterables);

        // The first tuple must be non-empty (one element per input iterable).
        List<Object> firstTuple = iterator.next();
        assertFalse(firstTuple.isEmpty());

        // Successive tuples must differ because the iterator advances through combinations.
        List<Object> secondTuple = iterator.next();
        assertFalse(secondTuple.equals((Object) firstTuple));
    }
}
