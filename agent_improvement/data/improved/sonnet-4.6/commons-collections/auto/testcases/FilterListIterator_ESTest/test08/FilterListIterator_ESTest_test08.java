package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
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
     * Verifies that FilterListIterator.hasNext() caches its look-ahead result.
     *
     * When the filter scans the underlying iterator to find the first matching element,
     * it consumes elements from that iterator. A subsequent hasNext() call must return
     * the same cached result without re-scanning, so the filter still reports a next
     * element even after the underlying iterator is fully exhausted.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Build a single-element list whose one element satisfies the predicate
        Object targetElement = new Object();
        LinkedList<Object> singleElementList = new LinkedList<Object>();
        singleElementList.push(targetElement);
        ListIterator<Object> underlyingIterator = singleElementList.listIterator();

        EqualPredicate<Object> matchesTarget =
                new EqualPredicate<Object>(targetElement, DefaultEquator.defaultEquator());
        FilterListIterator<Object> filterIterator =
                new FilterListIterator<Object>(underlyingIterator, matchesTarget);

        // First call: the filter reads ahead through the underlying iterator to locate
        // the matching element, caching it internally and exhausting the underlying iterator
        filterIterator.hasNext();

        // Second call: the filter must serve the result from the cache, not re-scan
        boolean secondHasNextResult = filterIterator.hasNext();

        // The underlying iterator is now exhausted due to the filter's look-ahead
        assertFalse(underlyingIterator.hasNext());
        // Despite the exhausted underlying iterator, the filter still sees a next element
        // because it cached the matching object during the first hasNext() call
        assertTrue(secondHasNextResult);
    }
}
