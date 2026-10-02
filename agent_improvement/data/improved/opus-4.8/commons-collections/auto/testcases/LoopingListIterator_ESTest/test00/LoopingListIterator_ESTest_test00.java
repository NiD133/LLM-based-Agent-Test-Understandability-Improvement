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
     * After adding an element through the iterator, the cursor sits just after
     * the newly inserted element, so previousIndex() should report that
     * element's position (index 0 in a previously empty list).
     */
    @Test(timeout = 4000)
    public void previousIndexAfterAddReturnsInsertedElementIndex() throws Throwable {
        LinkedList<InstanceofPredicate> backingList = new LinkedList<InstanceofPredicate>();
        LoopingListIterator<InstanceofPredicate> iterator =
                new LoopingListIterator<InstanceofPredicate>(backingList);

        InstanceofPredicate element = new InstanceofPredicate(Object.class);
        iterator.add(element);

        int previousIndex = iterator.previousIndex();

        assertEquals(0, previousIndex);
    }
}
