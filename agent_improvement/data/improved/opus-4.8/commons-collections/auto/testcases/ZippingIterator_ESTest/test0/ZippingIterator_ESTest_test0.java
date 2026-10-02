package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
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
     * Verifies that the three-iterator constructor accepts non-null iterators
     * (here, the same empty iterator passed three times) without throwing.
     */
    @Test(timeout = 4000)
    public void constructorWithThreeIteratorsSucceeds() throws Throwable {
        LinkedList<InstanceofPredicate> emptyList = new LinkedList<InstanceofPredicate>();
        Iterator<InstanceofPredicate> emptyIterator = emptyList.iterator();

        ZippingIterator<InstanceofPredicate> zippingIterator =
                new ZippingIterator<InstanceofPredicate>(emptyIterator, emptyIterator, emptyIterator);

        assertNotNull(zippingIterator);
    }
}
