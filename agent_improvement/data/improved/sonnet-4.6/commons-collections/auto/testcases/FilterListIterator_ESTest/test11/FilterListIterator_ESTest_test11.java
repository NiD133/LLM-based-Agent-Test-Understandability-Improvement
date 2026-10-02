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

    @Test(timeout = 4000)
    public void test11_addThrowsUnsupportedOperationException() throws Throwable {
        FilterListIterator<Object> iterator = new FilterListIterator<>();
        InstanceofPredicate integerTypePredicate = new InstanceofPredicate(Integer.class);

        try {
            iterator.add(integerTypePredicate);
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", e);
        }
    }
}
