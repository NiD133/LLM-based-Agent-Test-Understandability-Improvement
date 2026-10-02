package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Reproduces a historical bug where, after a {@link FilterListIterator} had been
 * walked all the way forward, a call to {@code hasNext()} could corrupt the
 * iterator's state so that the following {@code previous()} returned the wrong
 * element.
 *
 * <p>The iterator under test filters the numbers {@code 0..19} down to the
 * multiples of four ({@code 0, 4, 8, 12, 16}). A plain {@link ListIterator}
 * over the expected multiples of four serves as the reference for the assertions.</p>
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testFailingHasNextBug {

    /** The full source list of numbers {@code 0..19} that gets filtered. */
    private List<Integer> numbers;

    /** The expected filtered result: every multiple of four in {@code 0..19}. */
    private List<Integer> multiplesOfFour;

    /** Predicate accepting only multiples of four. */
    private Predicate<Integer> isMultipleOfFour;

    @BeforeEach
    public void setUp() {
        numbers = new ArrayList<>();
        multiplesOfFour = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            numbers.add(i);
            if (i % 4 == 0) {
                multiplesOfFour.add(i);
            }
        }
        isMultipleOfFour = value -> value % 4 == 0;
    }

    @Test
    void testFailingHasNextBug() {
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(numbers.listIterator(), isMultipleOfFour);
        final ListIterator<Integer> expected = multiplesOfFour.listIterator();

        // Walk both iterators forward until the expected one is exhausted.
        while (expected.hasNext()) {
            expected.next();
            filtered.next();
        }

        // At the end the filtered iterator must report a previous element but no next.
        assertTrue(filtered.hasPrevious());
        assertFalse(filtered.hasNext());

        // The hasNext() call above must not corrupt the backward step.
        assertEquals(expected.previous(), filtered.previous());
    }
}
