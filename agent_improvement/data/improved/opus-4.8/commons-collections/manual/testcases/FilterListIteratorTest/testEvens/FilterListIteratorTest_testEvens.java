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
 * Verifies that a {@link FilterListIterator} configured with an "is even"
 * predicate behaves, in every direction of traversal, exactly like a plain
 * {@link ListIterator} over the pre-filtered list of even numbers.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testEvens {

    /** Number of source elements (0..SIZE-1) used to build the test data. */
    private static final int SIZE = 20;

    /** The full source list: 0, 1, 2, ..., SIZE-1. */
    private ArrayList<Integer> list;

    /** The expected result of filtering {@link #list} for even values. */
    private ArrayList<Integer> evens;

    /** Predicate matching even integers. */
    private Predicate<Integer> evenPred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        evens = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            list.add(i);
            if (i % 2 == 0) {
                evens.add(i);
            }
        }
        evenPred = x -> x % 2 == 0;
    }

    @Test
    void testEvens() {
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(list.listIterator(), evenPred);
        // The filtered iterator must mirror a direct iterator over the evens.
        walkLists(evens, filtered);
    }

    /**
     * Drives {@code testing} through the same traversal as a reference iterator
     * over {@code expected}, asserting that both iterators stay in lock-step for
     * values, {@code nextIndex()} and {@code previousIndex()}.
     */
    private <E> void walkLists(final List<E> expectedList, final ListIterator<E> testing) {
        final ListIterator<E> expected = expectedList.listIterator();

        // 1. Walk all the way forward, then all the way back.
        walkForward(expected, testing);
        walkBackward(expected, testing);

        // 2. Zig-zag forward: each step advances, retreats, then advances again.
        while (expected.hasNext()) {
            assertIndexesMatch(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
        walkBackward(expected, testing);

        // 3. For each i, walk forward i, back i/2, forward i/2, back i.
        for (int i = 0; i < expectedList.size(); i++) {
            stepForward(expected, testing, i);
            stepBackward(expected, testing, i / 2);
            stepForward(expected, testing, i / 2);
            stepBackward(expected, testing, i);
        }

        // 4. Random walk: take 500 random forward/backward steps.
        final StringBuilder walkDescr = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                walkDescr.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkDescr.toString());
                }
            } else {
                walkDescr.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkDescr.toString());
                }
            }
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkDescr.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkDescr.toString());
        }
    }

    /** Advances both iterators forward until {@code expected} is exhausted. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertIndexesMatch(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Rewinds both iterators backward until {@code expected} is exhausted. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertIndexesMatch(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Takes exactly {@code count} forward steps on both iterators. */
    private void stepForward(final ListIterator<?> expected, final ListIterator<?> testing,
            final int count) {
        for (int j = 0; j < count; j++) {
            assertIndexesMatch(expected, testing);
            // A failure here would mean the test itself walked off the end.
            assertTrue(expected.hasNext());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Takes exactly {@code count} backward steps on both iterators. */
    private void stepBackward(final ListIterator<?> expected, final ListIterator<?> testing,
            final int count) {
        for (int j = 0; j < count; j++) {
            assertIndexesMatch(expected, testing);
            // A failure here would mean the test itself walked off the front.
            assertTrue(expected.hasPrevious());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Asserts that both iterators report the same next/previous index. */
    private void assertIndexesMatch(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }
}
