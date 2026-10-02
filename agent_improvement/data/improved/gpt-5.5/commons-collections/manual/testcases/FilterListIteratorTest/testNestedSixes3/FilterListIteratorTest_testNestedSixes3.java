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
public class FilterListIteratorTest_testNestedSixes3 {

    private static final int SOURCE_SIZE = 20;
    private static final int RANDOM_WALK_STEPS = 500;

    private ArrayList<Integer> list;

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
        list = new ArrayList<>();
        odds = new ArrayList<>();
        evens = new ArrayList<>();
        threes = new ArrayList<>();
        fours = new ArrayList<>();
        sixes = new ArrayList<>();

        for (int value = 0; value < SOURCE_SIZE; value++) {
            final Integer boxedValue = Integer.valueOf(value);
            list.add(boxedValue);
            if (value % 2 == 0) {
                evens.add(boxedValue);
            }
            if (value % 2 != 0) {
                odds.add(boxedValue);
            }
            if (value % 3 == 0) {
                threes.add(boxedValue);
            }
            if (value % 4 == 0) {
                fours.add(boxedValue);
            }
            if (value % 6 == 0) {
                sixes.add(boxedValue);
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
        list = null;
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

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private <E> void walkLists(final List<E> expectedValues, final ListIterator<E> testing) {
        final ListIterator<E> expected = expectedValues.listIterator();

        walkForward(expected, testing);
        walkBackward(expected, testing);
        walkForwardThenBackThenForward(expected, testing);
        walkBackward(expected, testing);
        walkIncreasingDistances(expectedValues, expected, testing);
        walkRandomly(expected, testing);
    }

    private void walkForwardThenBackThenForward(final ListIterator<?> expected, final ListIterator<?> testing) {
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
    }

    private <E> void walkIncreasingDistances(final List<E> expectedValues, final ListIterator<E> expected,
            final ListIterator<E> testing) {
        for (int distance = 0; distance < expectedValues.size(); distance++) {
            walkForwardBy(distance, expected, testing);
            walkBackwardBy(distance / 2, expected, testing);
            walkForwardBy(distance / 2, expected, testing);
            walkBackwardBy(distance, expected, testing);
        }
    }

    private void walkForwardBy(final int steps, final ListIterator<?> expected, final ListIterator<?> testing) {
        for (int step = 0; step < steps; step++) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            // if this one fails we've got a logic error in the test
            assertTrue(expected.hasNext());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private void walkBackwardBy(final int steps, final ListIterator<?> expected, final ListIterator<?> testing) {
        for (int step = 0; step < steps; step++) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            // if this one fails we've got a logic error in the test
            assertTrue(expected.hasPrevious());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    private void walkRandomly(final ListIterator<?> expected, final ListIterator<?> testing) {
        final StringBuilder walkdescr = new StringBuilder(500);
        for (int step = 0; step < RANDOM_WALK_STEPS; step++) {
            if (random.nextBoolean()) {
                walkdescr.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkdescr.toString());
                }
            } else {
                walkdescr.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkdescr.toString());
                }
            }
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkdescr.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkdescr.toString());
        }
    }

    @Test
    void testNestedSixes3() {
        final FilterListIterator<Integer> threesThenEvens =
                new FilterListIterator<>(new FilterListIterator<>(list.listIterator(), threePred), evenPred);

        walkLists(sixes, new FilterListIterator<>(threesThenEvens, truePred));
    }
}
