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
public class LoopingListIterator_ESTest_test12 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * hasPrevious() should return true for a non-empty list, because the
     * looping iterator can always wrap around to the last element.
     */
    @Test(timeout = 4000)
    public void hasPreviousReturnsTrueWhenListIsNotEmpty() throws Throwable {
        LinkedList<Integer> singleElementList = new LinkedList<Integer>();
        singleElementList.offer(Integer.valueOf(0));
        LoopingListIterator<Integer> loopingIterator =
            new LoopingListIterator<Integer>(singleElementList);

        boolean hasPrevious = loopingIterator.hasPrevious();

        assertTrue(hasPrevious);
    }
}
