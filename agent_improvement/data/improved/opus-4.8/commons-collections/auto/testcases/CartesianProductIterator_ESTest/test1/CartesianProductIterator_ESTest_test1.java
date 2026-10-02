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
     * When the iterator is built from zero iterables, the Cartesian product is
     * empty, so calling {@code next()} immediately must throw
     * {@link NoSuchElementException}.
     */
    @Test(timeout = 4000)
    public void nextOnEmptyProductThrowsNoSuchElementException() throws Throwable {
        Iterable<Object>[] noIterables = (Iterable<Object>[]) Array.newInstance(Iterable.class, 0);
        CartesianProductIterator<Object> iterator = new CartesianProductIterator<Object>(noIterables);

        try {
            iterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            // next() throws because there are no tuples to produce; no message is set.
            verifyException("org.apache.commons.collections4.iterators.CartesianProductIterator", e);
        }
    }
}
