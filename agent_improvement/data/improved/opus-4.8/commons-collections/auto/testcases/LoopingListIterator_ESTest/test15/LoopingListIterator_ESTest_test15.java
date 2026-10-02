package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test15 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * Adding then removing the only element should leave the iterator empty,
     * so hasPrevious() reports false.
     */
    @Test(timeout = 4000)
    public void addThenRemoveSoleElementLeavesIteratorEmpty() throws Throwable {
        LinkedList<Integer> backingList = new LinkedList<Integer>();
        LoopingListIterator<Integer> iterator = new LoopingListIterator<Integer>(backingList);

        // Insert a single element, then position the cursor on it via previous().
        Integer element = Integer.valueOf(-3423);
        iterator.add(element);
        iterator.previous();

        // Removing the element empties the underlying list.
        iterator.remove();

        assertFalse(iterator.hasPrevious());
    }
}
