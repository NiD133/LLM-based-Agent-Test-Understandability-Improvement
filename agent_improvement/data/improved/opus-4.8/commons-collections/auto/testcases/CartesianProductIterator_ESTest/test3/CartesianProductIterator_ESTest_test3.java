package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Array;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CartesianProductIterator_ESTest_test3 extends CartesianProductIterator_ESTest_scaffolding {

    /**
     * The {@code remove()} operation is not supported by
     * {@link CartesianProductIterator} and must always throw an
     * {@link UnsupportedOperationException}, even when the iterator was
     * built from an empty set of iterables.
     */
    @Test(timeout = 4000)
    public void remove_throwsUnsupportedOperationException() throws Throwable {
        // Build an iterator with no input iterables.
        Iterable<Object>[] noIterables = (Iterable<Object>[]) Array.newInstance(Iterable.class, 0);
        CartesianProductIterator<Object> iterator = new CartesianProductIterator<Object>(noIterables);

        try {
            iterator.remove();
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // remove() is unsupported and thrown by CartesianProductIterator itself.
            verifyException("org.apache.commons.collections4.iterators.CartesianProductIterator", e);
        }
    }
}
