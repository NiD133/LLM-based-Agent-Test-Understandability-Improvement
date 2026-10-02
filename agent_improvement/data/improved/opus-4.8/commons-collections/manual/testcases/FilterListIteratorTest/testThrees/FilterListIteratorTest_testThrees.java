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
 * Verifies that a {@link FilterListIterator} filtering the numbers 0..19 down to
 * the multiples of three behaves exactly like a plain {@link ListIterator} over
 * the list of those multiples, no matter how it is walked.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testThrees {

    /** The source numbers 0, 1, 2, ... 19 that the filter iterates over. */
    private ArrayList<Integer> list;

    /** The expected filtered result: every multiple of three in {@link #list}. */
    private ArrayList<Integer> threes;

    /** The filter predicate: keeps only multiples of three. */
    private Predicate<Integer> threePred;

    /** Drives the random-walk portion of {@link #walkLists}. */
    private final Random random = new Random();

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
        threePred = x -> x % 3 == 0;
    }

    @Test
    void testThrees() {
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(list.listIterator(), threePred);
        walkLists(threes, filtered);
    }

    /**
     * Exhaustively walks {@code testing} alongside a reference iterator over
     * {@code expectedList}, asserting that both stay in lock-step for forward,
     * backward, mixed, and random traversals.
     */
    private <E> void walkLists(final List<E> expectedList, final ListIterator<E> testing) {
        final ListIterator<E> expected = expectedList.listIterator();

        // Walk all the way forward, then all the way back.
        walkForward(expected, testing);
        walkBackward(expected, testing);

        // Forward, back, forward across the whole list.
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
        walkBackward(expected, testing);

        // For each i: walk forward i, back i/2, forward i/2, back i.
        for (int i = 0; i < expectedList.size(); i++) {
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext());
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious());
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext());
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious());
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
        }

        // Random walk: step forward or backward 500 times, recording the path
        // so any mismatch reports the exact sequence that caused it.
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

    /** Steps both iterators forward to the end, asserting they agree at every position. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Steps both iterators back to the start, asserting they agree at every position. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }
}
