package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedIterator_ESTest_test5 extends BoundedIterator_ESTest_scaffolding {

    /**
     * A BoundedIterator with max=0 over an empty list should report no elements available.
     * With offset=0 and max=0, the iterator's window is empty, so hasNext() must return false.
     */
    @Test(timeout = 4000)
    public void test_hasNext_returnsFalse_whenMaxIsZeroAndListIsEmpty() throws Throwable {
        LinkedList<Integer> emptyList = new LinkedList<Integer>();
        Iterator<Integer> emptyIterator = emptyList.iterator();

        long offset = 0L;
        long maxElements = 0L;
        BoundedIterator<Integer> boundedIterator = new BoundedIterator<Integer>(emptyIterator, offset, maxElements);

        boolean hasNext = boundedIterator.hasNext();

        assertFalse(hasNext);
    }
}
