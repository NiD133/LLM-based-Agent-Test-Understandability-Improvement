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
     * After hasNext() finds the single matching element, the underlying
     * iterator has advanced past it, so hasPrevious() on the filter reports
     * no matching element behind the current position.
     */
    @Test(timeout = 4000)
    public void hasPreviousReturnsFalseAfterHasNextConsumesOnlyElement() throws Throwable {
        // List with a single element, positioned at the start.
        Object onlyElement = new Object();
        LinkedList<Object> list = new LinkedList<Object>();
        list.push(onlyElement);
        ListIterator<Object> backingIterator = list.listIterator();

        // Predicate matches exactly the one element in the list.
        EqualPredicate<Object> matchesOnlyElement =
                new EqualPredicate<Object>(onlyElement, DefaultEquator.defaultEquator());
        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(backingIterator, matchesOnlyElement);

        // Probing for a next match advances the backing iterator past the element.
        filterIterator.hasNext();
        assertTrue(backingIterator.hasPrevious());

        // No matching element lies before the current position.
        boolean hasPrevious = filterIterator.hasPrevious();
        assertFalse(hasPrevious);
    }
}
