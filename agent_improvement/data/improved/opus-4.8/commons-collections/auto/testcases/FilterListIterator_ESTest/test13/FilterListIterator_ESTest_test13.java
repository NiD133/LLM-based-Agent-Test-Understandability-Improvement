package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test13 extends FilterListIterator_ESTest_scaffolding {

    /**
     * FilterListIterator does not support element removal: calling remove()
     * must always throw an UnsupportedOperationException.
     */
    @Test(timeout = 4000)
    public void removeThrowsUnsupportedOperationException() throws Throwable {
        FilterListIterator<Integer> filterListIterator = new FilterListIterator<Integer>();

        try {
            filterListIterator.remove();
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // FilterListIterator.remove() is not supported.
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", e);
        }
    }
}
