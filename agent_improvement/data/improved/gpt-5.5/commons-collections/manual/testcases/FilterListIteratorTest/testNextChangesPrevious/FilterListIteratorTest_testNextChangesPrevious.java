package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class FilterListIteratorTest_testNextChangesPrevious {

    private List<Integer> allValues;
    private List<Integer> multiplesOfThree;

    private Predicate<Integer> acceptAll;
    private Predicate<Integer> acceptMultiplesOfThree;

    @BeforeEach
    public void setUp() {
        allValues = new ArrayList<>();
        multiplesOfThree = new ArrayList<>();

        for (int value = 0; value < 20; value++) {
            allValues.add(Integer.valueOf(value));
            if (value % 3 == 0) {
                multiplesOfThree.add(Integer.valueOf(value));
            }
        }

        acceptAll = value -> true;
        acceptMultiplesOfThree = value -> value % 3 == 0;
    }

    @Test
    void testNextChangesPrevious() {
        assertNextRefreshesCachedPrevious(multiplesOfThree.listIterator(),
                new FilterListIterator<>(allValues.listIterator(), acceptMultiplesOfThree));

        assertNextRefreshesCachedPrevious(allValues.listIterator(),
                new FilterListIterator<>(allValues.listIterator(), acceptAll));
    }

    private void assertNextRefreshesCachedPrevious(final ListIterator<?> expected,
            final ListIterator<?> filtered) {
        assertEquals(expected.next(), filtered.next());
        assertEquals(expected.hasPrevious(), filtered.hasPrevious());

        final Object expectedValueAfterSecondNext = expected.next();
        final Object filteredValueAfterSecondNext = filtered.next();
        assertEquals(expectedValueAfterSecondNext, filteredValueAfterSecondNext);

        final Object expectedPreviousAfterSecondNext = expected.previous();
        final Object filteredPreviousAfterSecondNext = filtered.previous();
        assertEquals(expectedValueAfterSecondNext, expectedPreviousAfterSecondNext);
        assertEquals(filteredValueAfterSecondNext, filteredPreviousAfterSecondNext);
    }
}
