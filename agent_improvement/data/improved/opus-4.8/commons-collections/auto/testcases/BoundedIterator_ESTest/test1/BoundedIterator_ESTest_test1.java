package org.apache.commons.collections4.iterators;

import org.junit.Test;
import java.util.Iterator;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedIterator_ESTest_test1 extends BoundedIterator_ESTest_scaffolding {

    /**
     * After advancing past the first element with next(), remove() is allowed
     * and deletes that element from the underlying list via the decorated iterator.
     */
    @Test(timeout = 4000)
    public void removeAfterNextDeletesCurrentElement() throws Throwable {
        LinkedList<Integer> sourceList = new LinkedList<Integer>();
        sourceList.add(Integer.valueOf(0));

        Iterator<Integer> sourceIterator = sourceList.iterator();
        long offset = 0L;
        long max = 3226L;
        BoundedIterator<Integer> boundedIterator =
                new BoundedIterator<Integer>(sourceIterator, offset, max);

        boundedIterator.next();
        boundedIterator.remove();
    }
}
