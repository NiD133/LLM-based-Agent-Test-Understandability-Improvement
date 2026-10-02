package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test02 extends LoopingListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02_previousIndexAtStartOfSingleElementListReturnsLastIndex() throws Throwable {
        LinkedList<Object> singleElementList = new LinkedList<Object>();
        singleElementList.add(new Object());

        LoopingListIterator<Object> iterator = new LoopingListIterator<Object>(singleElementList);

        // When the iterator is at the beginning of the list, previousIndex() wraps around
        // and returns list.size() - 1 (the last valid index), not -1.
        int previousIdx = iterator.previousIndex();

        assertEquals(0, previousIdx);
    }
}
