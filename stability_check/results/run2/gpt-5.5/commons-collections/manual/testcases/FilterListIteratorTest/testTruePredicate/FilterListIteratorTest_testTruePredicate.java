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

    private final Random random = new Random();

    private ArrayList<Integer> list;
    private Predicate<Integer> truePredicate;

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        for (int value = 0; value < 20; value++) {
            list.add(Integer.valueOf(value));
        }
        truePredicate = value -> true;
    }

    @AfterEach
    public void tearDown() {
        list = null;
        truePredicate = null;
    }

    private void assertRemainingForwardTraversalMatches(
            final ListIterator<?> expected,
            final ListIterator<?> actual) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), actual.nextIndex());
            assertEquals(expected.previousIndex(), actual.previousIndex());
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
        }
    }

    private void assertRemainingBackwardTraversalMatches(
            final ListIterator<?> expected,
            final ListIterator<?> actual) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(), actual.nextIndex());
            assertEquals(expected.previousIndex(), actual.previousIndex());
            assertTrue(actual.hasPrevious());
            assertEquals(expected.previous(), actual.previous());
        }
    }

    private <E> void assertIteratorMatchesUnfilteredList(final List<E> list, final ListIterator<E> actual) {
        final ListIterator<E> expected = list.listIterator();

        assertRemainingForwardTraversalMatches(expected, actual);
        assertRemainingBackwardTraversalMatches(expected, actual);
        assertAlternatingForwardBackwardForwardTraversalMatches(expected, actual);
        assertRemainingBackwardTraversalMatches(expected, actual);
        assertGrowingRoundTripTraversalMatches(list, expected, actual);
        assertRandomTraversalMatches(expected, actual);
    }

    private void assertAlternatingForwardBackwardForwardTraversalMatches(
            final ListIterator<?> expected,
            final ListIterator<?> actual) {
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

    private void assertGrowingRoundTripTraversalMatches(
            final List<?> list,
            final ListIterator<?> expected,
            final ListIterator<?> actual) {
        for (int distance = 0; distance < list.size(); distance++) {
            moveForwardAndAssertMatches(expected, actual, distance);
            moveBackwardAndAssertMatches(expected, actual, distance / 2);
            moveForwardAndAssertMatches(expected, actual, distance / 2);
            moveBackwardAndAssertMatches(expected, actual, distance);
        }
    }

    private void moveForwardAndAssertMatches(
            final ListIterator<?> expected,
            final ListIterator<?> actual,
            final int steps) {
        for (int step = 0; step < steps; step++) {
            assertEquals(expected.nextIndex(), actual.nextIndex());
            assertEquals(expected.previousIndex(), actual.previousIndex());
            assertTrue(expected.hasNext());
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
        }
    }

    private void moveBackwardAndAssertMatches(
            final ListIterator<?> expected,
            final ListIterator<?> actual,
            final int steps) {
        for (int step = 0; step < steps; step++) {
            assertEquals(expected.nextIndex(), actual.nextIndex());
            assertEquals(expected.previousIndex(), actual.previousIndex());
            assertTrue(expected.hasPrevious());
            assertTrue(actual.hasPrevious());
            assertEquals(expected.previous(), actual.previous());
        }
    }

    private void assertRandomTraversalMatches(final ListIterator<?> expected, final ListIterator<?> actual) {
        final StringBuilder walkDescription = new StringBuilder(500);
        for (int step = 0; step < 500; step++) {
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
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), truePredicate);
        assertIteratorMatchesUnfilteredList(list, filtered);
    }
}
