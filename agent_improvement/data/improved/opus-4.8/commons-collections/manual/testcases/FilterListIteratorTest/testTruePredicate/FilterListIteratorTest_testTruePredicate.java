package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link FilterListIterator} using an always-true predicate
 * behaves exactly like the {@link ListIterator} of the underlying list:
 * filtering nothing out, it must mirror every forward/backward step and every
 * index reported by a plain list iterator.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testTruePredicate {

    /** Source list holding the integers 0..19. */
    private static final List<Integer> NUMBERS_0_TO_19 = buildNumbers();

    /** Predicate that accepts every element, so the filter passes everything through. */
    private static final Predicate<Integer> ACCEPT_ALL = x -> true;

    private final Random random = new Random();

    private static List<Integer> buildNumbers() {
        final List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            numbers.add(i);
        }
        return numbers;
    }

    /**
     * Steps both iterators forward to the end, asserting they agree on every
     * element and on both index accessors at each position.
     */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> filtered) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), filtered.nextIndex());
            assertEquals(expected.previousIndex(), filtered.previousIndex());
            assertTrue(filtered.hasNext());
            assertEquals(expected.next(), filtered.next());
        }
    }

    /**
     * Steps both iterators backward to the start, asserting they agree on every
     * element and on both index accessors at each position.
     */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> filtered) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(), filtered.nextIndex());
            assertEquals(expected.previousIndex(), filtered.previousIndex());
            assertTrue(filtered.hasPrevious());
            assertEquals(expected.previous(), filtered.previous());
        }
    }

    /**
     * Exercises {@code filtered} against a reference iterator over {@code source}
     * using a battery of traversal patterns, asserting equivalence throughout.
     */
    private <E> void walkLists(final List<E> source, final ListIterator<E> filtered) {
        final ListIterator<E> expected = source.listIterator();

        // walk all the way forward, then all the way back
        walkForward(expected, filtered);
        walkBackward(expected, filtered);

        // forward, back, forward at each position
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), filtered.nextIndex());
            assertEquals(expected.previousIndex(), filtered.previousIndex());
            assertTrue(filtered.hasNext());
            assertEquals(expected.next(), filtered.next());
            assertTrue(filtered.hasPrevious());
            assertEquals(expected.previous(), filtered.previous());
            assertTrue(filtered.hasNext());
            assertEquals(expected.next(), filtered.next());
        }
        walkBackward(expected, filtered);

        // for each i: forward i, back i/2, forward i/2, back i
        for (int i = 0; i < source.size(); i++) {
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), filtered.nextIndex());
                assertEquals(expected.previousIndex(), filtered.previousIndex());
                // if this one fails we've got a logic error in the test
                assertTrue(expected.hasNext());
                assertTrue(filtered.hasNext());
                assertEquals(expected.next(), filtered.next());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), filtered.nextIndex());
                assertEquals(expected.previousIndex(), filtered.previousIndex());
                // if this one fails we've got a logic error in the test
                assertTrue(expected.hasPrevious());
                assertTrue(filtered.hasPrevious());
                assertEquals(expected.previous(), filtered.previous());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), filtered.nextIndex());
                assertEquals(expected.previousIndex(), filtered.previousIndex());
                // if this one fails we've got a logic error in the test
                assertTrue(expected.hasNext());
                assertTrue(filtered.hasNext());
                assertEquals(expected.next(), filtered.next());
            }
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), filtered.nextIndex());
                assertEquals(expected.previousIndex(), filtered.previousIndex());
                // if this one fails we've got a logic error in the test
                assertTrue(expected.hasPrevious());
                assertTrue(filtered.hasPrevious());
                assertEquals(expected.previous(), filtered.previous());
            }
        }

        // random walk: take 500 random forward/backward steps, recording the
        // sequence so a failure message can reproduce the path taken
        final StringBuilder walkDescription = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                walkDescription.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), filtered.next(), walkDescription.toString());
                }
            } else {
                walkDescription.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), filtered.previous(), walkDescription.toString());
                }
            }
            assertEquals(expected.nextIndex(), filtered.nextIndex(), walkDescription.toString());
            assertEquals(expected.previousIndex(), filtered.previousIndex(), walkDescription.toString());
        }
    }

    @Test
    void testTruePredicate() {
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(NUMBERS_0_TO_19.listIterator(), ACCEPT_ALL);
        walkLists(NUMBERS_0_TO_19, filtered);
    }
}
