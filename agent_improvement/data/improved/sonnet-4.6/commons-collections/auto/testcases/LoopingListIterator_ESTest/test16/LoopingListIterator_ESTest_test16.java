package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test16 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * Verifies that a LoopingListIterator wrapping an empty list reports size 0.
     */
    @Test(timeout = 4000)
    public void test_sizeOfIteratorOverEmptyListIsZero() throws Throwable {
        LinkedList<Object> emptyList = new LinkedList<Object>();
        LoopingListIterator<Object> iterator = new LoopingListIterator<Object>(emptyList);

        int size = iterator.size();

        assertEquals("Iterator over empty list should have size 0", 0, size);
    }
}
