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
 * Verifies that a {@link FilterListIterator} behaves like a plain
 * {@link ListIterator} over the elements it is expected to yield.
 *
 * <p>In this scenario the filtered iterator decorates a list of 0..19, and the
 * <em>expected</em> view it is compared against is empty. Because every walk
 * helper is driven by the expected iterator, an empty expectation means no
 * element is ever pulled from the iterator under test - the assertions only
 * confirm that the filtered iterator agrees with "no elements".</p>
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testFalsePredicate {

    /** Source list [0, 1, ... 19] that the filtered iterator decorates. */
    private ArrayList<Integer> list;

    /**
     * Predicate handed to the iterator under test.
     *
     * <p>Note: despite its name this predicate always returns {@code true}.
     * This quirk is preserved from the original test; it is harmless here
     * because the iterator is never advanced (see the class comment).</p>
     */
    private Predicate<Integer> falsePred;

    /** Drives the random walk in {@link #walkLists}. */
    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(Integer.valueOf(i));
        }
        falsePred = x -> true;
    }

    @Test
    void testFalsePredicate() {
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(list.listIterator(), falsePred);
        // Compare against an empty expected view: the filtered iterator must
        // expose no elements to match it.
        walkLists(new ArrayList<>(), filtered);
    }

    /**
     * Walks {@code testing} in lock-step with a fresh iterator over
     * {@code expected}, asserting that both agree at every position and in
     * every direction (forward, backward, mixed and random).
     */
    private <E> void walkLists(final List<E> expectedList, final ListIterator<E> testing) {
        final ListIterator<E> expected = expectedList.listIterator();

        // Walk all the way forward, then all the way back.
        walkForward(expected, testing);
        walkBackward(expected, testing);

        // forward, back, forward
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

        // For each i, walk forward i, back i/2, forward i/2, then back i.
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

        // Random walk: step forward or backward 500 times, checking indices.
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

    /** Walks both iterators forward to the end, asserting agreement at each step. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Walks both iterators backward to the start, asserting agreement at each step. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }
}
