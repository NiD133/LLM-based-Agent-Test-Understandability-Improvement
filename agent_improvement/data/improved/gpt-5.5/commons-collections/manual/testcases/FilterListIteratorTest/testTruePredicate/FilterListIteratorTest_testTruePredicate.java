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
        assertEquals(expected.next(), testing.next());
        assertEquals(expected.hasPrevious(), testing.hasPrevious());

        final Object expectedAfterSecondNext = expected.next();
        final Object testingAfterSecondNext = testing.next();
        assertEquals(expectedAfterSecondNext, testingAfterSecondNext);

        final Object expectedAfterPrevious = expected.previous();
        final Object testingAfterPrevious = testing.previous();
        assertEquals(expectedAfterSecondNext, expectedAfterPrevious);
        assertEquals(testingAfterSecondNext, testingAfterPrevious);
    }

    private void previousPreviousNext(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.previous(), testing.previous());
        assertEquals(expected.hasNext(), testing.hasNext());

        final Object expectedAfterSecondPrevious = expected.previous();
        final Object testingAfterSecondPrevious = testing.previous();
        assertEquals(expectedAfterSecondPrevious, testingAfterSecondPrevious);

        final Object expectedAfterNext = expected.next();
        final Object testingAfterNext = testing.next();
        assertEquals(expectedAfterSecondPrevious, testingAfterNext);
        assertEquals(expectedAfterSecondPrevious, expectedAfterNext);
        assertEquals(testingAfterSecondPrevious, testingAfterNext);
    }

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        odds = new ArrayList<>();
        evens = new ArrayList<>();
        threes = new ArrayList<>();
        fours = new ArrayList<>();
        sixes = new ArrayList<>();

        for (int value = 0; value < FIXTURE_SIZE; value++) {
            list.add(Integer.valueOf(value));
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

        truePred = value -> true;
        falsePred = value -> true;
        evenPred = value -> value % 2 == 0;
        oddPred = value -> value % 2 != 0;
        threePred = value -> value % 3 == 0;
        fourPred = value -> value % 4 == 0;
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

    private void assertMatchingCursorPosition(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }

    private void assertNextMatches(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertMatchingCursorPosition(expected, testing);
        assertTrue(testing.hasNext());
        assertEquals(expected.next(), testing.next());
    }

    private void assertPreviousMatches(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertMatchingCursorPosition(expected, testing);
        assertTrue(testing.hasPrevious());
        assertEquals(expected.previous(), testing.previous());
    }

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertPreviousMatches(expected, testing);
        }
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertNextMatches(expected, testing);
        }
    }

    private <E> void walkLists(final List<E> expectedValues, final ListIterator<E> testing) {
        final ListIterator<E> expected = expectedValues.listIterator();

        walkForward(expected, testing);
        walkBackward(expected, testing);
        walkForwardBackwardForward(expected, testing);
        walkBackward(expected, testing);
        walkVariableDistances(expectedValues, expected, testing);
        walkRandomly(expected, testing);
    }

    private void walkForwardBackwardForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertNextMatches(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private <E> void walkVariableDistances(final List<E> expectedValues, final ListIterator<E> expected,
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
            assertMatchingCursorPosition(expected, testing);
            assertTrue(expected.hasNext());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private void walkBackwardBy(final int steps, final ListIterator<?> expected, final ListIterator<?> testing) {
        for (int step = 0; step < steps; step++) {
            assertMatchingCursorPosition(expected, testing);
            assertTrue(expected.hasPrevious());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    private void walkRandomly(final ListIterator<?> expected, final ListIterator<?> testing) {
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
    void testTruePredicate() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), truePred);
        walkLists(list, filtered);
    }
}
