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
public class LoopingListIterator_ESTest_test15 extends LoopingListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        LinkedList<Integer> backingList = new LinkedList<Integer>();
        LoopingListIterator<Integer> iterator = new LoopingListIterator<Integer>(backingList);
        Integer insertedValue = new Integer((-3423));

        iterator.add(insertedValue);
        iterator.previous();
        iterator.remove();

        assertFalse(iterator.hasPrevious());
    }
}
