package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.ListIterator;
import org.apache.commons.collections4.functors.DefaultEquator;
import org.apache.commons.collections4.functors.EqualPredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test02 extends FilterListIterator_ESTest_scaffolding {

    /**
     * Verifies that calling hasNext() on a FilterListIterator advances the underlying
     * iterator past the only matching element, so a subsequent hasPrevious() call
     * returns false even though the underlying iterator still has a previous element.
     *
     * The filter's hasNext() scans forward and caches the next match. When hasPrevious()
     * is then called, the filter undoes that scan internally, leaving the underlying
     * iterator back at position 0 with no element before it, so hasPrevious() is false.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Build a single-element list and position the iterator at the start
        LinkedList<Object> list = new LinkedList<Object>();
        Object singleElement = new Object();
        list.push(singleElement);
        ListIterator<Object> listIterator = list.listIterator();

        // Predicate that accepts only the single element
        DefaultEquator<Object> equator = DefaultEquator.defaultEquator();
        EqualPredicate<Object> matchesSingleElement = new EqualPredicate<Object>(singleElement, equator);

        FilterListIterator<Object> filterIterator = new FilterListIterator<Object>(listIterator, matchesSingleElement);

        // hasNext() scans forward, advancing the underlying iterator past singleElement
        filterIterator.hasNext();

        // The underlying iterator is now past the element, so it reports a previous
        assertTrue(listIterator.hasPrevious());

        // The filter's hasPrevious() internally reverses the forward scan, ending up at
        // position 0 where no previous element exists, so it returns false
        boolean hasPrevious = filterIterator.hasPrevious();
        assertFalse(hasPrevious);
    }
}
