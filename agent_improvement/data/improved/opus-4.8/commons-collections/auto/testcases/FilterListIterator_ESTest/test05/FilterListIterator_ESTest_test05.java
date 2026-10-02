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
public class FilterListIterator_ESTest_test05 extends FilterListIterator_ESTest_scaffolding {

    /**
     * Stepping forward with next() and then back with previous() over the only
     * matching element should leave the iterator positioned at the start again,
     * so nextIndex() returns 0.
     */
    @Test(timeout = 4000)
    public void nextThenPreviousRestoresStartingIndex() throws Throwable {
        // A list containing a single element.
        Object onlyElement = new Object();
        LinkedList<Object> list = new LinkedList<Object>();
        list.push(onlyElement);

        // Filter that only accepts elements equal to onlyElement, so the single
        // element passes through the filter.
        EqualPredicate<Object> matchesOnlyElement =
                new EqualPredicate<Object>(onlyElement, DefaultEquator.defaultEquator());
        ListIterator<Object> listIterator = list.listIterator();
        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(listIterator, matchesOnlyElement);

        // Advance past the element, then step back over it.
        filterIterator.next();
        filterIterator.previous();

        assertEquals(0, filterIterator.nextIndex());
    }
}
