package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CartesianProductIterator_ESTest_test1 extends CartesianProductIterator_ESTest_scaffolding {

    /**
     * Verifies that calling next() on an iterator constructed with zero iterables
     * throws NoSuchElementException, since there are no tuples to produce.
     */
    @Test(timeout = 4000)
    public void test1() throws Throwable {
        // Create an empty array of iterables — the Cartesian product of nothing has no elements
        Iterable<Object>[] emptyIterables = (Iterable<Object>[]) Array.newInstance(Iterable.class, 0);
        CartesianProductIterator<Object> iterator = new CartesianProductIterator<Object>(emptyIterables);

        try {
            iterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.apache.commons.collections4.iterators.CartesianProductIterator", e);
        }
    }
}
