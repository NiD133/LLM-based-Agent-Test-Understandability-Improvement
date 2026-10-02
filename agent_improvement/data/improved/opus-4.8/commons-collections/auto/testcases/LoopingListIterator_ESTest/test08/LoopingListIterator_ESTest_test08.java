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
public class LoopingListIterator_ESTest_test08 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * Verifies that add() inserts the element into the underlying list and that
     * the subsequent next() call returns that same element (looping back to it).
     */
    @Test(timeout = 4000)
    public void addedElementIsInsertedIntoUnderlyingListAndReturnedByNext() throws Throwable {
        LinkedList<Integer> backingList = new LinkedList<Integer>();
        LoopingListIterator<Integer> loopingIterator = new LoopingListIterator<Integer>(backingList);

        Integer elementToAdd = new Integer(0);
        loopingIterator.add(elementToAdd);

        Integer returnedElement = loopingIterator.next();

        assertTrue(backingList.contains(returnedElement));
    }
}
