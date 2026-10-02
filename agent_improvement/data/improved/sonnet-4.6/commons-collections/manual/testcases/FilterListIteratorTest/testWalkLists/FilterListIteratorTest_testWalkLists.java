package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class FilterListIteratorTest_testWalkLists {

    private ArrayList<Integer> list;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(Integer.valueOf(i));
        }
    }

    @AfterEach
    public void tearDown() {
        list = null;
    }

    /** Advances both iterators one step, asserting indices and element equality. */
    private void assertNextEquals(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
        assertTrue(testing.hasNext());
        assertEquals(expected.next(), testing.next());
    }

    /** Retreats both iterators one step, asserting indices and element equality. */
    private void assertPreviousEquals(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
        assertTrue(testing.hasPrevious());
        assertEquals(expected.previous(), testing.previous());
    }

    /** Walks {@code testing} forward through the full list in lock-step with {@code expected}. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertNextEquals(expected, testing);
        }
    }

    /** Walks {@code testing} backward through the full list in lock-step with {@code expected}. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertPreviousEquals(expected, testing);
        }
    }

    /**
     * Advances both iterators {@code steps} times, asserting equality at each step.
     * The comment "if this one fails we've got a logic error in the test" is preserved
     * as a reminder that {@code expected.hasNext()} guards test logic, not the SUT.
     */
    private void advanceSteps(final ListIterator<?> expected, final ListIterator<?> testing, final int steps) {
        for (int j = 0; j < steps; j++) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            // if this one fails we've got a logic error in the test
            assertTrue(expected.hasNext());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /**
     * Retreats both iterators {@code steps} times, asserting equality at each step.
     * The comment "if this one fails we've got a logic error in the test" is preserved
     * as a reminder that {@code expected.hasPrevious()} guards test logic, not the SUT.
     */
    private void retreatSteps(final ListIterator<?> expected, final ListIterator<?> testing, final int steps) {
        for (int j = 0; j < steps; j++) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            // if this one fails we've got a logic error in the test
            assertTrue(expected.hasPrevious());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /**
     * Exhaustively validates that {@code testing} behaves identically to a plain
     * list iterator over {@code list}. Six traversal phases are exercised:
     * <ol>
     *   <li>Full forward traversal</li>
     *   <li>Full backward traversal</li>
     *   <li>Alternating forward/backward zigzag (next → previous → next per element)</li>
     *   <li>Full backward traversal again</li>
     *   <li>Nested advance/retreat pattern: advance i, retreat i/2, advance i/2, retreat i</li>
     *   <li>500-step random walk</li>
     * </ol>
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // Phase 1: walk all the way forward
        walkForward(expected, testing);

        // Phase 2: walk all the way back
        walkBackward(expected, testing);

        // Phase 3: forward-back-forward zigzag — calls to next() must update previous()
        // even after hasPrevious() has already set the previous-object cache
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

        // Phase 4: walk all the way back
        walkBackward(expected, testing);

        // Phase 5: for each i in [0, size), advance i, retreat i/2, advance i/2, retreat i
        for (int i = 0; i < list.size(); i++) {
            advanceSteps(expected, testing, i);
            retreatSteps(expected, testing, i / 2);
            advanceSteps(expected, testing, i / 2);
            retreatSteps(expected, testing, i);
        }

        // Phase 6: 500-step random walk; walkHistory records the path for assertion messages
        final StringBuilder walkHistory = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                // step forward
                walkHistory.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkHistory.toString());
                }
            } else {
                // step backward
                walkHistory.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkHistory.toString());
                }
            }
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkHistory.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkHistory.toString());
        }
    }

    @Test
    void testWalkLists() {
        // Confirm that the walkLists validation harness itself works correctly by
        // using the plain ArrayList iterator as both the reference and the subject.
        walkLists(list, list.listIterator());
    }
}
