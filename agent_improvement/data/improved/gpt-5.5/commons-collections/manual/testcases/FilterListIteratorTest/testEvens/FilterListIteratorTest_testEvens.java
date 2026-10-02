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
public class FilterListIteratorTest_testEvens {

    private static final int SOURCE_SIZE = 20;
    private static final int RANDOM_WALK_STEPS = 500;

    private final Random random = new Random();

    private ArrayList<Integer> sourceValues;
    private ArrayList<Integer> expectedEvenValues;
    private Predicate<Integer> evenPredicate;

    @BeforeEach
    public void setUp() {
        sourceValues = new ArrayList<>();
        expectedEvenValues = new ArrayList<>();

        for (int value = 0; value < SOURCE_SIZE; value++) {
            sourceValues.add(Integer.valueOf(value));
            if (value % 2 == 0) {
                expectedEvenValues.add(Integer.valueOf(value));
            }
        }

        evenPredicate = value -> value % 2 == 0;
    }

    @Test
    void testEvens() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(sourceValues.listIterator(), evenPredicate);
        walkLists(expectedEvenValues, filtered);
    }

    private <E> void walkLists(final List<E> expectedValues, final ListIterator<E> filteredValues) {
        final ListIterator<E> expected = expectedValues.listIterator();

        walkForward(expected, filteredValues);
        walkBackward(expected, filteredValues);
        walkForwardWithSingleStepBacktracking(expected, filteredValues);
        walkBackward(expected, filteredValues);
        walkIncreasingRoundTrips(expectedValues.size(), expected, filteredValues);
        walkRandomly(expected, filteredValues);
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), actual.nextIndex());
            assertEquals(expected.previousIndex(), actual.previousIndex());
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
        }
    }

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(), actual.nextIndex());
            assertEquals(expected.previousIndex(), actual.previousIndex());
            assertTrue(actual.hasPrevious());
            assertEquals(expected.previous(), actual.previous());
        }
    }

    private void walkForwardWithSingleStepBacktracking(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), actual.nextIndex());
            assertEquals(expected.previousIndex(), actual.previousIndex());
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());

            assertTrue(actual.hasPrevious());
            assertEquals(expected.previous(), actual.previous());

            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
        }
    }

    private void walkIncreasingRoundTrips(
            final int expectedSize,
            final ListIterator<?> expected,
            final ListIterator<?> actual) {
        for (int distance = 0; distance < expectedSize; distance++) {
            walkForward(expected, actual, distance);
            walkBackward(expected, actual, distance / 2);
            walkForward(expected, actual, distance / 2);
            walkBackward(expected, actual, distance);
        }
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> actual, final int steps) {
        for (int step = 0; step < steps; step++) {
            assertEquals(expected.nextIndex(), actual.nextIndex());
            assertEquals(expected.previousIndex(), actual.previousIndex());
            assertTrue(expected.hasNext());
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
        }
    }

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> actual, final int steps) {
        for (int step = 0; step < steps; step++) {
            assertEquals(expected.nextIndex(), actual.nextIndex());
            assertEquals(expected.previousIndex(), actual.previousIndex());
            assertTrue(expected.hasPrevious());
            assertTrue(actual.hasPrevious());
            assertEquals(expected.previous(), actual.previous());
        }
    }

    private void walkRandomly(final ListIterator<?> expected, final ListIterator<?> actual) {
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
}
