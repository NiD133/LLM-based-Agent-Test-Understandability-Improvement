package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test00 extends FilterListIterator_ESTest_scaffolding {

    /**
     * Calling previous() on an uninitialized FilterListIterator (no backing iterator set)
     * must throw NoSuchElementException because there is no previous element to return.
     */
    @Test(timeout = 4000)
    public void test00_previousOnUninitializedIteratorThrowsNoSuchElementException() throws Throwable {
        FilterListIterator<Integer> filterListIterator0 = new FilterListIterator<Integer>();
        try {
            filterListIterator0.previous();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", e);
        }
    }
}
