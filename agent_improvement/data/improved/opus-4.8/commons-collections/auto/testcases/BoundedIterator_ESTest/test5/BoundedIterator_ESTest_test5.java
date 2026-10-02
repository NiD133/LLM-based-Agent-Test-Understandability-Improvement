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
     * A BoundedIterator configured to return at most zero elements (max = 0)
     * should report that it has no further elements, even when the underlying
     * iterator is non-empty (here it is empty as well).
     */
    @Test(timeout = 4000)
    public void hasNextReturnsFalseWhenMaxIsZero() throws Throwable {
        LinkedList<Integer> emptyList = new LinkedList<Integer>();
        Iterator<Integer> underlyingIterator = emptyList.iterator();

        long offset = 0L;
        long max = 0L;
        BoundedIterator<Integer> boundedIterator =
                new BoundedIterator<Integer>(underlyingIterator, offset, max);

        assertFalse(boundedIterator.hasNext());
    }
}
