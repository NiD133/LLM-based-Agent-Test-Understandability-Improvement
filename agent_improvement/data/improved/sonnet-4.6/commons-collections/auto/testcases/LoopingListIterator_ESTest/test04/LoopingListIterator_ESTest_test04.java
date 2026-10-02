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
public class LoopingListIterator_ESTest_test04 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * Verifies that calling previous() on a LoopingListIterator positioned at the start
     * of the list wraps around and returns the last element (looping behaviour).
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Build a single-element list: outerList = [ innerList ]
        LinkedList<Object> innerList = new LinkedList<Object>();
        LinkedList<LinkedList<Object>> outerList = new LinkedList<LinkedList<Object>>();
        outerList.add(innerList);

        // The iterator starts at index 0 (before any element), so hasPrevious() is false.
        // Calling previous() should loop around and return the last (only) element.
        LoopingListIterator<LinkedList<Object>> iterator = new LoopingListIterator<LinkedList<Object>>(outerList);
        LinkedList<Object> previousElement = iterator.previous();

        // The returned element must be the one that was placed in the list.
        assertTrue(outerList.contains(previousElement));
    }
}
