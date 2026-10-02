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
     * Verifies that after adding an element, navigating back to it via previous(),
     * and then removing it, the list becomes empty and hasPrevious() returns false.
     */
    @Test(timeout = 4000)
    public void test_addThenPreviousThenRemove_leavesListEmpty() throws Throwable {
        // Arrange: create an iterator over an empty list
        LinkedList<Integer> emptyList = new LinkedList<Integer>();
        LoopingListIterator<Integer> iterator = new LoopingListIterator<Integer>(emptyList);
        Integer valueToAdd = new Integer((-3423));

        // Act: add an element, navigate back to it, then remove it
        iterator.add(valueToAdd);
        iterator.previous();
        iterator.remove();

        // Assert: the list is now empty, so hasPrevious() must return false
        assertFalse(iterator.hasPrevious());
    }
}
