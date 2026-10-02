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
public class FilterListIterator_ESTest_test06 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        FilterListIterator<Integer> emptyBackingIterator = new FilterListIterator<Integer>();
        FilterListIterator<Object> iteratorWithNoMatches = new FilterListIterator<Object>(emptyBackingIterator);

        try {
            iteratorWithNoMatches.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", e);
        }
    }
}
