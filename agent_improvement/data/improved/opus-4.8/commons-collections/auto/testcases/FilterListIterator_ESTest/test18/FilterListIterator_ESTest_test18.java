package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test18 extends FilterListIterator_ESTest_scaffolding {

    /**
     * A freshly constructed FilterListIterator sits before the first element,
     * so previousIndex() should report -1 (one less than the next index of 0).
     */
    @Test(timeout = 4000)
    public void previousIndexOnNewIteratorIsMinusOne() throws Throwable {
        FilterListIterator<Integer> filterListIterator = new FilterListIterator<Integer>();

        int previousIndex = filterListIterator.previousIndex();

        assertEquals(-1, previousIndex);
    }
}
