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
public class ZippingIterator_ESTest_test1 extends ZippingIterator_ESTest_scaffolding {

    /**
     * Verifies that calling remove() without a prior next() throws IllegalStateException,
     * because no element has been returned yet and there is nothing to remove.
     */
    @Test(timeout = 4000)
    public void test1() throws Throwable {
        // Create an empty list and obtain its iterator (no elements to iterate)
        LinkedList<Object> emptyList = new LinkedList<Object>();
        Iterator<Object> emptyIterator = emptyList.descendingIterator();

        // Wrap the same empty iterator twice in a ZippingIterator
        ZippingIterator<Object> zippingIterator = new ZippingIterator<Object>(emptyIterator, emptyIterator);

        // remove() before any next() call must throw IllegalStateException
        try {
            zippingIterator.remove();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // No value can be removed at present
            //
            verifyException("org.apache.commons.collections4.iterators.ZippingIterator", e);
        }
    }
}
