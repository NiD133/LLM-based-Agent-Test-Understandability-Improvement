package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.ListIterator;
import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that calling next() on a FilterListIterator updates the value
 * that will subsequently be returned by previous(), even when hasPrevious()
 * was called between the two next() invocations.
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testNextChangesPrevious {

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
    public void tearDown() {
        list = null;
        threes = null;
        truePred = null;
        threePred = null;
    }

    /**
     * Verifies the contract: after calling next() twice (with a hasPrevious() check in between),
     * the value returned by previous() must equal the value returned by the second next() call.
     *
     * Sequence:
     *   1. next()          — advance both iterators once
     *   2. hasPrevious()   — peek without moving the cursor
     *   3. next()          — advance again, capture returned value
     *   4. previous()      — must return the same value as step 3
     */
    private void assertNextUpdatesPrevious(
            final ListIterator<?> expected,
            final ListIterator<?> testing) {
        // Step 1: first next() — both iterators must agree on the returned element
        assertEquals(expected.next(), testing.next());

        // Step 2: hasPrevious() peek — must agree and must not shift the cursor
        assertEquals(expected.hasPrevious(), testing.hasPrevious());

        // Step 3: second next() — capture what each iterator returns
        final Object expectedSecondNext = expected.next();
        final Object actualSecondNext = testing.next();
        assertEquals(expectedSecondNext, actualSecondNext);

        // Step 4: previous() — must return the same element as the second next() call,
        // proving that next() updated the cursor so previous() reflects the new position
        final Object expectedPrev = expected.previous();
        final Object actualPrev = testing.previous();
        assertEquals(expectedSecondNext, expectedPrev);
        assertEquals(actualSecondNext, actualPrev);
    }

    @Test
    void testNextChangesPrevious() {
        // Scenario 1: filter keeps only multiples of three
        final FilterListIterator<Integer> filteredThrees =
                new FilterListIterator<>(list.listIterator(), threePred);
        assertNextUpdatesPrevious(threes.listIterator(), filteredThrees);

        // Scenario 2: filter accepts every element (truePred), so filtered == unfiltered
        final FilterListIterator<Integer> filteredAll =
                new FilterListIterator<>(list.listIterator(), truePred);
        assertNextUpdatesPrevious(list.listIterator(), filteredAll);
    }
}
