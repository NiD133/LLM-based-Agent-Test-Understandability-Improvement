package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test16 extends FilterListIterator_ESTest_scaffolding {

    /**
     * A freshly constructed FilterListIterator has not advanced, so its
     * previousIndex() should report -1 (one before the initial next index of 0),
     * even after a backing list iterator has been assigned.
     */
    @Test(timeout = 4000)
    public void previousIndexOfUnusedIteratorIsMinusOne() throws Throwable {
        FilterListIterator<Integer> filterListIterator = new FilterListIterator<Integer>();
        filterListIterator.setListIterator(filterListIterator);

        assertEquals(-1, filterListIterator.previousIndex());
    }
}
