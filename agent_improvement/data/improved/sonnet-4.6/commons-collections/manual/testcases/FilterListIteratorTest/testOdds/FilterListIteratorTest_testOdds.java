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
 * Tests that {@link FilterListIterator} correctly filters a list to only odd numbers,
 * exercising full bidirectional navigation through the filtered view.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testOdds {

    private static final int LIST_SIZE = 20;
    private static final int RANDOM_WALK_STEPS = 500;

    private ArrayList<Integer> list;
    private ArrayList<Integer> odds;
    private Predicate<Integer> oddPred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        odds = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            list.add(Integer.valueOf(i));
            if (i % 2 != 0) {
                odds.add(Integer.valueOf(i));
            }
        }
        oddPred = x -> x % 2 != 0;
    }

    @AfterEach
    public void tearDown() {
        list = null;
        odds = null;
        oddPred = null;
    }

    @Test
    void testOdds() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), oddPred);
        walkLists(odds, filtered);
    }

    /**
     * Verifies that {@code testing} iterates over exactly the same elements as a fresh
     * list-iterator over {@code list}, by exercising six navigation phases:
     * full forward, full backward, forward/back/forward zigzag, full backward again,
     * symmetrical stride patterns for each stride length, and a random walk.
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // Phase 1: traverse all elements from start to end
        walkForward(expected, testing);

        // Phase 2: traverse all elements from end to start
        walkBackward(expected, testing);

        // Phase 3: zigzag — for each element, step forward, step back, then step forward again
        while (expected.hasNext()) {
            assertIteratorsInSync(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }

        // Phase 4: traverse all elements from end to start again
        walkBackward(expected, testing);

        // Phase 5: for each stride length i, walk forward i, back i/2, forward i/2, back i
        // (verifies that partial forward-then-backward traversals stay in sync)
        for (int i = 0; i < list.size(); i++) {
            walkForwardSteps(expected, testing, i);
            walkBackwardSteps(expected, testing, i / 2);
            walkForwardSteps(expected, testing, i / 2);
            walkBackwardSteps(expected, testing, i);
        }

        // Phase 6: random walk to cover arbitrary navigation patterns
        final StringBuilder walkTrace = new StringBuilder(RANDOM_WALK_STEPS);
        for (int i = 0; i < RANDOM_WALK_STEPS; i++) {
            if (random.nextBoolean()) {
                walkTrace.append('+');
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkTrace.toString());
                }
            } else {
                walkTrace.append('-');
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkTrace.toString());
                }
            }
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkTrace.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkTrace.toString());
        }
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertIteratorsInSync(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertIteratorsInSync(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    private void walkForwardSteps(final ListIterator<?> expected, final ListIterator<?> testing, final int steps) {
        for (int j = 0; j < steps; j++) {
            assertIteratorsInSync(expected, testing);
            assertTrue(expected.hasNext()); // if this fails, the test's stride arithmetic is wrong
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private void walkBackwardSteps(final ListIterator<?> expected, final ListIterator<?> testing, final int steps) {
        for (int j = 0; j < steps; j++) {
            assertIteratorsInSync(expected, testing);
            assertTrue(expected.hasPrevious()); // if this fails, the test's stride arithmetic is wrong
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Asserts that both iterators report the same cursor position. */
    private void assertIteratorsInSync(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }
}
