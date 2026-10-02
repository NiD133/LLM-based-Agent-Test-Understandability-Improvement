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
public class ZippingIterator_ESTest_test2 extends ZippingIterator_ESTest_scaffolding {

    /**
     * Verifies that remove() works correctly after forEachRemaining() has consumed
     * all elements. The ZippingIterator is constructed with the same underlying
     * iterator passed twice, and a no-op IfClosure (using an empty OnePredicate
     * and empty ChainedClosure) is used to iterate all elements without side effects.
     * After forEachRemaining() exhausts the iterator, remove() should successfully
     * remove the last element that was returned.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Set up a single-element list and obtain a descending iterator over it
        LinkedList<Object> singleElementList = new LinkedList<Object>();
        Object element = new Object();
        singleElementList.add(element);
        Iterator<Object> descendingIter = singleElementList.descendingIterator();

        // Create a ZippingIterator backed by the same iterator in both slots
        ZippingIterator<Object> zippingIterator = new ZippingIterator<Object>(descendingIter, descendingIter);

        // Build a no-op IfClosure: OnePredicate with no predicates always returns false,
        // so the closure body (an empty ChainedClosure) never executes
        Predicate<Object>[] emptyPredicates = (Predicate<Object>[]) Array.newInstance(Predicate.class, 0);
        OnePredicate<Object> neverTruePredicate = new OnePredicate<Object>(emptyPredicates);

        Closure<Object>[] emptyClosures = (Closure<Object>[]) Array.newInstance(Closure.class, 0);
        Closure<Object> noOpClosure = ChainedClosure.chainedClosure((Closure<? super Object>[]) emptyClosures);

        IfClosure<Object> noOpIfClosure = new IfClosure<Object>(neverTruePredicate, noOpClosure);

        // Consume all remaining elements via the no-op closure; this sets lastReturned
        zippingIterator.forEachRemaining(noOpIfClosure);

        // remove() should succeed because lastReturned was set during forEachRemaining
        zippingIterator.remove();
    }
}
