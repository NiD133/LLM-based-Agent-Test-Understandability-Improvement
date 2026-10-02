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
 * Verifies that a {@link FilterListIterator} configured with an "always true"
 * predicate behaves exactly like the plain {@link ListIterator} of the backing
 * list: since every element matches, filtering must be transparent.
 *
 * <p>The check is performed by {@link #walkLists}, which drives both the
 * reference iterator and the filtered iterator through the same sequence of
 * forward/backward moves and asserts they stay in lockstep for every value and
 * index.</p>
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testTruePredicate {

    /** Number of elements (0..19) placed in the backing list. */
    private static final int LIST_SIZE = 20;

    /** Number of random forward/backward steps in the final stress walk. */
    private static final int RANDOM_WALK_STEPS = 500;

    /** The list under iteration: the integers 0..LIST_SIZE-1. */
    private ArrayList<Integer> list;

    /** Predicate that accepts every element, so no element is filtered out. */
    private Predicate<Integer> truePred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            list.add(Integer.valueOf(i));
        }
        truePred = x -> true;
    }

    @Test
    void testTruePredicate() {
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(list.listIterator(), truePred);
        walkLists(list, filtered);
    }

    /**
     * Drives {@code testing} through the exact same traversal as a fresh
     * iterator over {@code list} (the {@code expected} reference), asserting
     * they agree on every value and on {@code nextIndex}/{@code previousIndex}.
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // 1. Walk all the way forward, then all the way back.
        walkForward(expected, testing);
        walkBackward(expected, testing);

        // 2. Zig-zag forward: for each element step forward, back, then forward again.
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

        // 3. For each i, walk forward i, back i/2, forward i/2, back i.
        for (int i = 0; i < list.size(); i++) {
            stepForward(expected, testing, i);
            stepBackward(expected, testing, i / 2);
            stepForward(expected, testing, i / 2);
            stepBackward(expected, testing, i);
        }

        // 4. Random walk: take random forward/backward steps and stay in sync.
        randomWalk(expected, testing);
    }

    /** Walks both iterators forward to the end, checking values and indices. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertIndicesMatch(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Walks both iterators backward to the start, checking values and indices. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertIndicesMatch(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Advances both iterators forward {@code count} times, checking each step. */
    private void stepForward(final ListIterator<?> expected, final ListIterator<?> testing, final int count) {
        for (int j = 0; j < count; j++) {
            assertIndicesMatch(expected, testing);
            // If this fails we've got a logic error in the test itself.
            assertTrue(expected.hasNext());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Advances both iterators backward {@code count} times, checking each step. */
    private void stepBackward(final ListIterator<?> expected, final ListIterator<?> testing, final int count) {
        for (int j = 0; j < count; j++) {
            assertIndicesMatch(expected, testing);
            // If this fails we've got a logic error in the test itself.
            assertTrue(expected.hasPrevious());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /**
     * Takes {@link #RANDOM_WALK_STEPS} random forward/backward steps, moving
     * only when the reference iterator can. The accumulated step description is
     * attached to each assertion so a failure can be reproduced.
     */
    private void randomWalk(final ListIterator<?> expected, final ListIterator<?> testing) {
        final StringBuilder walkDescr = new StringBuilder(RANDOM_WALK_STEPS);
        for (int i = 0; i < RANDOM_WALK_STEPS; i++) {
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

    /** Asserts both iterators report the same next and previous index. */
    private void assertIndicesMatch(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }
}
