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

    /**
     * When the iterator sits at the physical beginning of the list, previousIndex()
     * loops around to the end and returns (list size - 1). For a single-element list
     * that index is 0.
     */
    @Test(timeout = 4000)
    public void previousIndexAtStartWrapsToLastElement() throws Throwable {
        LinkedList<Object> singleElementList = new LinkedList<Object>();
        singleElementList.add(new Object());
        LoopingListIterator<Object> loopingIterator = new LoopingListIterator<Object>(singleElementList);

        int previousIndex = loopingIterator.previousIndex();

        assertEquals(0, previousIndex);
    }
}
