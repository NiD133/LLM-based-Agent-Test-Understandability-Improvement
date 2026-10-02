package org.apache.commons.collections4.iterators;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test00 extends FilterListIterator_ESTest_scaffolding {

    /**
     * A FilterListIterator built with the no-arg constructor has no underlying
     * list iterator, so it cannot produce a previous element. Calling
     * previous() must therefore throw NoSuchElementException.
     */
    @Test(timeout = 4000)
    public void previousWithoutUnderlyingIteratorThrowsNoSuchElement() throws Throwable {
        FilterListIterator<Integer> filterListIterator = new FilterListIterator<Integer>();

        try {
            filterListIterator.previous();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            // The exception originates from FilterListIterator and carries no message.
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", e);
        }
    }
}
