package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test10 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * Verifies that after advancing the iterator and replacing the current element
     * with itself via set(), hasNext() still returns true because the list is non-empty.
     */
    @Test(timeout = 4000)
    public void test_setCurrentElement_doesNotAffectHasNext() throws Throwable {
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(Integer.valueOf(2024));

        LoopingListIterator<Integer> iterator = new LoopingListIterator<Integer>(list);
        Integer current = iterator.next();
        iterator.set(current);

        assertTrue(iterator.hasNext());
    }
}
