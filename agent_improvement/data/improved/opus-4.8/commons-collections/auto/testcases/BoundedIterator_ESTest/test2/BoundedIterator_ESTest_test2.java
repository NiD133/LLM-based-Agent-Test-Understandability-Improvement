package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedIterator_ESTest_test2 extends BoundedIterator_ESTest_scaffolding {

    /**
     * A BoundedIterator with a max of 0 is allowed to return no elements at all.
     * Calling next() must therefore fail immediately with a NoSuchElementException,
     * even when the underlying iterator is otherwise valid.
     */
    @Test(timeout = 4000)
    public void nextThrowsWhenMaxIsZero() throws Throwable {
        Iterator<Integer> emptySource = new LinkedList<Integer>().iterator();
        BoundedIterator<Integer> boundedIterator =
                new BoundedIterator<Integer>(emptySource, 0L, 0L);

        try {
            boundedIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            // next() rejects the call before touching the underlying iterator,
            // so the exception originates in BoundedIterator and carries no message.
            verifyException("org.apache.commons.collections4.iterators.BoundedIterator", e);
        }
    }
}
