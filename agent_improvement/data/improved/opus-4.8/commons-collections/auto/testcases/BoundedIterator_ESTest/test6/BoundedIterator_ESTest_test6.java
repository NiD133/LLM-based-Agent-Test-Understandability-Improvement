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
public class BoundedIterator_ESTest_test6 extends BoundedIterator_ESTest_scaffolding {

    /**
     * Constructing a BoundedIterator with a negative {@code max} must be rejected
     * with an IllegalArgumentException ("Max parameter must not be negative.").
     */
    @Test(timeout = 4000)
    public void constructorRejectsNegativeMax() throws Throwable {
        Iterator<InstanceofPredicate> emptyIterator =
                new LinkedList<InstanceofPredicate>().descendingIterator();
        final long validOffset = 0L;
        final long negativeMax = -1162L;

        try {
            new BoundedIterator<InstanceofPredicate>(emptyIterator, validOffset, negativeMax);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Max parameter must not be negative.
            verifyException("org.apache.commons.collections4.iterators.BoundedIterator", e);
        }
    }
}
