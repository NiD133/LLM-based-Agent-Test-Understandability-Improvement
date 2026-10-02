package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test09 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * Calling next() on an iterator built from an empty list must throw a
     * NoSuchElementException, because there are no elements to loop over.
     */
    @Test(timeout = 4000)
    public void nextOnEmptyListThrowsNoSuchElementException() throws Throwable {
        LinkedList<Integer> emptyList = new LinkedList<Integer>();
        LoopingListIterator<Integer> iteratorOverEmptyList =
            new LoopingListIterator<Integer>(emptyList);

        try {
            iteratorOverEmptyList.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            // There are no elements for this iterator to loop on
            verifyException("org.apache.commons.collections4.iterators.LoopingListIterator", e);
        }
    }
}
