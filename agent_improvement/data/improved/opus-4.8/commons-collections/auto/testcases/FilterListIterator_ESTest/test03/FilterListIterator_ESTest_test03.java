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
public class FilterListIterator_ESTest_test03 extends FilterListIterator_ESTest_scaffolding {

    /**
     * When no element in the underlying list matches the predicate,
     * {@link FilterListIterator#hasNext()} returns {@code false}.
     *
     * Searching for a match consumes the single element of the underlying
     * list iterator, so afterwards that iterator reports a previous element.
     */
    @Test(timeout = 4000)
    public void hasNextIsFalseWhenNoElementMatchesPredicate() throws Throwable {
        // Underlying list holds a single element.
        Object elementInList = new Object();
        LinkedList<Object> list = new LinkedList<Object>();
        list.push(elementInList);
        ListIterator<Object> underlyingIterator = list.listIterator();

        // Predicate looks for a different object, so it never matches the element.
        Object soughtElement = new Object();
        DefaultEquator<Object> equator = DefaultEquator.defaultEquator();
        EqualPredicate<Object> matchesSoughtElement = new EqualPredicate<Object>(soughtElement, equator);

        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(underlyingIterator, matchesSoughtElement);

        boolean hasNext = filterIterator.hasNext();

        // No element matched, so there is no next element.
        assertFalse(hasNext);
        // Scanning for a match advanced the underlying iterator past the element.
        assertTrue(underlyingIterator.hasPrevious());
    }
}
