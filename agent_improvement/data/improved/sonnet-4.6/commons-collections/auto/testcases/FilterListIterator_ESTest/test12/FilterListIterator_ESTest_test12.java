package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test12 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12_nextIndexIsZeroForNewlyConstructedIterator() throws Throwable {
        FilterListIterator<Object> emptyFilterIterator = new FilterListIterator<>();
        int initialNextIndex = emptyFilterIterator.nextIndex();
        assertEquals(0, initialNextIndex);
    }
}
