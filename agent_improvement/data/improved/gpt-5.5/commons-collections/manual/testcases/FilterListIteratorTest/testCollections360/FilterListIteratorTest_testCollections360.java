package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Collection;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.list.GrowthList;
import org.junit.jupiter.api.Test;

public class FilterListIteratorTest_testCollections360 {

    /**
     * Test for https://issues.apache.org/jira/browse/COLLECTIONS-360.
     */
    @Test
    void testCollections360() throws Throwable {
        final Collection<Predicate<Object>> predicates = new GrowthList<>();
        final Predicate<Object> anyPredicate = PredicateUtils.anyPredicate(predicates);

        final FilterListIterator<Object> iteratorForNextCheck = new FilterListIterator<>(anyPredicate);
        assertFalse(iteratorForNextCheck.hasNext());

        final FilterListIterator<Object> iteratorForPreviousCheck = new FilterListIterator<>(anyPredicate);
        assertFalse(iteratorForPreviousCheck.hasPrevious());
    }
}
