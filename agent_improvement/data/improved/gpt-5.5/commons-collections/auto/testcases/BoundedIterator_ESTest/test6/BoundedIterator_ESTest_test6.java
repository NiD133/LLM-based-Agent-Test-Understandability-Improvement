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
public class BoundedIterator_ESTest_test6 extends BoundedIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        LinkedList<InstanceofPredicate> predicates = new LinkedList<InstanceofPredicate>();
        Iterator<InstanceofPredicate> descendingPredicateIterator = predicates.descendingIterator();

        long offsetAtStart = 0L;
        long negativeMaximumSize = (-1162L);

        try {
            new BoundedIterator<InstanceofPredicate>(descendingPredicateIterator, offsetAtStart, negativeMaximumSize);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Max parameter must not be negative.
            //
            verifyException("org.apache.commons.collections4.iterators.BoundedIterator", e);
        }
    }
}
