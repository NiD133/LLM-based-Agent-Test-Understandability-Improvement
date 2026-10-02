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
     * Verifies that calling next() then previous() on a FilterListIterator
     * returns the cursor to index 0 (the start of the list).
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Set up a single-element list and a predicate that matches that element
        LinkedList<Object> list = new LinkedList<Object>();
        Object element = new Object();
        list.push(element);

        ListIterator<Object> listIterator = list.listIterator();
        EqualPredicate<Object> matchesElement =
                new EqualPredicate<Object>(element, DefaultEquator.defaultEquator());

        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(listIterator, matchesElement);

        // Advance forward then step back — cursor should return to index 0
        filterIterator.next();
        filterIterator.previous();

        assertEquals(0, filterIterator.nextIndex());
    }
}
