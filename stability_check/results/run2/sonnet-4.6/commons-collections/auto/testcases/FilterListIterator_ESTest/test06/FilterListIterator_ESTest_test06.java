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
public class FilterListIterator_ESTest_test06 extends FilterListIterator_ESTest_scaffolding {

    /**
     * Verifies that calling next() on a FilterListIterator whose underlying iterator
     * has no matching elements throws NoSuchElementException.
     *
     * The inner iterator has no predicate and no source list, so it always reports
     * hasNext() == false. The outer iterator therefore finds no elements to return,
     * and next() must throw NoSuchElementException.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // An empty, predicate-less FilterListIterator used as the underlying source.
        // Because it has no iterator and no predicate, hasNext() always returns false.
        FilterListIterator<Integer> emptyInnerIterator = new FilterListIterator<Integer>();

        // Wraps the empty inner iterator; no predicate is set on this outer iterator.
        FilterListIterator<Object> outerIterator = new FilterListIterator<Object>(emptyInnerIterator);

        // Calling next() on an iterator with no available elements must throw NoSuchElementException.
        try {
            outerIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.apache.commons.collections4.iterators.FilterListIterator", e);
        }
    }
}
