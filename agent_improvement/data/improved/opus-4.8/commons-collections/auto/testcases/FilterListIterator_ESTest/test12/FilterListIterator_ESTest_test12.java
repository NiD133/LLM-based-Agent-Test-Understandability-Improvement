package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.collections4.Closure;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test12 extends FilterListIterator_ESTest_scaffolding {

    /**
     * A freshly constructed FilterListIterator should report a next index of 0,
     * matching the initial position of a list iterator before any traversal.
     */
    @Test(timeout = 4000)
    public void nextIndexOnNewIteratorIsZero() throws Throwable {
        FilterListIterator<Closure<Integer>> filterListIterator =
                new FilterListIterator<Closure<Integer>>();

        int nextIndex = filterListIterator.nextIndex();

        assertEquals(0, nextIndex);
    }
}
