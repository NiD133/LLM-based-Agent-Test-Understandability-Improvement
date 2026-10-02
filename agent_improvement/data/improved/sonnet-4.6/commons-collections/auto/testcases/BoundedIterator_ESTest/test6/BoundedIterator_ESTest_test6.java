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
     * Verifies that constructing a BoundedIterator with a negative max throws
     * IllegalArgumentException. The offset is valid (0), but max = -1162 is negative.
     */
    @Test(timeout = 4000)
    public void test_constructorRejectsNegativeMax() throws Throwable {
        LinkedList<InstanceofPredicate> emptyList = new LinkedList<InstanceofPredicate>();
        Iterator<InstanceofPredicate> descendingIterator = emptyList.descendingIterator();

        long validOffset = 0L;
        long negativeMax = -1162L;

        try {
            new BoundedIterator<InstanceofPredicate>(descendingIterator, validOffset, negativeMax);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.collections4.iterators.BoundedIterator", e);
        }
    }
}
