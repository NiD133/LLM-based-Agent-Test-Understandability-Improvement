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

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        LinkedList<Object> values = new LinkedList<Object>();
        Object matchingValue = new Object();
        values.push(matchingValue);

        ListIterator<Object> backingIterator = values.listIterator();
        DefaultEquator<Object> equator = DefaultEquator.defaultEquator();
        EqualPredicate<Object> matchesInsertedValue = new EqualPredicate<Object>(matchingValue, equator);
        FilterListIterator<Object> filteredIterator =
                new FilterListIterator<Object>(backingIterator, matchesInsertedValue);

        filteredIterator.hasNext();
        boolean secondHasNextResult = filteredIterator.hasNext();

        assertFalse(backingIterator.hasNext());
        assertTrue(secondHasNextResult);
    }
}
