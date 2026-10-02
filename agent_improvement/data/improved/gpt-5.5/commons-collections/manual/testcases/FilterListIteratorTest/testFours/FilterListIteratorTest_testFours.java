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
public class FilterListIteratorTest_testFours {

    private static final int SOURCE_SIZE = 20;
    private static final int RANDOM_WALK_STEPS = 500;

    private ArrayList<Integer> sourceValues;
    private ArrayList<Integer> odds;
    private ArrayList<Integer> evens;
    private ArrayList<Integer> threes;
    private ArrayList<Integer> multiplesOfFour;
    private ArrayList<Integer> sixes;

    private Predicate<Integer> truePred;
    private Predicate<Integer> falsePred;
    private Predicate<Integer> evenPred;
    private Predicate<Integer> oddPred;
    private Predicate<Integer> threePred;
    private Predicate<Integer> fourPred;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        sourceValues = new ArrayList<>();
        odds = new ArrayList<>();
        evens = new ArrayList<>();
        threes = new ArrayList<>();
        multiplesOfFour = new ArrayList<>();
        sixes = new ArrayList<>();

        for (int i = 0; i < SOURCE_SIZE; i++) {
            sourceValues.add(Integer.valueOf(i));
            if (i % 2 == 0) {
                evens.add(Integer.valueOf(i));
            }
            if (i % 2 != 0) {
                odds.add(Integer.valueOf(i));
            }
            if (i % 3 == 0) {
                threes.add(Integer.valueOf(i));
            }
            if (i % 4 == 0) {
                multiplesOfFour.add(Integer.valueOf(i));
            }
            if (i % 6 == 0) {
                sixes.add(Integer.valueOf(i));
            }
        }

        truePred = x -> true;
        falsePred = x -> true;
        evenPred = x -> x % 2 == 0;
        oddPred = x -> x % 2 != 0;
        threePred = x -> x % 3 == 0;
        fourPred = x -> x % 4 == 0;
    }

    @AfterEach
    public void tearDown() throws Exception {
        sourceValues = null;
        odds = null;
        evens = null;
        threes = null;
        multiplesOfFour = null;
        sixes = null;
        truePred = null;
        falsePred = null;
        evenPred = null;
        oddPred = null;
        threePred = null;
        fourPred = null;
    }

    private void assertIteratorPosition(final ListIterator<?> expected, final ListIterator<?> actual) {
        assertEquals(expected.nextIndex(), actual.nextIndex());
        assertEquals(expected.previousIndex(), actual.previousIndex());
    }

    private void assertNextElement(final ListIterator<?> expected, final ListIterator<?> actual) {
        assertIteratorPosition(expected, actual);
        assertTrue(actual.hasNext());
        assertEquals(expected.next(), actual.next());
    }

    private void assertPreviousElement(final ListIterator<?> expected, final ListIterator<?> actual) {
        assertIteratorPosition(expected, actual);
        assertTrue(actual.hasPrevious());
        assertEquals(expected.previous(), actual.previous());
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasNext()) {
            assertNextElement(expected, actual);
        }
    }

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasPrevious()) {
            assertPreviousElement(expected, actual);
        }
    }

    private void walkForwardBackForward(final ListIterator<?> expected, final ListIterator<?> actual) {
        while (expected.hasNext()) {
            assertNextElement(expected, actual);
            assertTrue(actual.hasPrevious());
            assertEquals(expected.previous(), actual.previous());
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
        }
    }

    private void walkVariableDistances(final ListIterator<?> expected, final ListIterator<?> actual,
        final int expectedSize) {
        for (int distance = 0; distance < expectedSize; distance++) {
            walkForwardBy(expected, actual, distance);
            walkBackwardBy(expected, actual, distance / 2);
            walkForwardBy(expected, actual, distance / 2);
            walkBackwardBy(expected, actual, distance);
        }
    }

    private void walkForwardBy(final ListIterator<?> expected, final ListIterator<?> actual, final int steps) {
        for (int i = 0; i < steps; i++) {
            assertIteratorPosition(expected, actual);
            assertTrue(expected.hasNext());
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
        }
    }

    private void walkBackwardBy(final ListIterator<?> expected, final ListIterator<?> actual, final int steps) {
        for (int i = 0; i < steps; i++) {
            assertIteratorPosition(expected, actual);
            assertTrue(expected.hasPrevious());
            assertTrue(actual.hasPrevious());
            assertEquals(expected.previous(), actual.previous());
        }
    }

    private void randomWalk(final ListIterator<?> expected, final ListIterator<?> actual) {
        final StringBuilder walkdescr = new StringBuilder(500);
        for (int i = 0; i < RANDOM_WALK_STEPS; i++) {
            if (random.nextBoolean()) {
                walkdescr.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), actual.next(), walkdescr.toString());
                }
            } else {
                walkdescr.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), actual.previous(), walkdescr.toString());
                }
            }
            assertEquals(expected.nextIndex(), actual.nextIndex(), walkdescr.toString());
            assertEquals(expected.previousIndex(), actual.previousIndex(), walkdescr.toString());
        }
    }

    private <E> void assertSameIterationBehavior(final List<E> expectedValues, final ListIterator<E> actual) {
        final ListIterator<E> expected = expectedValues.listIterator();

        walkForward(expected, actual);
        walkBackward(expected, actual);
        walkForwardBackForward(expected, actual);
        walkBackward(expected, actual);
        walkVariableDistances(expected, actual, expectedValues.size());
        randomWalk(expected, actual);
    }

    @Test
    void testFours() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(sourceValues.listIterator(), fourPred);
        assertSameIterationBehavior(multiplesOfFour, filtered);
    }
}
