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
 * Verifies that nesting two {@link FilterListIterator}s composes their predicates.
 * <p>
 * The inner iterator keeps the even numbers of {@code 0..19} and the outer
 * iterator keeps the multiples of three from that filtered view. The result is
 * the set of multiples of six, so the nested iterator must behave exactly like a
 * {@link ListIterator} over {@code [0, 6, 12, 18]}.
 * </p>
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testNestedSixes2 {

    /** Highest value (exclusive) placed into the source list. */
    private static final int LIMIT = 20;

    /** The source list: every integer in {@code 0..19}. */
    private ArrayList<Integer> list;

    /** The expected elements of the nested iterator: every multiple of six in {@code 0..19}. */
    private ArrayList<Integer> sixes;

    /** Keeps even numbers; used by the inner iterator. */
    private Predicate<Integer> evenPred;

    /** Keeps multiples of three; used by the outer iterator. */
    private Predicate<Integer> threePred;

    /** Drives the unseeded random walk; matches the original test's behaviour. */
    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        sixes = new ArrayList<>();
        for (int i = 0; i < LIMIT; i++) {
            list.add(i);
            if (i % 6 == 0) {
                sixes.add(i);
            }
        }
        evenPred = x -> x % 2 == 0;
        threePred = x -> x % 3 == 0;
    }

    @Test
    void testNestedSixes2() {
        // Filter evens first, then multiples of three -> multiples of six.
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(new FilterListIterator<>(list.listIterator(), evenPred), threePred);
        assertSameTraversal(sixes, filtered);
    }

    /**
     * Asserts that {@code testing} traverses identically to a fresh iterator over
     * {@code expectedElements}, exercising forward walks, backward walks, mixed
     * direction changes, and an unseeded random walk.
     */
    private <E> void assertSameTraversal(final List<E> expectedElements, final ListIterator<E> testing) {
        final ListIterator<E> expected = expectedElements.listIterator();

        // Walk all the way forward, then all the way back.
        walkForward(expected, testing);
        walkBackward(expected, testing);

        // forward, back, forward at each position.
        while (expected.hasNext()) {
            assertIndicesMatch(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
        walkBackward(expected, testing);

        // For each prefix length i: forward i, back i/2, forward i/2, back i.
        for (int i = 0; i < expectedElements.size(); i++) {
            stepForward(expected, testing, i);
            stepBackward(expected, testing, i / 2);
            stepForward(expected, testing, i / 2);
            stepBackward(expected, testing, i);
        }

        randomWalk(expected, testing);
    }

    /** Walks forward until {@code expected} is exhausted, asserting equality at each step. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertIndicesMatch(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Walks backward until {@code expected} is exhausted, asserting equality at each step. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertIndicesMatch(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Advances both iterators {@code count} times, asserting equality at each step. */
    private void stepForward(final ListIterator<?> expected, final ListIterator<?> testing, final int count) {
        for (int j = 0; j < count; j++) {
            assertIndicesMatch(expected, testing);
            // If these fail, the test logic itself is wrong.
            assertTrue(expected.hasNext());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Rewinds both iterators {@code count} times, asserting equality at each step. */
    private void stepBackward(final ListIterator<?> expected, final ListIterator<?> testing, final int count) {
        for (int j = 0; j < count; j++) {
            assertIndicesMatch(expected, testing);
            // If these fail, the test logic itself is wrong.
            assertTrue(expected.hasPrevious());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Takes 500 random forward/backward steps, asserting equality and indices throughout. */
    private void randomWalk(final ListIterator<?> expected, final ListIterator<?> testing) {
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

    /** Asserts both iterators agree on {@code nextIndex()} and {@code previousIndex()}. */
    private void assertIndicesMatch(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }
}
