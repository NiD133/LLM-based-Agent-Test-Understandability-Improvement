package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test16 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        FilterListIterator<Integer> iteratorUnderTest = new FilterListIterator<Integer>();

        // Preserve the original self-delegating iterator setup.
        iteratorUnderTest.setListIterator(iteratorUnderTest);

        assertEquals((-1), iteratorUnderTest.previousIndex());
    }
}
