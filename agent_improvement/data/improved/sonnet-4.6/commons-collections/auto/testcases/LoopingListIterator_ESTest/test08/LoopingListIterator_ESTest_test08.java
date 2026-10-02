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
     * Verifies that an element added via the iterator is reachable through next()
     * and is present in the backing list.
     *
     * Steps:
     *  1. Create an empty LinkedList and wrap it with a LoopingListIterator.
     *  2. Add the value 0 through the iterator (which inserts it into the backing list).
     *  3. Call next() — the iterator should return the newly added element.
     *  4. Assert that the returned element exists in the original LinkedList,
     *     confirming that add() and next() both operate on the same backing list.
     */
    @Test(timeout = 4000)
    public void test08_addedElementIsReturnedByNextAndPresentInBackingList() throws Throwable {
        LinkedList<Integer> backingList = new LinkedList<Integer>();
        LoopingListIterator<Integer> iterator = new LoopingListIterator<Integer>(backingList);

        Integer valueToAdd = new Integer(0);
        iterator.add(valueToAdd);

        Integer returnedValue = iterator.next();

        assertTrue(backingList.contains(returnedValue));
    }
}
