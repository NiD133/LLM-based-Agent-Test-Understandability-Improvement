package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link FilterListIterator} keeps {@code next()} and {@code previous()}
 * in sync the same way a plain {@link ListIterator} does.
 *
 * <p>Each scenario compares the filtered iterator (the object under test) against a
 * reference {@link ListIterator} over the list of values we expect the filter to let
 * through. If the two iterators ever disagree, the filter logic is wrong.</p>
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testPreviousChangesNext {

    /** Source values 0..19 that the filter iterates over. */
    private ArrayList<Integer> list;

    /** The subset of {@link #list} divisible by three: the expected output of {@link #divisibleByThree}. */
    private ArrayList<Integer> threes;

    /** Matches every element, so the filter passes the whole list through unchanged. */
    private Predicate<Integer> acceptAll;

    /** Matches only multiples of three. */
    private Predicate<Integer> divisibleByThree;

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        threes = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
            if (i % 3 == 0) {
                threes.add(i);
            }
        }
        acceptAll = x -> true;
        divisibleByThree = x -> x % 3 == 0;
    }

    /**
     * Verifies that calling {@code previous()} changes the value later returned by
     * {@code next()}, even when {@code next()} was already primed by a {@code hasNext()}
     * call. The reference iterator drives the expected values.
     */
    private void previousPreviousNext(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.previous(), testing.previous());
        assertEquals(expected.hasNext(), testing.hasNext());

        final Object expectedPrevious = expected.previous();
        final Object testingPrevious = testing.previous();
        assertEquals(expectedPrevious, testingPrevious);

        final Object expectedNext = expected.next();
        final Object testingNext = testing.next();
        // next() must return the element we just stepped back over, for both iterators.
        assertEquals(expectedPrevious, testingNext);
        assertEquals(expectedPrevious, expectedNext);
        assertEquals(testingPrevious, testingNext);
    }

    /** Walks both iterators forward to the end, asserting they stay in lock-step. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Runs one scenario: filter {@code list} with {@code predicate} and compare against {@code expectedValues}. */
    private void assertPreviousChangesNext(final Predicate<Integer> predicate, final List<Integer> expectedValues) {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), predicate);
        final ListIterator<Integer> expected = expectedValues.listIterator();
        walkForward(expected, filtered);
        previousPreviousNext(expected, filtered);
    }

    @Test
    void testPreviousChangesNext() {
        // Filtering to multiples of three: expected output is the "threes" list.
        assertPreviousChangesNext(divisibleByThree, threes);

        // Filtering with an accept-all predicate: expected output is the full list.
        assertPreviousChangesNext(acceptAll, list);
    }
}
