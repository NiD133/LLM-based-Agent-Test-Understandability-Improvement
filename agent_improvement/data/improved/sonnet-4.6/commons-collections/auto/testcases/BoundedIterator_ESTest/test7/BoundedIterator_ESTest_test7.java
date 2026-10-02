package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.InstanceofPredicate;
import org.apache.commons.collections4.functors.SwitchClosure;
import org.apache.commons.collections4.functors.TransformerClosure;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedIterator_ESTest_test7 extends BoundedIterator_ESTest_scaffolding {

    /**
     * Verifies that constructing a BoundedIterator with a negative offset throws
     * IllegalArgumentException. The offset (-963) is invalid because the iterator
     * cannot skip a negative number of elements.
     */
    @Test(timeout = 4000)
    public void test_constructorWithNegativeOffset_throwsIllegalArgumentException() throws Throwable {
        long negativeOffset = -963L;
        long max = -963L;
        BoundedIterator<Predicate<Object>> boundedIterator = null;
        try {
            boundedIterator = new BoundedIterator<Predicate<Object>>((Iterator<? extends Predicate<Object>>) null, negativeOffset, max);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Offset parameter must not be negative.
            //
            verifyException("org.apache.commons.collections4.iterators.BoundedIterator", e);
        }
    }
}
