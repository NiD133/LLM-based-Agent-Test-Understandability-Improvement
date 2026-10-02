package org.apache.commons.collections4.iterators;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZippingIterator_ESTest_test1 extends ZippingIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        LinkedList<Object> source = new LinkedList<Object>();
        Iterator<Object> sharedIterator = source.descendingIterator();
        ZippingIterator<Object> zippingIterator = new ZippingIterator<Object>(sharedIterator, sharedIterator);

        try {
            zippingIterator.remove();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.collections4.iterators.ZippingIterator", e);
        }
    }
}
