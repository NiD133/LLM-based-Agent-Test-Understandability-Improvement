package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Regression test for the hasNext() bug in FilterListIterator.
 *
 * The bug: after iterating forward through all matching elements and then
 * calling hasPrevious(), a subsequent call to hasNext() would incorrectly
 * return true even though the iterator was already at the end.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testFailingHasNextBug {

    /** Source list containing integers 0..19. */
    private ArrayList<Integer> list;

    /** Expected filtered results: multiples of 4 in 0..19 (i.e. 0, 4, 8, 12, 16). */
    private ArrayList<Integer> fours;

    /** Predicate that accepts only multiples of 4. */
    private Predicate<Integer> fourPred;

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        fours = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(Integer.valueOf(i));
            if (i % 4 == 0) {
                fours.add(Integer.valueOf(i));
            }
        }
        fourPred = x -> x % 4 == 0;
    }

    @AfterEach
    public void tearDown() {
        list = null;
        fours = null;
        fourPred = null;
    }

    /**
     * Verifies that after exhausting all filtered elements by stepping forward,
     * the iterator correctly reports hasPrevious()=true and hasNext()=false,
     * and that previous() returns the last matched element.
     *
     * Before the bug fix, calling hasPrevious() would corrupt the lookahead
     * state so that a following hasNext() call incorrectly returned true.
     */
    @Test
    void testFailingHasNextBug() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), fourPred);
        final ListIterator<Integer> expected = fours.listIterator();

        // Advance both iterators all the way to the end.
        while (expected.hasNext()) {
            expected.next();
            filtered.next();
        }

        // At this point both iterators are positioned after the last matching element (16).
        // The filtered iterator must recognise that there is a previous element but no next one.
        assertTrue(filtered.hasPrevious());
        assertFalse(filtered.hasNext());

        // Stepping backward must yield the same last element from both iterators.
        assertEquals(expected.previous(), filtered.previous());
    }
}
