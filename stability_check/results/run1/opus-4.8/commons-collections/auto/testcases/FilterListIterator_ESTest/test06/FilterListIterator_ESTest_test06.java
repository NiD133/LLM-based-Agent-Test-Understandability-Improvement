package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test06 extends FilterListIterator_ESTest_scaffolding {

    /**
     * Calling {@code next()} when the underlying iterator has no matching
     * elements must throw a {@link NoSuchElementException}.
     *
     * Here the underlying iterator is an empty {@code FilterListIterator}, so
     * there is no next element for the outer iterator to return.
     */
    @Test(timeout = 4000)
    public void nextOnEmptyIteratorThrowsNoSuchElementException() throws Throwable {
        FilterListIterator<Integer> emptyUnderlyingIterator = new FilterListIterator<Integer>();
        FilterListIterator<Object> filterIterator = new FilterListIterator<Object>(emptyUnderlyingIterator);

        try {
            filterIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            // No matching next element, so next() reports NoSuchElementException.
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", e);
        }
    }
}
