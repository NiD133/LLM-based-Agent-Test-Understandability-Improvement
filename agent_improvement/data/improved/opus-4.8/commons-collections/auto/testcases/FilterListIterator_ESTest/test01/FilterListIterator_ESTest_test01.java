package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.ListIterator;
import org.apache.commons.collections4.functors.DefaultEquator;
import org.apache.commons.collections4.functors.EqualPredicate;
import org.apache.commons.collections4.functors.UniquePredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test01 extends FilterListIterator_ESTest_scaffolding {

    /**
     * Builds two nested filtering iterators over a single-element list and verifies that,
     * after the outer iterator advances forward (consuming the backing list), there is no
     * previous element to step back to.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // A list holding exactly one element.
        Object element = new Object();
        LinkedList<Object> list = new LinkedList<Object>();
        list.push(element);
        ListIterator<Object> backingIterator = list.listIterator();

        // Inner filter: only lets through the element equal to the one we stored.
        EqualPredicate<Object> equalToElement =
                new EqualPredicate<Object>(element, DefaultEquator.defaultEquator());
        FilterListIterator<Object> innerFilter =
                new FilterListIterator<Object>(backingIterator, equalToElement);

        // Outer filter: only lets through elements seen for the first time.
        UniquePredicate<Object> uniqueOnly = new UniquePredicate<Object>();
        FilterListIterator<Object> outerFilter =
                new FilterListIterator<Object>(innerFilter, uniqueOnly);

        // Advancing forward walks through (and exhausts) the backing list iterator.
        outerFilter.hasNext();
        assertFalse(backingIterator.hasNext());

        // No element precedes the current position.
        boolean hasPrevious = outerFilter.hasPrevious();
        assertFalse(hasPrevious);
    }
}
