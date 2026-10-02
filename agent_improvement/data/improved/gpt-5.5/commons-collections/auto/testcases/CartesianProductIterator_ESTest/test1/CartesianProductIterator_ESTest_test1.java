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
public class CartesianProductIterator_ESTest_test1 extends CartesianProductIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        Iterable<Object>[] emptyInputIterables = (Iterable<Object>[]) Array.newInstance(Iterable.class, 0);
        CartesianProductIterator<Object> emptyProductIterator = new CartesianProductIterator<Object>(emptyInputIterables);

        try {
            emptyProductIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException expectedException) {
            verifyException("org.apache.commons.collections4.iterators.CartesianProductIterator", expectedException);
        }
    }
}
