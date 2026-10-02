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
public class FilterListIterator_ESTest_test04 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        LinkedList<Object> singleElementList = new LinkedList<Object>();
        Object targetElement = new Object();
        singleElementList.push(targetElement);
        ListIterator<Object> sharedListIterator = singleElementList.listIterator();

        EqualPredicate<Object> matchesTarget = new EqualPredicate<Object>(targetElement, DefaultEquator.defaultEquator());

        FilterListIterator<Object> firstFilterIterator = new FilterListIterator<Object>(sharedListIterator, matchesTarget);
        firstFilterIterator.next();
        // hasPrevious() scans backward through sharedListIterator, repositioning it
        firstFilterIterator.hasPrevious();

        // secondFilterIterator shares sharedListIterator, which was repositioned above
        FilterListIterator<Object> secondFilterIterator = new FilterListIterator<Object>(sharedListIterator, matchesTarget);
        secondFilterIterator.next(); // advances sharedListIterator to the end

        // firstFilterIterator can no longer find a next element because sharedListIterator is exhausted
        boolean firstHasNext = firstFilterIterator.hasNext();
        assertEquals(1, firstFilterIterator.nextIndex());
        assertFalse(firstHasNext);
    }
}
