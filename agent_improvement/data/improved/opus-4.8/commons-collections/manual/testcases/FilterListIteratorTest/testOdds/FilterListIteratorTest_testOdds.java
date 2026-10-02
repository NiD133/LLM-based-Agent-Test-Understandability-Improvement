package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link FilterListIterator} configured with an "odd numbers"
 * predicate behaves exactly like a plain {@link ListIterator} over a list that
 * contains only the odd numbers.
 *
 * <p>The source list holds the integers {@code 0..19}. The filtered iterator
 * walks that list but is expected to yield only the odd values, so its behaviour
 * is compared element-by-element (and index-by-index) against a reference
 * iterator over a pre-built list of just the odd numbers.</p>
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testOdds {

    /** The full source list: 0, 1, 2, ... 19. */
    private ArrayList<Integer> list;

    /** The expected result of filtering {@link #list} for odd numbers. */
    private ArrayList<Integer> odds;

    /** Matches odd numbers only. */
    private Predicate<Integer> oddPred;

    /** Drives the random walk in {@link #walkLists}. */
    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        odds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
            if (i % 2 != 0) {
                odds.add(i);
            }
        }
        oddPred = x -> x % 2 != 0;
    }

    @Test
    void testOdds() {
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(list.listIterator(), oddPred);
        walkLists(odds, filtered);
    }

    /**
     * Exhaustively compares {@code testing} against a fresh reference iterator
     * over {@code expected} using forward, backward, mixed, and random walks.
     * At every step both the returned element and the reported indices must agree.
     */
    private <E> void walkLists(final List<E> expectedList, final ListIterator<E> testing) {
        final ListIterator<E> expected = expectedList.listIterator();

        // Walk all the way forward, then all the way back.
        walkForward(expected, testing);
        walkBackward(expected, testing);

        // Forward, back, forward across the whole list.
        while (expected.hasNext()) {
            assertIndicesMatch(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }

        // Walk all the way back.
        walkBackward(expected, testing);

        // For each i: forward i, back i/2, forward i/2, back i.
        for (int i = 0; i < expectedList.size(); i++) {
            stepForward(expected, testing, i);
            stepBackward(expected, testing, i / 2);
            stepForward(expected, testing, i / 2);
            stepBackward(expected, testing, i);
        }

        // Random walk: 500 random forward/backward steps, indices verified each step.
        final StringBuilder walkDescription = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                walkDescription.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkDescription.toString());
                }
            } else {
                walkDescription.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkDescription.toString());
                }
            }
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkDescription.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkDescription.toString());
        }
    }

    /** Walks both iterators forward to the end, checking every element and index. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertIndicesMatch(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Walks both iterators backward to the start, checking every element and index. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertIndicesMatch(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Advances both iterators {@code count} times via {@code next()}. */
    private void stepForward(final ListIterator<?> expected, final ListIterator<?> testing, final int count) {
        for (int j = 0; j < count; j++) {
            assertIndicesMatch(expected, testing);
            // If this fails we've got a logic error in the test itself.
            assertTrue(expected.hasNext());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Retreats both iterators {@code count} times via {@code previous()}. */
    private void stepBackward(final ListIterator<?> expected, final ListIterator<?> testing, final int count) {
        for (int j = 0; j < count; j++) {
            assertIndicesMatch(expected, testing);
            // If this fails we've got a logic error in the test itself.
            assertTrue(expected.hasPrevious());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Asserts the two iterators agree on both {@code nextIndex()} and {@code previousIndex()}. */
    private void assertIndicesMatch(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }
}
