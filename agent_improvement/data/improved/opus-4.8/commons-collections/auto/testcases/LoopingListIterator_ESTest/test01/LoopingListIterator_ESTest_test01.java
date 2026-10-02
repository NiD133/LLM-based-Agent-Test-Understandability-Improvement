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
public class LoopingListIterator_ESTest_test01 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * previousIndex() must throw NoSuchElementException when the iterator
     * wraps an empty list, because there are no elements to loop over.
     */
    @Test(timeout = 4000)
    public void previousIndexOnEmptyListThrowsNoSuchElementException() throws Throwable {
        LinkedList<Object> emptyList = new LinkedList<Object>();
        LoopingListIterator<Object> iteratorOverEmptyList =
            new LoopingListIterator<Object>(emptyList);

        try {
            iteratorOverEmptyList.previousIndex();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            // "There are no elements for this iterator to loop on"
            verifyException("org.apache.commons.collections4.iterators.LoopingListIterator", e);
        }
    }
}
