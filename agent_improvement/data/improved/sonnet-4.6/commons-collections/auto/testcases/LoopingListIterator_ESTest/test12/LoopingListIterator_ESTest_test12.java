package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test12 extends LoopingListIterator_ESTest_scaffolding {

    // hasPrevious() returns true whenever the underlying list is non-empty,
    // regardless of the iterator's current position.
    @Test(timeout = 4000)
    public void test_hasPrevious_returnsTrueForNonEmptyList() throws Throwable {
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.offer(Integer.valueOf(0));

        LoopingListIterator<Integer> iterator = new LoopingListIterator<Integer>(list);

        boolean hasPrevious = iterator.hasPrevious();

        assertTrue(hasPrevious);
    }
}
