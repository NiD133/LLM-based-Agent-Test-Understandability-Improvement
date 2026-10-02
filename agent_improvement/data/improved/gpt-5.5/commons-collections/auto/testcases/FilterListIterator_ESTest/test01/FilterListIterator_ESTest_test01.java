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
public class FilterListIterator_ESTest_test01 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        LinkedList<Object> sourceElements = new LinkedList<Object>();
        Object onlyElement = new Object();
        sourceElements.push(onlyElement);
        ListIterator<Object> sourceIterator = sourceElements.listIterator();

        DefaultEquator<Object> defaultEquator = DefaultEquator.defaultEquator();
        EqualPredicate<Object> matchesOnlyElement = new EqualPredicate<Object>(onlyElement, defaultEquator);
        FilterListIterator<Object> matchingIterator = new FilterListIterator<Object>(sourceIterator, matchesOnlyElement);

        UniquePredicate<Object> acceptsFirstOccurrence = new UniquePredicate<Object>();
        FilterListIterator<Object> uniqueMatchingIterator = new FilterListIterator<Object>(matchingIterator, acceptsFirstOccurrence);

        uniqueMatchingIterator.hasNext();
        assertFalse(sourceIterator.hasNext());

        boolean hasPreviousAfterPrefetch = uniqueMatchingIterator.hasPrevious();
        assertFalse(hasPreviousAfterPrefetch);
    }
}
