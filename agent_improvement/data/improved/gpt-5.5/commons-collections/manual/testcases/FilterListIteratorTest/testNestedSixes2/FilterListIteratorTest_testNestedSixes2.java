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

@SuppressWarnings("boxing")
public class FilterListIteratorTest_testNestedSixes2 {

    private static final int SOURCE_SIZE = 20;
    private static final int RANDOM_WALK_STEPS = 500;

    private ArrayList<Integer> sourceValues;
    private ArrayList<Integer> valuesDivisibleBySix;
    private Predicate<Integer> evenPredicate;
    private Predicate<Integer> divisibleByThreePredicate;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        sourceValues = new ArrayList<>();
        valuesDivisibleBySix = new ArrayList<>();

        for (int value = 0; value < SOURCE_SIZE; value++) {
            sourceValues.add(Integer.valueOf(value));
            if (value % 6 == 0) {
                valuesDivisibleBySix.add(Integer.valueOf(value));
            }
        }

        evenPredicate = value -> value % 2 == 0;
        divisibleByThreePredicate = value -> value % 3 == 0;
    }

    private void assertIteratorPositionMatches(final ListIterator<?> expected, final ListIterator<?> actual) {
        assertEquals(expected.nextIndex(), actual.nextIndex());
        assertEquals(expected.previousIndex(), actual.previousIndex());
    }

    private void assertNextValueMatches(final ListIterator<?> expected, final ListIterator<?> actual) {
        assertIteratorPositionMatches(expected, actual);
        assertTrue(expected.hasNext());
        assertTrue(actual.hasNext());
        assertEquals(expected.next(), actual.next());
    }

    private void assertPreviousValueMatches(final ListIterator<?> expected, final ListIterator<?> actual) {
        assertIteratorPositionMatches(expected, actual);
        assertTrue(expected.hasPrevious());
        assertTrue(actual.hasPrevious());
        assertEquals(expected.previous(), actual.previous());
    }

    private void walkForwardToEnd(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasNext()) {
            assertIteratorPositionMatches(expected, actual);
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
        }
    }

    private void walkBackwardToStart(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasPrevious()) {
            assertIteratorPositionMatches(expected, actual);
            assertTrue(actual.hasPrevious());
            assertEquals(expected.previous(), actual.previous());
        }
    }

    private void walkForwardBackwardForward(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasNext()) {
            assertIteratorPositionMatches(expected, actual);
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());

            assertTrue(actual.hasPrevious());
            assertEquals(expected.previous(), actual.previous());

            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
        }
    }

    private void walkIncreasingDistancesFromStart(final List<?> expectedValues, final ListIterator<?> expected,
            final ListIterator<?> actual) {
        for (int distance = 0; distance < expectedValues.size(); distance++) {
            for (int step = 0; step < distance; step++) {
                assertNextValueMatches(expected, actual);
            }
            for (int step = 0; step < distance / 2; step++) {
                assertPreviousValueMatches(expected, actual);
            }
            for (int step = 0; step < distance / 2; step++) {
                assertNextValueMatches(expected, actual);
            }
            for (int step = 0; step < distance; step++) {
                assertPreviousValueMatches(expected, actual);
            }
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

    private <E> void assertIteratesLikeList(final List<E> expectedValues, final ListIterator<E> actual) {
        final ListIterator<E> expected = expectedValues.listIterator();

        walkForwardToEnd(expected, actual);
        walkBackwardToStart(expected, actual);
        walkForwardBackwardForward(expected, actual);
        walkBackwardToStart(expected, actual);
        walkIncreasingDistancesFromStart(expectedValues, expected, actual);
        randomWalk(expected, actual);
    }

    @Test
    void testNestedSixes2() {
        final FilterListIterator<Integer> evenValues =
                new FilterListIterator<>(sourceValues.listIterator(), evenPredicate);
        final FilterListIterator<Integer> valuesDivisibleByTwoAndThree =
                new FilterListIterator<>(evenValues, divisibleByThreePredicate);

        assertIteratesLikeList(valuesDivisibleBySix, valuesDivisibleByTwoAndThree);
    }
}
