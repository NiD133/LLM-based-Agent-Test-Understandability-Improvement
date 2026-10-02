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
     * Verifies that calling next() on a FilterListIterator wrapping an empty,
     * predicate-less FilterListIterator throws NoSuchElementException.
     *
     * The inner iterator has no backing list and no predicate, so hasNext()
     * returns false. The outer iterator therefore has nothing to advance to,
     * causing next() to throw.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        FilterListIterator<Integer> emptyInnerIterator = new FilterListIterator<Integer>();
        FilterListIterator<Object> outerIterator = new FilterListIterator<Object>(emptyInnerIterator);

        try {
            outerIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", e);
        }
    }
}
