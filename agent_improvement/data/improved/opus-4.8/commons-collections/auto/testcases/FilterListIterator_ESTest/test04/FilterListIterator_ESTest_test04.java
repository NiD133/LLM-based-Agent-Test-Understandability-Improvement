package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
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

    /**
     * Two FilterListIterators share the SAME underlying ListIterator. The first
     * iterator advances the shared cursor past the only element, so once the
     * second iterator also consumes that element the first one can no longer
     * find a further match and reports that it has no next element.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // A list holding a single element that the predicate will accept.
        Object onlyElement = new Object();
        LinkedList<Object> list = new LinkedList<Object>();
        list.push(onlyElement);

        // Both filter iterators wrap this one shared cursor over the list.
        ListIterator<Object> sharedCursor = list.listIterator();

        // Predicate that only matches the single element in the list.
        EqualPredicate<Object> matchesOnlyElement =
                new EqualPredicate<Object>(onlyElement, DefaultEquator.<Object>defaultEquator());

        // First iterator consumes the matching element, advancing the shared cursor.
        FilterListIterator<Object> firstIterator =
                new FilterListIterator<Object>(sharedCursor, matchesOnlyElement);
        firstIterator.next();
        firstIterator.hasPrevious();

        // Second iterator (over the same exhausted cursor) consumes the element too.
        FilterListIterator<Object> secondIterator =
                new FilterListIterator<Object>(sharedCursor, matchesOnlyElement);
        secondIterator.next();

        // The first iterator can no longer find a next match.
        boolean firstHasNext = firstIterator.hasNext();

        assertEquals(1, firstIterator.nextIndex());
        assertFalse(firstHasNext);
    }
}
