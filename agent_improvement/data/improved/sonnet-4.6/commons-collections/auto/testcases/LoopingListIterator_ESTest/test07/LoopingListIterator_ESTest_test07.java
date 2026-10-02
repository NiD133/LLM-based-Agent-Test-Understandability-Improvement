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
public class LoopingListIterator_ESTest_test07 extends LoopingListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_nextIndex_returnsZero_whenIteratorIsAtStart() throws Throwable {
        // A single-element list gives the iterator a valid starting position at index 0
        LinkedList<Object> list = new LinkedList<Object>();
        list.add(new Object());

        LoopingListIterator<Object> iterator = new LoopingListIterator<Object>(list);

        // At construction, the iterator is positioned before the first element, so nextIndex() == 0
        int nextIdx = iterator.nextIndex();
        assertEquals(0, nextIdx);
    }
}
