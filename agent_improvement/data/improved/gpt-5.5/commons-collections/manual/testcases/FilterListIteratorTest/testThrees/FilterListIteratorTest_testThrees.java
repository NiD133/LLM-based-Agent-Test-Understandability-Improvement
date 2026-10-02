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
public class FilterListIteratorTest_testThrees {

    private static final int SOURCE_SIZE = 20;
    private static final int RANDOM_WALK_STEPS = 500;

    private ArrayList<Integer> sourceNumbers;

    private ArrayList<Integer> odds;

    private ArrayList<Integer> evens;

    private ArrayList<Integer> threes;

    private ArrayList<Integer> fours;

    private ArrayList<Integer> sixes;

    private Predicate<Integer> truePred;

    private Predicate<Integer> falsePred;

    private Predicate<Integer> evenPred;

    private Predicate<Integer> oddPred;

    private Predicate<Integer> threePred;

    private Predicate<Integer> fourPred;

    private final Random random = new Random();

    private void nextNextPrevious(final ListIterator<?> expected, final ListIterator<?> testing) {
        // calls to next() should change the value returned by previous()
        // even after previous() has been set by a call to hasPrevious()
        assertEquals(expected.next(), testing.next());
        assertEquals(expected.hasPrevious(), testing.hasPrevious());
        final Object expecteda = expected.next();
        final Object testinga = testing.next();
        assertEquals(expecteda, testinga);
        final Object expectedb = expected.previous();
        final Object testingb = testing.previous();
        assertEquals(expecteda, expectedb);
        assertEquals(testinga, testingb);
    }

    private void previousPreviousNext(final ListIterator<?> expected, final ListIterator<?> testing) {
        // calls to previous() should change the value returned by next()
        // even after next() has been set by a call to hasNext()
        assertEquals(expected.previous(), testing.previous());
        assertEquals(expected.hasNext(), testing.hasNext());
        final Object expecteda = expected.previous();
        final Object testinga = testing.previous();
        assertEquals(expecteda, testinga);
        final Object expectedb = expected.next();
        final Object testingb = testing.next();
        assertEquals(expecteda, testingb);
        assertEquals(expecteda, expectedb);
        assertEquals(testinga, testingb);
    }

    @BeforeEach
    public void setUp() {
        sourceNumbers = new ArrayList<>();
        odds = new ArrayList<>();
        evens = new ArrayList<>();
        threes = new ArrayList<>();
        fours = new ArrayList<>();
        sixes = new ArrayList<>();

        for (int value = 0; value < SOURCE_SIZE; value++) {
            sourceNumbers.add(Integer.valueOf(value));
            if (value % 2 == 0) {
                evens.add(Integer.valueOf(value));
            }
            if (value % 2 != 0) {
                odds.add(Integer.valueOf(value));
            }
            if (value % 3 == 0) {
                threes.add(Integer.valueOf(value));
            }
            if (value % 4 == 0) {
                fours.add(Integer.valueOf(value));
            }
            if (value % 6 == 0) {
                sixes.add(Integer.valueOf(value));
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
        sourceNumbers = null;
        odds = null;
        evens = null;
        threes = null;
        fours = null;
        sixes = null;
        truePred = null;
        falsePred = null;
        evenPred = null;
        oddPred = null;
        threePred = null;
        fourPred = null;
    }

    private void assertSamePosition(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertSamePosition(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertSamePosition(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private <E> void walkLists(final List<E> expectedValues, final ListIterator<E> testing) {
        final ListIterator<E> expected = expectedValues.listIterator();

        walkForward(expected, testing);
        walkBackward(expected, testing);
        walkForwardBackForward(expected, testing);
        walkBackward(expected, testing);
        walkVaryingDistances(expectedValues.size(), expected, testing);
        walkRandomly(expected, testing);
    }

    private <E> void walkForwardBackForward(final ListIterator<E> expected, final ListIterator<E> testing) {
        while (expected.hasNext()) {
            assertSamePosition(expected, testing);
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());

            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());

            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private <E> void walkVaryingDistances(final int size, final ListIterator<E> expected, final ListIterator<E> testing) {
        for (int distance = 0; distance < size; distance++) {
            walkForwardBy(distance, expected, testing);
            walkBackwardBy(distance / 2, expected, testing);
            walkForwardBy(distance / 2, expected, testing);
            walkBackwardBy(distance, expected, testing);
        }
    }

    private <E> void walkForwardBy(final int steps, final ListIterator<E> expected, final ListIterator<E> testing) {
        for (int step = 0; step < steps; step++) {
            assertSamePosition(expected, testing);
            assertTrue(expected.hasNext());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private <E> void walkBackwardBy(final int steps, final ListIterator<E> expected, final ListIterator<E> testing) {
        for (int step = 0; step < steps; step++) {
            assertSamePosition(expected, testing);
            assertTrue(expected.hasPrevious());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    private <E> void walkRandomly(final ListIterator<E> expected, final ListIterator<E> testing) {
        final StringBuilder walkDescription = new StringBuilder(RANDOM_WALK_STEPS);
        for (int step = 0; step < RANDOM_WALK_STEPS; step++) {
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

    @Test
    void testThrees() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(sourceNumbers.listIterator(), threePred);
        walkLists(threes, filtered);
    }
}
