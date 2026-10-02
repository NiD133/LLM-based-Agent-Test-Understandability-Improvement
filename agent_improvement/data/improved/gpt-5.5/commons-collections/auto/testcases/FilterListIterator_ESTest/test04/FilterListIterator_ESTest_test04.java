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
public class FilterListIterator_ESTest_test04 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        LinkedList<Object> matchingElements = new LinkedList<Object>();
        Object matchingElement = new Object();
        matchingElements.push(matchingElement);

        ListIterator<Object> backingIterator = matchingElements.listIterator();
        DefaultEquator<Object> defaultEquator = DefaultEquator.defaultEquator();
        EqualPredicate<Object> matchesOnlyInsertedElement =
                new EqualPredicate<Object>(matchingElement, defaultEquator);

        FilterListIterator<Object> firstFilteredIterator =
                new FilterListIterator<Object>(backingIterator, matchesOnlyInsertedElement);
        firstFilteredIterator.next();
        firstFilteredIterator.hasPrevious();

        FilterListIterator<Object> secondFilteredIterator =
                new FilterListIterator<Object>(backingIterator, matchesOnlyInsertedElement);
        secondFilteredIterator.next();

        boolean hasAnotherMatchingElement = firstFilteredIterator.hasNext();

        assertEquals(1, firstFilteredIterator.nextIndex());
        assertFalse(hasAnotherMatchingElement);
    }
}
