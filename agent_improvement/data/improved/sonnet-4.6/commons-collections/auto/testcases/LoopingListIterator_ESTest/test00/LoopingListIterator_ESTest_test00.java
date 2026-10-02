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
public class LoopingListIterator_ESTest_test00 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * Verifies that after adding one element via the iterator's add() method,
     * previousIndex() returns 0 — the index of the newly added (and only) element.
     *
     * After add(), the cursor is positioned just after the inserted element,
     * so previousIndex() points back to it at index 0.
     */
    @Test(timeout = 4000)
    public void test_previousIndex_afterAddingOneElementToEmptyList_returnsZero() throws Throwable {
        // Arrange: create a LoopingListIterator over an initially empty list
        LinkedList<InstanceofPredicate> emptyList = new LinkedList<InstanceofPredicate>();
        LoopingListIterator<InstanceofPredicate> iterator = new LoopingListIterator<InstanceofPredicate>(emptyList);

        // Act: insert one element; the cursor advances past it (index 0)
        InstanceofPredicate objectPredicate = new InstanceofPredicate(Object.class);
        iterator.add(objectPredicate);
        int previousIdx = iterator.previousIndex();

        // Assert: the single inserted element sits at index 0
        assertEquals(0, previousIdx);
    }
}
