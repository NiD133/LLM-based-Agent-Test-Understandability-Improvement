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
     * Verifies that calling next() on a FilterListIterator wrapping an empty,
     * predicate-less FilterListIterator throws NoSuchElementException.
     * The inner iterator has no backing list and no predicate, so hasNext()
     * returns false, leaving the outer iterator with no elements to return.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Inner iterator with no backing list and no predicate — always empty
        FilterListIterator<Integer> emptyInnerIterator = new FilterListIterator<Integer>();

        // Outer iterator wraps the empty inner iterator; no predicate set
        FilterListIterator<Object> outerIterator = new FilterListIterator<Object>(emptyInnerIterator);

        // Calling next() on the outer iterator should throw NoSuchElementException
        // because the inner iterator has no elements to offer
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
