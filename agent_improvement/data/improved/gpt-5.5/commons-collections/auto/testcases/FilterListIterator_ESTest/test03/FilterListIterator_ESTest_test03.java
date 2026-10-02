package org.apache.commons.collections4.iterators;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.ListIterator;

import org.apache.commons.collections4.functors.DefaultEquator;
import org.apache.commons.collections4.functors.EqualPredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test03 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        final LinkedList<Object> source = new LinkedList<Object>();
        final Object elementInSource = new Object();
        source.push(elementInSource);

        final ListIterator<Object> sourceIterator = source.listIterator();
        final Object unmatchedElement = new Object();
        final DefaultEquator<Object> equator = DefaultEquator.defaultEquator();
        final EqualPredicate<Object> onlyUnmatchedElement = new EqualPredicate<Object>(unmatchedElement, equator);
        final FilterListIterator<Object> filteredIterator = new FilterListIterator<Object>(sourceIterator, onlyUnmatchedElement);

        final boolean hasAcceptedNextElement = filteredIterator.hasNext();

        assertTrue(sourceIterator.hasPrevious());
        assertFalse(hasAcceptedNextElement);
    }
}
