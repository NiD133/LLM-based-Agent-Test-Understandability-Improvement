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
public class FilterListIterator_ESTest_test07 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        LinkedList<Object> source = new LinkedList<Object>();
        Object matchingElement = new Object();
        source.push(matchingElement);

        ListIterator<Object> sourceIterator = source.listIterator();
        DefaultEquator<Object> defaultEquator = DefaultEquator.defaultEquator();
        EqualPredicate<Object> matchesElement = new EqualPredicate<Object>(matchingElement, defaultEquator);
        FilterListIterator<Object> filteredIterator = new FilterListIterator<Object>(sourceIterator, matchesElement);

        filteredIterator.next();
        filteredIterator.hasPrevious();
        boolean hasPreviousAfterNext = filteredIterator.hasPrevious();

        assertEquals(0, filteredIterator.previousIndex());
        assertTrue(hasPreviousAfterNext);
    }
}
