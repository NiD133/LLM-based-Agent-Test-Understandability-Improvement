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
public class FilterListIterator_ESTest_test07 extends FilterListIterator_ESTest_scaffolding {

    /**
     * Verifies that after advancing past the single matching element via next(),
     * hasPrevious() returns true and previousIndex() reflects the consumed element's index (0).
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Set up a single-element list and obtain its list iterator
        LinkedList<Object> singleElementList = new LinkedList<Object>();
        Object targetElement = new Object();
        singleElementList.push(targetElement);
        ListIterator<Object> listIterator = singleElementList.listIterator();

        // Create a predicate that only accepts elements equal to targetElement
        DefaultEquator<Object> equator = DefaultEquator.defaultEquator();
        EqualPredicate<Object> equalityPredicate = new EqualPredicate<Object>(targetElement, equator);

        // Wrap the list iterator with the equality filter
        FilterListIterator<Object> filterIterator = new FilterListIterator<Object>(listIterator, equalityPredicate);

        // Advance past the single matching element (nextIndex becomes 1)
        filterIterator.next();

        // First hasPrevious() call triggers internal look-ahead state setup
        filterIterator.hasPrevious();
        // Second call returns the stable result after state is initialized
        boolean hasPreviousElement = filterIterator.hasPrevious();

        // previousIndex() == nextIndex - 1 == 0, pointing to the consumed element
        assertEquals(0, filterIterator.previousIndex());
        assertTrue(hasPreviousElement);
    }
}
