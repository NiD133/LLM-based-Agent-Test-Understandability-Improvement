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
        Class<ClientInfoStatus> clientInfoStatusType = ClientInfoStatus.class;
        EnumSet<ClientInfoStatus> allClientInfoStatuses = EnumSet.allOf(clientInfoStatusType);

        Iterable<ClientInfoStatus>[] productInputs = (Iterable<ClientInfoStatus>[]) Array.newInstance(Iterable.class, 3);
        productInputs[0] = (Iterable<ClientInfoStatus>) allClientInfoStatuses;
        productInputs[1] = (Iterable<ClientInfoStatus>) allClientInfoStatuses;

        ClientInfoStatus fixedStatus = ClientInfoStatus.REASON_UNKNOWN_PROPERTY;
        EnumSet<ClientInfoStatus> fixedStatusOnly = EnumSet.of(fixedStatus);
        productInputs[2] = (Iterable<ClientInfoStatus>) fixedStatusOnly;

        CartesianProductIterator<Object> iterator = new CartesianProductIterator<Object>(productInputs);
        List<Object> firstTuple = iterator.next();
        assertFalse(firstTuple.isEmpty());

        List<Object> secondTuple = iterator.next();
        assertFalse(secondTuple.equals((Object) firstTuple));
    }
}
