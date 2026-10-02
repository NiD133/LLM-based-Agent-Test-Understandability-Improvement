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
public class LoopingListIterator_ESTest_test03 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * Calling previous() on an iterator backed by an empty list must throw
     * NoSuchElementException because there are no elements to loop over.
     */
    @Test(timeout = 4000)
    public void test03_previousOnEmptyListThrowsNoSuchElementException() throws Throwable {
        LinkedList<Integer> emptyList = new LinkedList<Integer>();
        LoopingListIterator<Integer> iterator = new LoopingListIterator<Integer>(emptyList);

        try {
            iterator.previous();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.apache.commons.collections4.iterators.LoopingListIterator", e);
        }
    }
}
