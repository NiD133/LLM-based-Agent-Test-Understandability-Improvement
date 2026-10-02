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
     * Calling next() must throw a NoSuchElementException when the underlying
     * iterator has no matching elements. Here the underlying iterator is itself
     * an empty FilterListIterator, so no element can ever be produced.
     */
    @Test(timeout = 4000)
    public void next_onEmptyUnderlyingIterator_throwsNoSuchElementException() throws Throwable {
        FilterListIterator<Integer> emptyUnderlyingIterator = new FilterListIterator<Integer>();
        FilterListIterator<Object> filterListIterator = new FilterListIterator<Object>(emptyUnderlyingIterator);

        try {
            filterListIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            // next() found no matching element and signalled exhaustion.
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", e);
        }
    }
}
