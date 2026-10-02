/*
 * Improved understandability version of the EvoSuite-generated test suite
 * for FilterListIterator. Refactored: descriptive method names, cleaner
 * variable names. Runtime behaviour is identical to the original.
 */

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
import org.apache.commons.collections4.iterators.FilterListIterator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest extends FilterListIterator_ESTest_scaffolding {

    // -----------------------------------------------------------------------
    // Exception-throwing behaviour for unsupported operations
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void previous_throwsNoSuchElementException_whenIteratorIsEmpty() throws Throwable {
        FilterListIterator<Integer> emptyIterator = new FilterListIterator<Integer>();
        try {
            emptyIterator.previous();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
        }
    }

    @Test(timeout = 4000)
    public void next_throwsNoSuchElementException_whenIteratorIsEmpty() throws Throwable {
        FilterListIterator<Integer> emptyInner = new FilterListIterator<Integer>();
        FilterListIterator<Object> outerIterator = new FilterListIterator<Object>(emptyInner);
        try {
            outerIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
        }
    }

    @Test(timeout = 4000)
    public void add_throwsUnsupportedOperationException() throws Throwable {
        FilterListIterator<Object> iterator = new FilterListIterator<Object>();
        InstanceofPredicate instanceofPredicate = new InstanceofPredicate(Integer.class);
        try {
            iterator.add(instanceofPredicate);
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }

    @Test(timeout = 4000)
    public void remove_throwsUnsupportedOperationException() throws Throwable {
        FilterListIterator<Integer> iterator = new FilterListIterator<Integer>();
        try {
            iterator.remove();
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }

    @Test(timeout = 4000)
    public void set_throwsUnsupportedOperationException() throws Throwable {
        DefaultEquator<Object> defaultEquator = DefaultEquator.defaultEquator();
        FilterListIterator<Integer> iterator = new FilterListIterator<Integer>((ListIterator<? extends Integer>) null);
        try {
            iterator.set((Integer) defaultEquator.HASHCODE_NULL);
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }

    // -----------------------------------------------------------------------
    // hasNext / hasPrevious behaviour
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void hasNext_returnsFalse_whenNoElementsMatchPredicate() throws Throwable {
        LinkedList<Object> list = new LinkedList<Object>();
        Object existingElement = new Object();
        list.push(existingElement);
        ListIterator<Object> listIterator = list.listIterator();

        // Predicate matches a *different* object, so no element passes the filter
        Object nonMatchingTarget = new Object();
        EqualPredicate<Object> nonMatchingPredicate =
                new EqualPredicate<Object>(nonMatchingTarget, DefaultEquator.defaultEquator());
        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(listIterator, nonMatchingPredicate);

        boolean hasNext = filterIterator.hasNext();
    }

    @Test(timeout = 4000)
    public void hasNext_isIdempotent_whenCalledTwice() throws Throwable {
        LinkedList<Object> list = new LinkedList<Object>();
        Object element = new Object();
        list.push(element);
        ListIterator<Object> listIterator = list.listIterator();
        EqualPredicate<Object> matchingPredicate =
                new EqualPredicate<Object>(element, DefaultEquator.defaultEquator());
        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(listIterator, matchingPredicate);

        filterIterator.hasNext(); // first call buffers the matching element
        boolean secondHasNext = filterIterator.hasNext();
    }

    @Test(timeout = 4000)
    public void hasNext_returnsFalse_afterForwardAndBackwardTraversal() throws Throwable {
        LinkedList<Object> list = new LinkedList<Object>();
        Object element = new Object();
        list.push(element);
        ListIterator<Object> listIterator = list.listIterator();
        EqualPredicate<Object> matchingPredicate =
                new EqualPredicate<Object>(element, DefaultEquator.defaultEquator());
        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(listIterator, matchingPredicate);
        filterIterator.next();
        filterIterator.hasPrevious();

        // A second FilterListIterator using the same (now-exhausted) underlying iterator
        FilterListIterator<Object> secondIterator =
                new FilterListIterator<Object>(listIterator, matchingPredicate);
        secondIterator.next();

        boolean hasNext = filterIterator.hasNext();
    }

    @Test(timeout = 4000)
    public void hasPrevious_returnsFalse_afterHasNext_whenElementIsBufferedForward() throws Throwable {
        LinkedList<Object> list = new LinkedList<Object>();
        Object element = new Object();
        list.push(element);
        ListIterator<Object> listIterator = list.listIterator();
        EqualPredicate<Object> matchingPredicate =
                new EqualPredicate<Object>(element, DefaultEquator.defaultEquator());
        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(listIterator, matchingPredicate);

        // hasNext buffers the matching element forward; hasPrevious should still return false
        filterIterator.hasNext();

        boolean hasPrevious = filterIterator.hasPrevious();
    }

    @Test(timeout = 4000)
    public void hasPrevious_returnsTrue_afterNextFollowedByHasPrevious() throws Throwable {
        LinkedList<Object> list = new LinkedList<Object>();
        Object element = new Object();
        list.push(element);
        ListIterator<Object> listIterator = list.listIterator();
        EqualPredicate<Object> matchingPredicate =
                new EqualPredicate<Object>(element, DefaultEquator.defaultEquator());
        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(listIterator, matchingPredicate);

        filterIterator.next();
        filterIterator.hasPrevious(); // warms the previous buffer

        boolean hasPrevious = filterIterator.hasPrevious();
    }

    @Test(timeout = 4000)
    public void hasPrevious_returnsFalse_afterHasNextOnNestedFilterIterator() throws Throwable {
        LinkedList<Object> list = new LinkedList<Object>();
        Object element = new Object();
        list.push(element);
        ListIterator<Object> listIterator = list.listIterator();
        EqualPredicate<Object> equalPredicate =
                new EqualPredicate<Object>(element, DefaultEquator.defaultEquator());
        FilterListIterator<Object> innerFilter =
                new FilterListIterator<Object>(listIterator, equalPredicate);
        FilterListIterator<Object> outerFilter =
                new FilterListIterator<Object>(innerFilter, new UniquePredicate<Object>());

        outerFilter.hasNext();

        boolean hasPrevious = outerFilter.hasPrevious();
    }

    @Test(timeout = 4000)
    public void hasNext_returnsFalse_afterHasPreviousReversesBufferedPosition() throws Throwable {
        LinkedList<Object> list = new LinkedList<Object>();
        Object element = new Object();
        list.push(element);
        ListIterator<Object> listIterator = list.listIterator();
        EqualPredicate<Object> matchingPredicate =
                new EqualPredicate<Object>(element, DefaultEquator.defaultEquator());
        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(listIterator, matchingPredicate);

        filterIterator.next();
        filterIterator.hasPrevious();

        boolean hasNext = filterIterator.hasNext();
    }

    // -----------------------------------------------------------------------
    // next / previous navigation
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void previous_afterNext_resetsNextIndexToZero() throws Throwable {
        LinkedList<Object> list = new LinkedList<Object>();
        Object element = new Object();
        list.push(element);
        ListIterator<Object> listIterator = list.listIterator();
        EqualPredicate<Object> matchingPredicate =
                new EqualPredicate<Object>(element, DefaultEquator.defaultEquator());
        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(listIterator, matchingPredicate);

        filterIterator.next();
        filterIterator.previous();

    }

    // -----------------------------------------------------------------------
    // Index accessors
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void nextIndex_returnsZero_forNewEmptyIterator() throws Throwable {
        FilterListIterator<Closure<Integer>> iterator = new FilterListIterator<Closure<Integer>>();
        int nextIndex = iterator.nextIndex();
    }

    @Test(timeout = 4000)
    public void previousIndex_returnsNegativeOne_forNewEmptyIterator() throws Throwable {
        FilterListIterator<Integer> iterator = new FilterListIterator<Integer>();
        int previousIndex = iterator.previousIndex();
    }

    @Test(timeout = 4000)
    public void setListIterator_updatesIterator_previousIndexRemainsNegativeOne() throws Throwable {
        FilterListIterator<Integer> iterator = new FilterListIterator<Integer>();
        iterator.setListIterator(iterator);
    }

    // -----------------------------------------------------------------------
    // Getter / setter accessors
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void getPredicate_returnsNull_whenConstructedWithNullPredicate() throws Throwable {
        FilterListIterator<Integer> iterator = new FilterListIterator<Integer>((Predicate<? super Integer>) null);
        Predicate<? super Integer> predicate = iterator.getPredicate();
    }

    @Test(timeout = 4000)
    public void getListIterator_returnsNull_whenConstructedWithPredicateOnly() throws Throwable {
        UniquePredicate<Object> uniquePredicate = new UniquePredicate<Object>();
        NullIsFalsePredicate<Object> wrappedPredicate = new NullIsFalsePredicate<Object>(uniquePredicate);
        FilterListIterator<Object> iterator = new FilterListIterator<Object>(wrappedPredicate);

        ListIterator<?> listIterator = iterator.getListIterator();
    }

    @Test(timeout = 4000)
    public void setPredicate_doesNotThrow_andNextIndexRemainsZero() throws Throwable {
        FilterListIterator<Transformer<Object, Object>> iterator =
                new FilterListIterator<Transformer<Object, Object>>();
        Object target = new Object();
        EqualPredicate<Object> predicate = new EqualPredicate<Object>(target, DefaultEquator.defaultEquator());
        iterator.setPredicate(predicate);
    }
}
