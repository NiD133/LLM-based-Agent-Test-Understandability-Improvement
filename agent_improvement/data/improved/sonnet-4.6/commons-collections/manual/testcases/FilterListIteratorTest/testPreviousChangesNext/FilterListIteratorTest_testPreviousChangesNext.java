package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class FilterListIteratorTest_testPreviousChangesNext {

    private ArrayList<Integer> list;
    private ArrayList<Integer> threes;

    private Predicate<Integer> truePred;
    private Predicate<Integer> threePred;

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        threes = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(Integer.valueOf(i));
            if (i % 3 == 0) {
                threes.add(Integer.valueOf(i));
            }
        }
        truePred = x -> true;
        threePred = x -> x % 3 == 0;
    }

    @AfterEach
    public void tearDown() throws Exception {
        list = null;
        threes = null;
        truePred = null;
        threePred = null;
    }

    /**
     * Advances both iterators by one step forward, checks that hasPrevious() agrees,
     * then advances one more step forward and verifies that calling previous() returns
     * the element that was just returned by next() — confirming that next() updates the
     * previous-element cursor correctly.
     */
    private void nextNextPrevious(final ListIterator<?> expected, final ListIterator<?> testing) {
        // calls to next() should change the value returned by previous()
        // even after previous() has been set by a call to hasPrevious()
        assertEquals(expected.next(), testing.next());
        assertEquals(expected.hasPrevious(), testing.hasPrevious());
        final Object firstExpectedNext = expected.next();
        final Object firstFilteredNext = testing.next();
        assertEquals(firstExpectedNext, firstFilteredNext);
        final Object secondExpectedPrevious = expected.previous();
        final Object secondFilteredPrevious = testing.previous();
        assertEquals(firstExpectedNext, secondExpectedPrevious);
        assertEquals(firstFilteredNext, secondFilteredPrevious);
    }

    /**
     * Steps both iterators backward once, checks that hasNext() agrees, then steps
     * backward again and verifies that calling next() returns the element that was just
     * returned by previous() — confirming that previous() updates the next-element cursor
     * correctly.
     */
    private void previousPreviousNext(final ListIterator<?> expected, final ListIterator<?> testing) {
        // calls to previous() should change the value returned by next()
        // even after next() has been set by a call to hasNext()
        assertEquals(expected.previous(), testing.previous());
        assertEquals(expected.hasNext(), testing.hasNext());
        final Object firstExpectedPrevious = expected.previous();
        final Object firstFilteredPrevious = testing.previous();
        assertEquals(firstExpectedPrevious, firstFilteredPrevious);
        final Object secondExpectedNext = expected.next();
        final Object secondFilteredNext = testing.next();
        assertEquals(firstExpectedPrevious, secondFilteredNext);
        assertEquals(firstExpectedPrevious, secondExpectedNext);
        assertEquals(firstFilteredPrevious, secondFilteredNext);
    }

    /**
     * Walks both iterators forward in lockstep, asserting that each step produces
     * matching indices and elements.
     */
    private void walkForward(final ListIterator<?> expected, final ListIterator<?> testing) {
        while (expected.hasNext()) {
            assertEquals(expected.nextIndex(), testing.nextIndex());
            assertEquals(expected.previousIndex(), testing.previousIndex());
            assertTrue(testing.hasNext());
            assertEquals(expected.next(), testing.next());
        }
    }

    @Test
    void testPreviousChangesNext() {
        // Scenario 1: filter by multiples-of-three predicate.
        // Walk forward through all matching elements, then verify that calling previous()
        // correctly updates the cursor so next() returns the right element.
        {
            final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), threePred);
            final ListIterator<Integer> expected = threes.listIterator();
            walkForward(expected, filtered);
            previousPreviousNext(expected, filtered);
        }

        // Scenario 2: truePred passes every element, so the filtered iterator should
        // behave identically to a plain iterator over the full list.
        {
            final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), truePred);
            final ListIterator<Integer> expected = list.listIterator();
            walkForward(expected, filtered);
            previousPreviousNext(expected, filtered);
        }
    }
}
