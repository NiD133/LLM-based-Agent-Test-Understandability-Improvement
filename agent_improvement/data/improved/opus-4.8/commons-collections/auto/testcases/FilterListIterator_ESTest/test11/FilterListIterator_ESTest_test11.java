package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.collections4.functors.InstanceofPredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test11 extends FilterListIterator_ESTest_scaffolding {

    /**
     * FilterListIterator does not support insertion, so add(Object) must always
     * throw UnsupportedOperationException regardless of the element passed in.
     */
    @Test(timeout = 4000)
    public void testAddThrowsUnsupportedOperationException() throws Throwable {
        FilterListIterator<Object> filterListIterator = new FilterListIterator<Object>();
        InstanceofPredicate elementToAdd = new InstanceofPredicate(Integer.class);

        try {
            filterListIterator.add(elementToAdd);
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // FilterListIterator.add(Object) is not supported.
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", e);
        }
    }
}
