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
        Class<ClientInfoStatus> class0 = ClientInfoStatus.class;
        EnumSet<ClientInfoStatus> enumSet0 = EnumSet.allOf(class0);
        Iterable<ClientInfoStatus>[] iterableArray0 = (Iterable<ClientInfoStatus>[]) Array.newInstance(Iterable.class, 3);
        iterableArray0[0] = (Iterable<ClientInfoStatus>) enumSet0;
        iterableArray0[1] = (Iterable<ClientInfoStatus>) enumSet0;
        ClientInfoStatus clientInfoStatus0 = ClientInfoStatus.REASON_UNKNOWN_PROPERTY;
        EnumSet<ClientInfoStatus> enumSet1 = EnumSet.of(clientInfoStatus0);
        iterableArray0[2] = (Iterable<ClientInfoStatus>) enumSet1;
        CartesianProductIterator<Object> cartesianProductIterator0 = new CartesianProductIterator<Object>(iterableArray0);
        List<Object> list0 = cartesianProductIterator0.next();
        assertFalse(list0.isEmpty());
        List<Object> list1 = cartesianProductIterator0.next();
        assertFalse(list1.equals((Object) list0));
    }
}
