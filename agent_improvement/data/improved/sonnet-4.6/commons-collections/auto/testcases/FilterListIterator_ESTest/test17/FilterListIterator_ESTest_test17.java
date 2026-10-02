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
public class FilterListIterator_ESTest_test17 extends FilterListIterator_ESTest_scaffolding {

    /**
     * Tests that after consuming the only matching element and calling hasPrevious(),
     * hasNext() returns false and previousIndex() reflects the consumed element's position.
     *
     * A single-element list is iterated with a predicate that matches only that element.
     * After next() is called to consume it, hasPrevious() is called (which internally
     * repositions the underlying iterator). hasNext() must still return false, and
     * previousIndex() must be 0 (pointing to the consumed element).
     */
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        // Set up a single-element list containing one object
        LinkedList<Object> singleElementList = new LinkedList<Object>();
        Object targetObject = new Object();
        singleElementList.push(targetObject);
        ListIterator<Object> listIterator = singleElementList.listIterator();

        // Create a predicate that only accepts elements equal to targetObject
        DefaultEquator<Object> equator = DefaultEquator.defaultEquator();
        EqualPredicate<Object> matchesTargetObject = new EqualPredicate<Object>(targetObject, equator);

        // Wrap the list iterator so only elements matching the predicate are visible
        FilterListIterator<Object> filterListIterator = new FilterListIterator<Object>(listIterator, matchesTargetObject);

        // Consume the single matching element by advancing forward
        filterListIterator.next();

        // Check backward navigation (internally repositions the underlying iterator)
        filterListIterator.hasPrevious();

        // After consuming the only matching element, there should be no more elements ahead
        boolean hasMoreElements = filterListIterator.hasNext();

        // The previous index should be 0 (the position of the consumed element)
        assertEquals(0, filterListIterator.previousIndex());
        assertFalse(hasMoreElements);
    }
}
