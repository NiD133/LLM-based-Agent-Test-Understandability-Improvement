package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedIterator_ESTest_test0 extends BoundedIterator_ESTest_scaffolding {

    /**
     * Verifies that calling remove() on a BoundedIterator before ever calling next()
     * throws an IllegalStateException, regardless of offset and max settings.
     */
    @Test(timeout = 4000)
    public void remove_beforeCallingNext_throwsIllegalStateException() throws Throwable {
        LinkedList<Integer> emptyList = new LinkedList<Integer>();
        Iterator<Integer> emptyIterator = emptyList.iterator();
        // offset=0, max=1357: next() has never been called, so remove() must fail
        BoundedIterator<Integer> boundedIterator = new BoundedIterator<Integer>(emptyIterator, 0L, 1357L);

        try {
            boundedIterator.remove();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.collections4.iterators.BoundedIterator", e);
        }
    }
}
