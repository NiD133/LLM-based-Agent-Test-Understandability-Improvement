package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link FilterListIterator} keeping only multiples of four
 * behaves exactly like a plain {@link ListIterator} over the pre-filtered list
 * of those multiples, no matter how the caller walks back and forth.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testFours {

    /** Source list 0, 1, 2, ... , 19 that the filtered iterator decorates. */
    private ArrayList<Integer> sourceNumbers;

    /** The expected result of filtering {@link #sourceNumbers}: 0, 4, 8, 12, 16. */
    private ArrayList<Integer> multiplesOfFour;

    /** Predicate that keeps only multiples of four. */
    private Predicate<Integer> divisibleByFour;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        sourceNumbers = new ArrayList<>();
        multiplesOfFour = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            sourceNumbers.add(i);
            if (i % 4 == 0) {
                multiplesOfFour.add(i);
            }
        }
        divisibleByFour = x -> x % 4 == 0;
    }

    @AfterEach
    public void tearDown() {
        sourceNumbers = null;
        multiplesOfFour = null;
        divisibleByFour = null;
    }

    @Test
    void testFours() {
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(sourceNumbers.listIterator(), divisibleByFour);
        // The filtered view of sourceNumbers must match a direct iterator over multiplesOfFour.
        assertSameTraversal(multiplesOfFour, filtered);
    }

    /**
     * Drives {@code actual} through a series of traversal patterns and checks at
     * every step that it agrees with a fresh {@link ListIterator} over
     * {@code reference} (the known-correct sequence).
     */
    private <E> void assertSameTraversal(final List<E> reference, final ListIterator<E> actual) {
        final ListIterator<E> expected = reference.listIterator();

        walkForward(expected, actual);
        walkBackward(expected, actual);

        // forward, back, forward across the whole sequence
        while (expected.hasNext()) {
            assertPositions(expected, actual);
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
            assertTrue(actual.hasPrevious());
            assertEquals(expected.previous(), actual.previous());
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
        }
        walkBackward(expected, actual);

        // For each i: forward i, back i/2, forward i/2, back i.
        for (int i = 0; i < reference.size(); i++) {
            stepForward(expected, actual, i);
            stepBackward(expected, actual, i / 2);
            stepForward(expected, actual, i / 2);
            stepBackward(expected, actual, i);
        }

        randomWalk(expected, actual);
    }

    /** Walks both iterators all the way to the end, asserting agreement at each step. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasNext()) {
            assertPositions(expected, actual);
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
        }
    }

    /** Walks both iterators all the way back to the start, asserting agreement at each step. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasPrevious()) {
            assertPositions(expected, actual);
            assertTrue(actual.hasPrevious());
            assertEquals(expected.previous(), actual.previous());
        }
    }

    /** Advances both iterators forward {@code steps} times, asserting agreement at each step. */
    private void stepForward(final ListIterator<?> expected, final ListIterator<?> actual, final int steps) {
        for (int j = 0; j < steps; j++) {
            assertPositions(expected, actual);
            // If these fail the test itself has a logic error, not the iterator.
            assertTrue(expected.hasNext());
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
        }
    }

    /** Moves both iterators backward {@code steps} times, asserting agreement at each step. */
    private void stepBackward(final ListIterator<?> expected, final ListIterator<?> actual, final int steps) {
        for (int j = 0; j < steps; j++) {
            assertPositions(expected, actual);
            // If these fail the test itself has a logic error, not the iterator.
            assertTrue(expected.hasPrevious());
            assertTrue(actual.hasPrevious());
            assertEquals(expected.previous(), actual.previous());
        }
    }

    /** Takes 500 random forward/backward steps, asserting agreement after each one. */
    private void randomWalk(final ListIterator<?> expected, final ListIterator<?> actual) {
        final StringBuilder walkDescription = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                walkDescription.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), actual.next(), walkDescription.toString());
                }
            } else {
                walkDescription.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), actual.previous(), walkDescription.toString());
                }
            }
            assertEquals(expected.nextIndex(), actual.nextIndex(), walkDescription.toString());
            assertEquals(expected.previousIndex(), actual.previousIndex(), walkDescription.toString());
        }
    }

    /** Asserts the two iterators report the same next/previous index. */
    private void assertPositions(final ListIterator<?> expected, final ListIterator<?> actual) {
        assertEquals(expected.nextIndex(), actual.nextIndex());
        assertEquals(expected.previousIndex(), actual.previousIndex());
    }
}
