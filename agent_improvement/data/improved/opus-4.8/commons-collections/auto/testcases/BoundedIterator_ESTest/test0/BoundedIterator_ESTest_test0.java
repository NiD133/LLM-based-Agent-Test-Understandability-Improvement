package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedIterator_ESTest_test0 extends BoundedIterator_ESTest_scaffolding {

    /**
     * Verifies that calling remove() before any call to next() throws an
     * IllegalStateException, as documented by BoundedIterator.remove().
     */
    @Test(timeout = 4000)
    public void removeBeforeNextThrowsIllegalStateException() throws Throwable {
        // Bound an empty iterator over the range [offset=0, max=1357).
        Iterator<Integer> emptyIterator = new LinkedList<Integer>().iterator();
        BoundedIterator<Integer> boundedIterator =
                new BoundedIterator<Integer>(emptyIterator, 0L, 1357L);

        try {
            // remove() is illegal until next() has positioned the iterator.
            boundedIterator.remove();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // remove() cannot be called before calling next()
            verifyException("org.apache.commons.collections4.iterators.BoundedIterator", e);
        }
    }
}
