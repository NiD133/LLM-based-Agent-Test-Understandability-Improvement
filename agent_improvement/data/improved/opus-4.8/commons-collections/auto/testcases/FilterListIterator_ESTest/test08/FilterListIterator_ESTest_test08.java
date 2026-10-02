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
public class FilterListIterator_ESTest_test08 extends FilterListIterator_ESTest_scaffolding {

    /**
     * When the filter's predicate matches the single underlying element,
     * hasNext() reports that a matching element is available. Calling
     * hasNext() repeatedly is idempotent: it caches the matched element on
     * the first call (consuming the underlying iterator) and returns the same
     * answer on the second call without advancing further.
     */
    @Test(timeout = 4000)
    public void hasNextIsTrueAndIdempotentWhenSingleElementMatches() throws Throwable {
        Object matchingElement = new Object();
        LinkedList<Object> list = new LinkedList<Object>();
        list.push(matchingElement);
        ListIterator<Object> underlyingIterator = list.listIterator();

        // Predicate that accepts only the element we just inserted.
        EqualPredicate<Object> matchesInsertedElement =
                new EqualPredicate<Object>(matchingElement, DefaultEquator.defaultEquator());
        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(underlyingIterator, matchesInsertedElement);

        // First call caches the matching element, draining the underlying iterator.
        filterIterator.hasNext();
        // Second call returns the cached result.
        boolean hasNextAgain = filterIterator.hasNext();

        assertFalse(underlyingIterator.hasNext());
        assertTrue(hasNextAgain);
    }
}
