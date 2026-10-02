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

@SuppressWarnings("boxing")
public class FilterListIteratorTest_testTruePredicate {

    private static final int FIXTURE_SIZE = 20;
    private static final int RANDOM_WALK_STEPS = 500;

    private ArrayList<Integer> list;

    private Predicate<Integer> truePred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        for (int value = 0; value < FIXTURE_SIZE; value++) {
            list.add(Integer.valueOf(value));
        }
        truePred = value -> true;
    }

    @AfterEach
    public void tearDown() throws Exception {
        list = null;
        truePred = null;
    }

    private void assertIteratorIndexesMatch(final ListIterator<?> expected, final ListIterator<?> actual) {
        assertEquals(expected.nextIndex(), actual.nextIndex());
        assertEquals(expected.previousIndex(), actual.previousIndex());
    }

    private void assertNextElementsMatch(final ListIterator<?> expected, final ListIterator<?> actual) {
        assertIteratorIndexesMatch(expected, actual);
        assertTrue(actual.hasNext());
        assertEquals(expected.next(), actual.next());
    }

    private void assertPreviousElementsMatch(final ListIterator<?> expected, final ListIterator<?> actual) {
        assertIteratorIndexesMatch(expected, actual);
        assertTrue(actual.hasPrevious());
        assertEquals(expected.previous(), actual.previous());
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasNext()) {
            assertNextElementsMatch(expected, actual);
        }
    }

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasPrevious()) {
            assertPreviousElementsMatch(expected, actual);
        }
    }

    private <E> void assertFilteredIteratorMatches(final List<E> expectedValues, final ListIterator<E> actual) {
        final ListIterator<E> expected = expectedValues.listIterator();

        walkForward(expected, actual);
        walkBackward(expected, actual);
        walkForwardWithOneStepBack(expected, actual);
        walkBackward(expected, actual);
        walkIncreasingDistancesFromStart(expectedValues.size(), expected, actual);
        randomWalk(expected, actual);
    }

    private void walkForwardWithOneStepBack(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasNext()) {
            assertNextElementsMatch(expected, actual);
            assertPreviousElementsMatch(expected, actual);
            assertNextElementsMatch(expected, actual);
        }
    }

    private void walkIncreasingDistancesFromStart(
            final int size,
            final ListIterator<?> expected,
            final ListIterator<?> actual) {

        for (int distanceFromStart = 0; distanceFromStart < size; distanceFromStart++) {
            walkStepsForward(distanceFromStart, expected, actual);
            walkStepsBackward(distanceFromStart / 2, expected, actual);
            walkStepsForward(distanceFromStart / 2, expected, actual);
            walkStepsBackward(distanceFromStart, expected, actual);
        }
    }

    private void walkStepsForward(final int steps, final ListIterator<?> expected, final ListIterator<?> actual) {
        for (int step = 0; step < steps; step++) {
            assertTrue(expected.hasNext());
            assertNextElementsMatch(expected, actual);
        }
    }

    private void walkStepsBackward(final int steps, final ListIterator<?> expected, final ListIterator<?> actual) {
        for (int step = 0; step < steps; step++) {
            assertTrue(expected.hasPrevious());
            assertPreviousElementsMatch(expected, actual);
        }
    }

    private void randomWalk(final ListIterator<?> expected, final ListIterator<?> actual) {
        final StringBuilder walkDescription = new StringBuilder(RANDOM_WALK_STEPS);
        for (int step = 0; step < RANDOM_WALK_STEPS; step++) {
            if (random.nextBoolean()) {
                walkDescription.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), actual.next(), walkDescription.toString());
                }
            } else {
                walkDescription.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), actual.previous(), walkDescription.toString());
                }
            }
            assertEquals(expected.nextIndex(), actual.nextIndex(), walkDescription.toString());
            assertEquals(expected.previousIndex(), actual.previousIndex(), walkDescription.toString());
        }
    }

    @Test
    void testTruePredicate() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), truePred);
        assertFilteredIteratorMatches(list, filtered);
    }
}
