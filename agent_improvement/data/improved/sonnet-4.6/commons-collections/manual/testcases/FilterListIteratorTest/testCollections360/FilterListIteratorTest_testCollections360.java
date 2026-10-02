package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Collection;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.list.GrowthList;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class FilterListIteratorTest_testCollections360 {

    /**
     * Regression test for COLLECTIONS-360: a FilterListIterator constructed
     * with only a predicate (no backing list iterator) must report both
     * hasNext() and hasPrevious() as false rather than throwing a NullPointerException.
     */
    @Test
    void testCollections360() throws Throwable {
        final Collection<Predicate<Object>> emptyPredicates = new GrowthList<>();
        final Predicate<Object> neverMatchingPredicate = PredicateUtils.anyPredicate(emptyPredicates);

        final FilterListIterator<Object> iteratorUnderHasNext = new FilterListIterator<>(neverMatchingPredicate);
        assertFalse(iteratorUnderHasNext.hasNext());

        final FilterListIterator<Object> iteratorUnderHasPrevious = new FilterListIterator<>(neverMatchingPredicate);
        assertFalse(iteratorUnderHasPrevious.hasPrevious());
    }
}
