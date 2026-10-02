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
     * Calling next() must throw NoSuchElementException when no matching element
     * can be produced. Here the outer iterator wraps an empty, unconfigured
     * FilterListIterator (no backing ListIterator and no predicate), so there is
     * nothing for next() to return.
     */
    @Test(timeout = 4000)
    public void testNextThrowsWhenNoMatchingElementExists() throws Throwable {
        FilterListIterator<Integer> emptyBackingIterator = new FilterListIterator<Integer>();
        FilterListIterator<Object> filterIterator = new FilterListIterator<Object>(emptyBackingIterator);

        try {
            filterIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            // next() found no matching element; the exception carries no message.
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", e);
        }
    }
}
