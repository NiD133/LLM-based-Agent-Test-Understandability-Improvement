package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.InstanceofPredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test16 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * size() should report the size of the underlying list. When the iterator
     * wraps an empty list, the reported size is zero.
     */
    @Test(timeout = 4000)
    public void sizeOfIteratorOverEmptyListIsZero() throws Throwable {
        LinkedList<Object> emptyList = new LinkedList<Object>();
        LoopingListIterator<Object> iterator = new LoopingListIterator<Object>(emptyList);

        int size = iterator.size();

        assertEquals(0, size);
    }
}
