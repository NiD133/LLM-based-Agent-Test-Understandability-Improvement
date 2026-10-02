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
public class LoopingListIterator_ESTest_test06 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * Verifies that calling nextIndex() on a LoopingListIterator backed by an
     * empty list throws NoSuchElementException, because there are no elements
     * to iterate over.
     */
    @Test(timeout = 4000)
    public void test06_nextIndex_onEmptyList_throwsNoSuchElementException() throws Throwable {
        LinkedList<Integer> emptyList = new LinkedList<Integer>();
        LoopingListIterator<Integer> iterator = new LoopingListIterator<Integer>(emptyList);

        try {
            iterator.nextIndex();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.apache.commons.collections4.iterators.LoopingListIterator", e);
        }
    }
}
