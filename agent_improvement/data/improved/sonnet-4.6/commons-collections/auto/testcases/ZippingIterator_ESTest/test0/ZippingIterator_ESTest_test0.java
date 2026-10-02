package org.apache.commons.collections4.iterators;

import org.junit.Test;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.commons.collections4.functors.InstanceofPredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZippingIterator_ESTest_test0 extends ZippingIterator_ESTest_scaffolding {

    /**
     * Verifies that ZippingIterator can be constructed with three iterators
     * (including the same iterator instance passed multiple times) without throwing.
     */
    @Test(timeout = 4000)
    public void test0_constructWithThreeEmptyIterators() throws Throwable {
        LinkedList<InstanceofPredicate> emptyList = new LinkedList<InstanceofPredicate>();
        Iterator<InstanceofPredicate> emptyIterator = emptyList.iterator();

        // The 3-argument constructor accepts three iterators for interleaved iteration
        ZippingIterator<InstanceofPredicate> zippingIterator =
                new ZippingIterator<InstanceofPredicate>(emptyIterator, emptyIterator, emptyIterator);
    }
}
