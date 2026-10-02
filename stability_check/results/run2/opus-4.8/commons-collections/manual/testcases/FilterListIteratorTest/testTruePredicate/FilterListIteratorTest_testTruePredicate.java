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
 * Verifies that a {@link FilterListIterator} configured with an always-true
 * predicate behaves exactly like the plain {@link ListIterator} of the
 * underlying list: it must let every element through and stay perfectly in
 * step during forward, backward, mixed and random traversals.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testTruePredicate {

    /** Number of random forward/backward steps used in the random walk. */
    private static final int RANDOM_WALK_STEPS = 500;

    /** The list under test: the integers 0..19. */
    private ArrayList<Integer> list;

    /** Predicate that accepts every element. */
    private Predicate<Integer> truePred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(Integer.valueOf(i));
        }
        truePred = x -> true;
    }

    @AfterEach
    public void tearDown() {
        list = null;
        truePred = null;
    }

    @Test
    void testTruePredicate() {
        // A true predicate should let the filtered iterator mirror the raw list iterator.
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(list.listIterator(), truePred);
        walkLists(list, filtered);
    }

    /**
     * Drives {@code testing} through the same traversals as a reference
     * iterator over {@code list}, asserting they agree at every step.
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // Walk all the way forward, then all the way back.
        walkForward(expected, testing);
        walkBackward(expected, testing);

        // Zig-zag forward: for each element step forward, back, then forward again.
        while (expected.hasNext()) {
            assertIndexesMatch(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }

        // Walk all the way back.
        walkBackward(expected, testing);

        // For increasing i, walk forward i, back i/2, forward i/2, back i.
        for (int i = 0; i < list.size(); i++) {
            stepForward(expected, testing, i);
            stepBackward(expected, testing, i / 2);
            stepForward(expected, testing, i / 2);
            stepBackward(expected, testing, i);
        }

        // Random walk: take random forward/backward steps and stay in sync.
        final StringBuilder walkDescription = new StringBuilder(RANDOM_WALK_STEPS);
        for (int i = 0; i < RANDOM_WALK_STEPS; i++) {
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

    /** Advances both iterators forward {@code count} times, asserting agreement. */
    private void stepForward(final ListIterator<?> expected, final ListIterator<?> testing, final int count) {
        for (int j = 0; j < count; j++) {
            assertIndexesMatch(expected, testing);
            // If this fails we've got a logic error in the test itself.
            assertTrue(expected.hasNext());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Advances both iterators backward {@code count} times, asserting agreement. */
    private void stepBackward(final ListIterator<?> expected, final ListIterator<?> testing, final int count) {
        for (int j = 0; j < count; j++) {
            assertIndexesMatch(expected, testing);
            // If this fails we've got a logic error in the test itself.
            assertTrue(expected.hasPrevious());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Walks both iterators forward until {@code expected} is exhausted. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertIndexesMatch(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /** Walks both iterators backward until {@code expected} is exhausted. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertIndexesMatch(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Asserts both iterators report the same next and previous indexes. */
    private void assertIndexesMatch(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }
}
