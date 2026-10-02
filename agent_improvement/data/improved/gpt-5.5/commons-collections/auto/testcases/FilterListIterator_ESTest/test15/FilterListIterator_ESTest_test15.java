package org.apache.commons.collections4.iterators;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import java.util.ListIterator;

import org.apache.commons.collections4.functors.DefaultEquator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test15 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        DefaultEquator<Object> defaultEquator = DefaultEquator.defaultEquator();
        FilterListIterator<Integer> iterator = new FilterListIterator<Integer>((ListIterator<? extends Integer>) null);

        try {
            iterator.set((Integer) defaultEquator.HASHCODE_NULL);
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException exception) {
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", exception);
        }
    }
}
