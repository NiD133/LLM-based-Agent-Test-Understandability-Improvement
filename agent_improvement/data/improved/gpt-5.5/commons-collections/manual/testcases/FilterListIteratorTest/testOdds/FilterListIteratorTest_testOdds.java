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
public class FilterListIteratorTest_testOdds {

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

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        odds = new ArrayList<>();
        evens = new ArrayList<>();
        threes = new ArrayList<>();
        fours = new ArrayList<>();
        sixes = new ArrayList<>();

        for (int i = 0; i < LIST_SIZE; i++) {
            list.add(Integer.valueOf(i));
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
                fours.add(Integer.valueOf(i));
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

    private void assertSameIteratorPosition(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.nextIndex(), testing.nextIndex());
        assertEquals(expected.previousIndex(), testing.previousIndex());
    }

    private void assertStepForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertSameIteratorPosition(expected, testing);
        assertTrue(testing.hasNext());
        assertEquals(expected.next(), testing.next());
    }

    private void assertStepBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertSameIteratorPosition(expected, testing);
        assertTrue(testing.hasPrevious());
        assertEquals(expected.previous(), testing.previous());
    }

    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertStepForward(expected, testing);
        }
    }

    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertStepBackward(expected, testing);
        }
    }

    private void walkForwardBackForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertStepForward(expected, testing);
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    private void walkVariableDistances(final List<?> expectedValues, final ListIterator<?> expected,
            final ListIterator<?> testing) {
        for (int i = 0; i < expectedValues.size(); i++) {
            for (int j = 0; j < i; j++) {
                assertStepForward(expected, testing);
            }
            for (int j = 0; j < i / 2; j++) {
                assertStepBackward(expected, testing);
            }
            for (int j = 0; j < i / 2; j++) {
                assertStepForward(expected, testing);
            }
            for (int j = 0; j < i; j++) {
                assertStepBackward(expected, testing);
            }
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

    private <E> void walkLists(final List<E> expectedValues, final ListIterator<E> testing) {
        final ListIterator<E> expected = expectedValues.listIterator();

        walkForward(expected, testing);
        walkBackward(expected, testing);
        walkForwardBackForward(expected, testing);
        walkBackward(expected, testing);
        walkVariableDistances(expectedValues, expected, testing);
        walkRandomly(expected, testing);
    }

    @Test
    void testOdds() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), oddPred);
        walkLists(odds, filtered);
    }
}
