package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.apache.commons.collections4.functors.InstanceofPredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZippingIterator_ESTest_test3 extends ZippingIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        LinkedList<InstanceofPredicate> emptyPredicates = new LinkedList<InstanceofPredicate>();
        Iterator<InstanceofPredicate> emptyIterator = emptyPredicates.iterator();
        ZippingIterator<InstanceofPredicate> zippingIterator =
                new ZippingIterator<InstanceofPredicate>(emptyIterator, emptyIterator);

        try {
            zippingIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.apache.commons.collections4.iterators.ZippingIterator", e);
        }
    }
}
