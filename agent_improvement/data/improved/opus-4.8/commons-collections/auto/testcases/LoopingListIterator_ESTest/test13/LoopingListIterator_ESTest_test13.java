package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test13 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * An iterator over an empty list has no elements to loop over,
     * so hasNext() must report false.
     */
    @Test(timeout = 4000)
    public void hasNextReturnsFalseForEmptyList() throws Throwable {
        LinkedList<Integer> emptyList = new LinkedList<Integer>();
        LoopingListIterator<Integer> iterator = new LoopingListIterator<Integer>(emptyList);

        boolean hasNext = iterator.hasNext();

        assertFalse(hasNext);
    }
}
