package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.ChainedClosure;
import org.apache.commons.collections4.functors.IfClosure;
import org.apache.commons.collections4.functors.InstanceofPredicate;
import org.apache.commons.collections4.functors.OnePredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZippingIterator_ESTest_test0 extends ZippingIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        LinkedList<InstanceofPredicate> predicates = new LinkedList<InstanceofPredicate>();
        Iterator<InstanceofPredicate> emptyPredicateIterator = predicates.iterator();

        // Exercise the three-iterator constructor with the same empty iterator used in each slot.
        ZippingIterator<InstanceofPredicate> zippingIterator = new ZippingIterator<InstanceofPredicate>(
                emptyPredicateIterator,
                emptyPredicateIterator,
                emptyPredicateIterator);
    }
}
