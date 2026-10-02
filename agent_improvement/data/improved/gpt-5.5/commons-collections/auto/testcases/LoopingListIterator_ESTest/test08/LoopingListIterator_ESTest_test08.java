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

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        LinkedList<Integer> listUnderIteration = new LinkedList<Integer>();
        LoopingListIterator<Integer> loopingIterator = new LoopingListIterator<Integer>(listUnderIteration);
        Integer addedValue = new Integer(0);

        // The value inserted through the iterator is returned by the next traversal step.
        loopingIterator.add(addedValue);
        Integer nextValue = loopingIterator.next();

        assertTrue(listUnderIteration.contains(nextValue));
    }
}
