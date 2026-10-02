package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import org.apache.commons.collections4.Predicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedIterator_ESTest_test7 extends BoundedIterator_ESTest_scaffolding {

    /**
     * The constructor must reject a negative offset by throwing an
     * IllegalArgumentException, even before the (null) iterator argument is
     * dereferenced. The offset check happens first in the constructor.
     */
    @Test(timeout = 4000)
    public void constructorRejectsNegativeOffset() throws Throwable {
        final long negativeOffset = -963L;
        final long max = -963L;

        try {
            new BoundedIterator<Predicate<Object>>(
                    (Iterator<? extends Predicate<Object>>) null, negativeOffset, max);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Offset parameter must not be negative.
            verifyException("org.apache.commons.collections4.iterators.BoundedIterator", e);
        }
    }
}
