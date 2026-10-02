package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.DefaultEquator;
import org.apache.commons.collections4.functors.EqualPredicate;
import org.apache.commons.collections4.functors.InstanceofPredicate;
import org.apache.commons.collections4.functors.NullIsFalsePredicate;
import org.apache.commons.collections4.functors.UniquePredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test03 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Build a list containing one element that does NOT match the predicate's target
        LinkedList<Object> list = new LinkedList<Object>();
        Object existingElement = new Object();
        list.push(existingElement);
        ListIterator<Object> listIterator = list.listIterator();

        // Create a predicate that only accepts a different object (targetElement)
        Object targetElement = new Object();
        DefaultEquator<Object> equator = DefaultEquator.defaultEquator();
        EqualPredicate<Object> matchTargetOnly = new EqualPredicate<Object>(targetElement, equator);

        // Wrap the list iterator so that only elements equal to targetElement pass through
        FilterListIterator<Object> filterIterator = new FilterListIterator<Object>(listIterator, matchTargetOnly);

        // hasNext() must return false: the list has one element but it does not equal targetElement.
        // Internally, hasNext() advances the underlying iterator past existingElement while scanning,
        // which is why listIterator.hasPrevious() becomes true afterwards.
        boolean hasMatchingNext = filterIterator.hasNext();
        assertTrue(listIterator.hasPrevious());
        assertFalse(hasMatchingNext);
    }
}
