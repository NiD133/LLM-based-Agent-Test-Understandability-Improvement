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
public class LoopingListIterator_ESTest_test05 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * After consuming the only element via next(), nextIndex() should return 0
     * because the LoopingListIterator wraps back to the beginning of the list.
     */
    @Test(timeout = 4000)
    public void test_nextIndex_wrapsToZero_afterConsumingSingleElement() throws Throwable {
        // Build a one-element list containing an InstanceofPredicate for Integer
        LinkedList<InstanceofPredicate> singlePredicateList = new LinkedList<InstanceofPredicate>();
        InstanceofPredicate integerInstanceofPredicate = new InstanceofPredicate(Integer.class);
        singlePredicateList.offerFirst(integerInstanceofPredicate);

        LinkedList<Predicate<Object>> predicateList = new LinkedList<Predicate<Object>>(singlePredicateList);
        LoopingListIterator<Predicate<Object>> iterator = new LoopingListIterator<Predicate<Object>>(predicateList);

        // Advance past the single element; the iterator is now at the physical end of the list
        iterator.next();

        // nextIndex() on a looping iterator at the end of the list returns 0 (wraps around)
        int nextIdx = iterator.nextIndex();
        assertEquals(0, nextIdx);
    }
}
