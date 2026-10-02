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

    /**
     * On a fresh iterator positioned at the start of a non-empty list,
     * nextIndex() should report the index of the first element, which is 0.
     */
    @Test(timeout = 4000)
    public void nextIndexAtStartOfListReturnsZero() throws Throwable {
        LinkedList<Object> list = new LinkedList<Object>();
        list.add(new Object());
        LoopingListIterator<Object> iterator = new LoopingListIterator<Object>(list);

        int nextIndex = iterator.nextIndex();

        assertEquals(0, nextIndex);
    }
}
