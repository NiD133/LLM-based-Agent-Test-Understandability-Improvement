package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
public class FilterListIteratorTest_testNestedSixes2 {

    private ArrayList<Integer> list;

    private ArrayList<Integer> odds;

    private ArrayList<Integer> evens;

    private ArrayList<Integer> threes;

    private ArrayList<Integer> fours;

    // Numbers divisible by both 2 and 3 (i.e. divisible by 6)
    private ArrayList<Integer> sixes;

    private Predicate<Integer> truePred;

    private Predicate<Integer> falsePred;

    private Predicate<Integer> evenPred;

    private Predicate<Integer> oddPred;

    private Predicate<Integer> threePred;

    private Predicate<Integer> fourPred;

    private final Random random = new Random();

    /**
     * Verifies that calling next() twice then previous() once returns the second element,
     * and that next()/previous() remain consistent after an intermediate hasPrevious() call.
     */
    private void nextNextPrevious(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.next(), testing.next());
        assertEquals(expected.hasPrevious(), testing.hasPrevious());

        final Object expectedFirst = expected.next();
        final Object testingFirst = testing.next();
        assertEquals(expectedFirst, testingFirst);

        // previous() must return the same element that next() just returned
        final Object expectedBack = expected.previous();
        final Object testingBack = testing.previous();
        assertEquals(expectedFirst, expectedBack);
        assertEquals(testingFirst, testingBack);
    }

    /**
     * Verifies that calling previous() twice then next() once returns the second-to-last element,
     * and that previous()/next() remain consistent after an intermediate hasNext() call.
     */
    private void previousPreviousNext(final ListIterator<?> expected, final ListIterator<?> testing) {
        assertEquals(expected.previous(), testing.previous());
        assertEquals(expected.hasNext(), testing.hasNext());

        final Object expectedFirst = expected.previous();
        final Object testingFirst = testing.previous();
        assertEquals(expectedFirst, testingFirst);

        // next() must return the same element that previous() just returned
        final Object expectedForward = expected.next();
        final Object testingForward = testing.next();
        assertEquals(expectedFirst, testingForward);
        assertEquals(expectedFirst, expectedForward);
        assertEquals(testingFirst, testingForward);
    }

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        odds = new ArrayList<>();
        evens = new ArrayList<>();
        threes = new ArrayList<>();
        fours = new ArrayList<>();
        sixes = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
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

    /** Walks the iterator backward, asserting each element and index matches the expected iterator. */
    private void walkBackward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasPrevious()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasPrevious());
            assertEquals(expected.previous(), testing.previous());
        }
    }

    /** Walks the iterator forward, asserting each element and index matches the expected iterator. */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    /**
     * Exhaustively validates that {@code testing} produces the same sequence as iterating
     * over {@code list} directly. Covers: full forward pass, full backward pass,
     * alternating forward/backward steps, partial forward-then-backward zigzags of
     * increasing depth, and a 500-step random walk.
     */
    private <E> void walkLists(final List<E> list, final ListIterator<E> testing) {
        final ListIterator<E> expected = list.listIterator();

        // Pass 1: walk all the way forward
        walkForward(expected, testing);

        // Pass 2: walk all the way back
        walkBackward(expected, testing);

        // Pass 3: forward, back, forward — one step at a time
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

        // Pass 4: walk all the way back
        walkBackward(expected, testing);

        // Pass 5: zigzag — for each depth i, advance i steps, retreat i/2, advance i/2, retreat i
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext());   // logic guard, not a FilterListIterator assertion
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious()); // logic guard
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
            for (int j = 0; j < i / 2; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasNext());   // logic guard
                assertTrue(testing.hasNext());
                assertEquals(expected.next(), testing.next());
            }
            for (int j = 0; j < i; j++) {
                assertEquals(expected.nextIndex(), testing.nextIndex());
                assertEquals(expected.previousIndex(), testing.previousIndex());
                assertTrue(expected.hasPrevious()); // logic guard
                assertTrue(testing.hasPrevious());
                assertEquals(expected.previous(), testing.previous());
            }
        }

        // Pass 6: 500-step random walk; record the walk path for diagnostic messages on failure
        final StringBuilder walkPath = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            if (random.nextBoolean()) {
                walkPath.append("+");
                if (expected.hasNext()) {
                    assertEquals(expected.next(), testing.next(), walkPath.toString());
                }
            } else {
                walkPath.append("-");
                if (expected.hasPrevious()) {
                    assertEquals(expected.previous(), testing.previous(), walkPath.toString());
                }
            }
            assertEquals(expected.nextIndex(), testing.nextIndex(), walkPath.toString());
            assertEquals(expected.previousIndex(), testing.previousIndex(), walkPath.toString());
        }
    }

    /**
     * Verifies that nesting two FilterListIterators is equivalent to applying both predicates
     * simultaneously. Filtering the full list first by evenPred (keeps multiples of 2) and then
     * by threePred (keeps multiples of 3) must yield exactly the multiples of 6.
     */
    @Test
    void testNestedSixes2() {
        final FilterListIterator<Integer> evensIterator =
                new FilterListIterator<>(list.listIterator(), evenPred);
        final FilterListIterator<Integer> sixesIterator =
                new FilterListIterator<>(evensIterator, threePred);
        walkLists(sixes, sixesIterator);
    }
}
