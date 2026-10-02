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
     * Verifies that calling remove() on a FilterListIterator always throws
     * UnsupportedOperationException, since removal is not supported by this iterator.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        FilterListIterator<Integer> iterator = new FilterListIterator<Integer>();

        try {
            iterator.remove();
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", e);
        }
    }
}
