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
public class FilterListIteratorTest_testNestedSixes {

    private static final int LIST_SIZE = 20;

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
        final Object secondExpectedNext = expected.next();
        final Object secondTestingNext = testing.next();
        assertEquals(secondExpectedNext, secondTestingNext);
        final Object expectedPrevious = expected.previous();
        final Object testingPrevious = testing.previous();
        assertEquals(secondExpectedNext, expectedPrevious);
        assertEquals(secondTestingNext, testingPrevious);
    }

    private void previousPreviousNext(final ListIterator<?> expected, final ListIterator<?> testing) {
        // calls to previous() should change the value returned by next()
        // even after next() has been set by a call to hasNext()
        assertEquals(expected.previous(), testing.previous());
        assertEquals(expected.hasNext(), testing.hasNext());
        final Object secondExpectedPrevious = expected.previous();
        final Object secondTestingPrevious = testing.previous();
        assertEquals(secondExpectedPrevious, secondTestingPrevious);
        final Object expectedNext = expected.next();
        final Object testingNext = testing.next();
        assertEquals(secondExpectedPrevious, testingNext);
        assertEquals(secondExpectedPrevious, expectedNext);
        assertEquals(secondTestingPrevious, testingNext);
    }

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        odds = new ArrayList<>();
        evens = new ArrayList<>();
        threes = new ArrayList<>();
        fours = new ArrayList<>();
        sixes = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            final Integer value = Integer.valueOf(i);
            list.add(value);
            if (i % 2 == 0) {
                evens.add(value);
            }
            if (i % 2 != 0) {
                odds.add(value);
            }
            if (i % 3 == 0) {
                threes.add(value);
            }
            if (i % 4 == 0) {
                fours.add(value);
            }
            if (i % 6 == 0) {
                sixes.add(value);
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

        walkBackward(expected, testing);

        for (int i = 0; i < expectedValues.size(); i++) {
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                // if this one fails we've got a logic error in the test
                assertTrue(expected.hasNext());
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                // if this one fails we've got a logic error in the test
                assertTrue(expected.hasPrevious());
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                // if this one fails we've got a logic error in the test
                assertTrue(expected.hasNext());
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                // if this one fails we've got a logic error in the test
                assertTrue(expected.hasPrevious());
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
        }

        final StringBuilder walkDescription = new StringBuilder(RANDOM_WALK_STEPS);
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

    @Test
    void testNestedSixes() {
        final FilterListIterator<Integer> multiplesOfThree = new FilterListIterator<>(list.listIterator(), threePred);
        final FilterListIterator<Integer> multiplesOfThreeAndEven = new FilterListIterator<>(multiplesOfThree, evenPred);

        walkLists(sixes, multiplesOfThreeAndEven);
    }
}
