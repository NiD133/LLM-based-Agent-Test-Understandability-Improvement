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
public class FilterListIterator_ESTest_test01 extends FilterListIterator_ESTest_scaffolding {

    /**
     * Verifies that calling hasNext() on a nested FilterListIterator exhausts the
     * underlying iterator, and that hasPrevious() returns false because no element
     * has been returned via next() yet (the iterator position has not advanced).
     *
     * Setup: a single-element list whose element matches the inner filter predicate
     * (EqualPredicate) and also passes the outer filter predicate (UniquePredicate).
     * The outer FilterListIterator wraps an inner FilterListIterator which itself
     * wraps the raw ListIterator.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Build a single-element list and obtain its ListIterator
        LinkedList<Object> singleElementList = new LinkedList<Object>();
        Object targetObject = new Object();
        singleElementList.push(targetObject);
        ListIterator<Object> rawListIterator = singleElementList.listIterator();

        // Inner filter: only pass elements equal to targetObject
        DefaultEquator<Object> equator = DefaultEquator.defaultEquator();
        EqualPredicate<Object> equalsPredicate = new EqualPredicate<Object>(targetObject, equator);
        FilterListIterator<Object> innerFilterIterator = new FilterListIterator<Object>(rawListIterator, equalsPredicate);

        // Outer filter: only pass elements seen for the first time (uniqueness filter)
        UniquePredicate<Object> uniquePredicate = new UniquePredicate<Object>();
        FilterListIterator<Object> outerFilterIterator = new FilterListIterator<Object>(innerFilterIterator, uniquePredicate);

        // Calling hasNext() causes the outer iterator to scan forward through the chain,
        // consuming the single element from the underlying rawListIterator in the process.
        outerFilterIterator.hasNext();

        // The underlying raw iterator has been fully consumed by the hasNext() scan above
        assertFalse(rawListIterator.hasNext());

        // hasPrevious() returns false: no element has been returned via next() yet,
        // so the logical position has not advanced and there is nothing to go back to.
        boolean hasPreviousResult = outerFilterIterator.hasPrevious();
        assertFalse(hasPreviousResult);
    }
}
