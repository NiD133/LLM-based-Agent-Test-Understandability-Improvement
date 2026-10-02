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
     * Verifies that calling next() on a BoundedIterator with a max of zero
     * throws NoSuchElementException, even before exhausting the underlying iterator,
     * because the bounded range allows no elements.
     */
    @Test(timeout = 4000)
    public void test_next_throwsNoSuchElement_whenMaxIsZero() throws Throwable {
        LinkedList<Integer> emptyList = new LinkedList<Integer>();
        Iterator<Integer> emptyIterator = emptyList.iterator();
        // offset=0, max=0: no elements are within the bounded range
        BoundedIterator<Integer> boundedIterator = new BoundedIterator<Integer>(emptyIterator, 0L, 0L);

        try {
            boundedIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.apache.commons.collections4.iterators.BoundedIterator", e);
        }
    }
}
