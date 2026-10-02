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
     * After advancing past the single matching element with next(), the iterator
     * should report a previous element and a previousIndex() of 0.
     */
    @Test(timeout = 4000)
    public void hasPreviousIsTrueAfterConsumingTheOnlyMatchingElement() throws Throwable {
        // List containing one element that the predicate will accept.
        Object matchingElement = new Object();
        LinkedList<Object> list = new LinkedList<Object>();
        list.push(matchingElement);

        // Filter that only lets through elements equal to matchingElement.
        EqualPredicate<Object> acceptsMatchingElement =
                new EqualPredicate<Object>(matchingElement, DefaultEquator.defaultEquator());
        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(list.listIterator(), acceptsMatchingElement);

        // Move forward past the only matching element.
        filterIterator.next();

        // Now there is an element behind the cursor. The first call primes the
        // iterator's lookbehind state; the second confirms it stays consistent.
        filterIterator.hasPrevious();
        boolean hasPrevious = filterIterator.hasPrevious();

        assertEquals(0, filterIterator.previousIndex());
        assertTrue(hasPrevious);
    }
}
