package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link FilterListIterator} keeps {@code next()} and
 * {@code previous()} consistent: calling {@code next()} must change the value
 * that a subsequent {@code previous()} returns, even after {@code hasPrevious()}
 * has already been queried.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testNextChangesPrevious {

    /** Predicate that accepts every element. */
    private static final Predicate<Integer> ACCEPT_ALL = x -> true;

    /** Predicate that accepts only multiples of three. */
    private static final Predicate<Integer> MULTIPLES_OF_THREE = x -> x % 3 == 0;

    /** The full source list: 0, 1, 2, ... 19. */
    private List<Integer> list;

    /** The elements of {@link #list} that are multiples of three: 0, 3, 6, ... 18. */
    private List<Integer> multiplesOfThree;

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        multiplesOfThree = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
            if (i % 3 == 0) {
                multiplesOfThree.add(i);
            }
        }
    }

    /**
     * Walks both iterators through the sequence {@code next, next, previous} and
     * asserts the filtered iterator returns exactly the same values as the
     * reference iterator at each step. The key behaviour exercised is that the
     * second {@code next()} (issued after {@code hasPrevious()}) advances the
     * cursor so that {@code previous()} returns the element just produced.
     *
     * @param expected the reference iterator over the elements that should pass the filter
     * @param testing  the {@link FilterListIterator} under test
     */
    private void assertNextThenPreviousAgree(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.next(), testing.next());
        assertEquals(expected.hasPrevious(), testing.hasPrevious());

        final Object expectedNext = expected.next();
        final Object testingNext = testing.next();
        assertEquals(expectedNext, testingNext);

        final Object expectedPrevious = expected.previous();
        final Object testingPrevious = testing.previous();
        // previous() must return the element that next() just returned
        assertEquals(expectedNext, expectedPrevious);
        assertEquals(testingNext, testingPrevious);
    }

    @Test
    void testNextChangesPrevious() {
        // Filtering to multiples of three should behave like iterating multiplesOfThree directly.
        final FilterListIterator<Integer> filteredToThrees =
                new FilterListIterator<>(list.listIterator(), MULTIPLES_OF_THREE);
        assertNextThenPreviousAgree(multiplesOfThree.listIterator(), filteredToThrees);

        // Accepting every element should behave like iterating the full list directly.
        final FilterListIterator<Integer> filteredToAll =
                new FilterListIterator<>(list.listIterator(), ACCEPT_ALL);
        assertNextThenPreviousAgree(list.listIterator(), filteredToAll);
    }
}
