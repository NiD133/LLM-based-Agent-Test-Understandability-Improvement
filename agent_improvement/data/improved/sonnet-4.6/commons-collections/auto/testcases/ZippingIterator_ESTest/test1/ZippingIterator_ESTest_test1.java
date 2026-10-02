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
     * Verifies that calling remove() before any call to next() throws
     * IllegalStateException, because there is no "last returned" element
     * to remove yet.
     */
    @Test(timeout = 4000)
    public void test_removeWithoutPriorNext_throwsIllegalStateException() throws Throwable {
        LinkedList<Object> emptyList = new LinkedList<Object>();
        Iterator<Object> emptyIterator = emptyList.descendingIterator();
        ZippingIterator<Object> zippingIterator = new ZippingIterator<Object>(emptyIterator, emptyIterator);

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
