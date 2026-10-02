package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test18 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_previousIndex_onNewIterator_returnsNegativeOne() throws Throwable {
        // A freshly constructed FilterListIterator has nextIndex == 0,
        // so previousIndex() must return nextIndex - 1 == -1.
        FilterListIterator<Integer> iterator = new FilterListIterator<Integer>();
        int previousIndex = iterator.previousIndex();
        assertEquals(-1, previousIndex);
    }
}
