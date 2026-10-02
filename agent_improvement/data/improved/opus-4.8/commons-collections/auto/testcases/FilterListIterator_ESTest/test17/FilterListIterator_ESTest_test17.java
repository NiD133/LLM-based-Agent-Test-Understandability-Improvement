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
public class FilterListIterator_ESTest_test17 extends FilterListIterator_ESTest_scaffolding {

    /**
     * Consumes the single matching element via next(), then verifies that the
     * iterator reports it is positioned past the end: there is no further next
     * element, and the previous index points back at the consumed element (0).
     */
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        // A list whose only element is the one the predicate will accept.
        Object onlyElement = new Object();
        LinkedList<Object> list = new LinkedList<Object>();
        list.push(onlyElement);
        ListIterator<Object> backingIterator = list.listIterator();

        // Predicate that accepts exactly the single list element.
        EqualPredicate<Object> matchesOnlyElement =
                new EqualPredicate<Object>(onlyElement, DefaultEquator.defaultEquator());
        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(backingIterator, matchesOnlyElement);

        // Advance past the single matching element.
        filterIterator.next();
        filterIterator.hasPrevious();

        boolean hasNextAfterLast = filterIterator.hasNext();

        assertEquals(0, filterIterator.previousIndex());
        assertFalse(hasNextAfterLast);
    }
}
