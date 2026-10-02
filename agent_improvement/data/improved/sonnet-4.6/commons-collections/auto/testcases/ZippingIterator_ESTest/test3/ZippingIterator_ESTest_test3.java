package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZippingIterator_ESTest_test3 extends ZippingIterator_ESTest_scaffolding {

    /**
     * Verifies that calling next() on a ZippingIterator whose underlying
     * iterators are both empty throws NoSuchElementException.
     */
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // Both iterators point to an empty list, so the ZippingIterator has no elements
        LinkedList<String> emptyList = new LinkedList<String>();
        Iterator<String> emptyIterator = emptyList.iterator();
        ZippingIterator<String> zippingIterator = new ZippingIterator<String>(emptyIterator, emptyIterator);

        try {
            zippingIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.apache.commons.collections4.iterators.ZippingIterator", e);
        }
    }
}
