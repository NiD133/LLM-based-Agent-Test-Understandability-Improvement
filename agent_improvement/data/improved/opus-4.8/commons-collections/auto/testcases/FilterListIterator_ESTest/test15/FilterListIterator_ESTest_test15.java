package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.ListIterator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test15 extends FilterListIterator_ESTest_scaffolding {

    /**
     * FilterListIterator does not support set(Object); it must throw
     * UnsupportedOperationException regardless of the element passed in.
     */
    @Test(timeout = 4000)
    public void testSetThrowsUnsupportedOperationException() throws Throwable {
        FilterListIterator<Integer> filterListIterator =
                new FilterListIterator<Integer>((ListIterator<? extends Integer>) null);

        try {
            filterListIterator.set(Integer.valueOf(-1));
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // FilterListIterator.set(Object) is not supported.
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", e);
        }
    }
}
