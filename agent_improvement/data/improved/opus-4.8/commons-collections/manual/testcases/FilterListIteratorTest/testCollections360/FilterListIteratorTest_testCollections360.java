package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Collection;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.list.GrowthList;
import org.junit.jupiter.api.Test;

/**
 * Regression test for a {@link FilterListIterator} that has a predicate but no
 * backing {@link java.util.ListIterator ListIterator}.
 *
 * <p>See <a href="https://issues.apache.org/jira/browse/COLLECTIONS-360">
 * COLLECTIONS-360</a>: when no underlying iterator has been set, both
 * {@code hasNext()} and {@code hasPrevious()} must report that there are no
 * elements rather than throwing a {@code NullPointerException}.</p>
 */
public class FilterListIteratorTest_testCollections360 {

    @Test
    void testCollections360() throws Throwable {
        // A predicate that never matches because it is built over an empty
        // collection of predicates (anyPredicate of nothing is always false).
        final Collection<Predicate<Object>> emptyPredicates = new GrowthList<>();
        final Predicate<Object> matchNone = PredicateUtils.anyPredicate(emptyPredicates);

        // The iterator is constructed with a predicate only, so its underlying
        // ListIterator stays null. Navigation queries must still be safe.
        assertFalse(new FilterListIterator<>(matchNone).hasNext());
        assertFalse(new FilterListIterator<>(matchNone).hasPrevious());
    }
}
