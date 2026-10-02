package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test07 extends LoopingListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_nextIndex_returnsZero_whenIteratorAtStart() throws Throwable {
        // A single-element list positioned at the start should report nextIndex() == 0
        LinkedList<Object> list = new LinkedList<Object>();
        list.add(new Object());

        LoopingListIterator<Object> iterator = new LoopingListIterator<Object>(list);

        int nextIdx = iterator.nextIndex();

        assertEquals(0, nextIdx);
    }
}
