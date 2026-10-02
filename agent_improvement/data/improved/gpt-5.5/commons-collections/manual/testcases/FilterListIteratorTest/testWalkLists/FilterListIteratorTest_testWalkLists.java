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

    private static final int LIST_SIZE = 20;
    private static final int RANDOM_WALK_STEPS = 500;

    private ArrayList<Integer> list;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            list.add(Integer.valueOf(i));
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        list = null;
    }

    private void assertIteratorPositionsMatch(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }

    private void assertNextElementMatches(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertIteratorPositionsMatch(expected, testing);
        assertTrue(testing.hasNext());
        assertEquals(expected.next(), testing.next());
    }

    private void assertPreviousElementMatches(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertIteratorPositionsMatch(expected, testing);
        assertTrue(testing.hasPrevious());
        assertEquals(expected.previous(), testing.previous());
    }

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertPreviousElementMatches(expected, testing);
        }
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertNextElementMatches(expected, testing);
        }
    }

    private void walkForwardBackForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertNextElementMatches(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private void walkForwardSteps(final int steps, final ListIterator<?> expected, final ListIterator<?> testing) {
        for (int j = 0; j < steps; j++) {
            assertIteratorPositionsMatch(expected, testing);
            assertTrue(expected.hasNext());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private void walkBackwardSteps(final int steps, final ListIterator<?> expected, final ListIterator<?> testing) {
        for (int j = 0; j < steps; j++) {
            assertIteratorPositionsMatch(expected, testing);
            assertTrue(expected.hasPrevious());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    private void walkVariableLengthPatterns(final int size, final ListIterator<?> expected, final ListIterator<?> testing) {
        for (int i = 0; i < size; i++) {
            walkForwardSteps(i, expected, testing);
            walkBackwardSteps(i / 2, expected, testing);
            walkForwardSteps(i / 2, expected, testing);
            walkBackwardSteps(i, expected, testing);
        }
    }

    private void walkRandomly(final ListIterator<?> expected, final ListIterator<?> testing) {
        final StringBuilder walkDescription = new StringBuilder(500);
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

    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        walkForward(expected, testing);
        walkBackward(expected, testing);
        walkForwardBackForward(expected, testing);
        walkBackward(expected, testing);
        walkVariableLengthPatterns(list.size(), expected, testing);
        walkRandomly(expected, testing);
    }

    @Test
    void testWalkLists() {
        walkLists(list, list.listIterator());
    }
}
