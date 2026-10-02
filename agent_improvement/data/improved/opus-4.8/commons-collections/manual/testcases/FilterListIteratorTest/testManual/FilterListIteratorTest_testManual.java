package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Hand-written sanity check for {@link FilterListIterator}.
 *
 * <p>The iterator wraps the numbers 0..19 and keeps only the multiples of
 * three: {@code 0, 3, 6, 9, 12, 15, 18}. The test walks this filtered view
 * forward and backward in various patterns and verifies that {@code next()}
 * and {@code previous()} always return the neighbouring matching element.</p>
 */
@SuppressWarnings("boxing")
public class FilterListIteratorTest_testManual {

    /** The backing list of numbers 0..19 that the iterator filters. */
    private List<Integer> numbers;

    /** Keeps only multiples of three. */
    private final Predicate<Integer> multipleOfThree = value -> value % 3 == 0;

    @BeforeEach
    public void setUp() {
        numbers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            numbers.add(Integer.valueOf(i));
        }
    }

    @Test
    void testManual() {
        // The filtered view exposes only the multiples of three: 0, 3, 6, 9, 12, 15, 18.
        final FilterListIterator<Integer> filtered =
                new FilterListIterator<>(numbers.listIterator(), multipleOfThree);

        // Walk all the way forward to the end.
        assertEquals(Integer.valueOf(0), filtered.next());
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());
        assertEquals(Integer.valueOf(9), filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(18), filtered.next());

        // Walk all the way back to the start.
        assertEquals(Integer.valueOf(18), filtered.previous());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9), filtered.previous());
        assertEquals(Integer.valueOf(6), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.previous());
        assertEquals(Integer.valueOf(0), filtered.previous());
        assertFalse(filtered.hasPrevious());

        // Walk forward again to the end.
        assertEquals(Integer.valueOf(0), filtered.next());
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());
        assertEquals(Integer.valueOf(9), filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(18), filtered.next());
        assertFalse(filtered.hasNext());

        // And back to the start once more.
        assertEquals(Integer.valueOf(18), filtered.previous());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9), filtered.previous());
        assertEquals(Integer.valueOf(6), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.previous());
        assertEquals(Integer.valueOf(0), filtered.previous());

        // next() then previous() at the start should return the same element.
        assertEquals(Integer.valueOf(0), filtered.next());
        assertEquals(Integer.valueOf(0), filtered.previous());

        // Step forward two, then back two in the middle of the sequence.
        assertEquals(Integer.valueOf(0), filtered.next());
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.previous());
        assertEquals(Integer.valueOf(3), filtered.previous());

        // Resume walking forward, then partially back.
        assertEquals(Integer.valueOf(3), filtered.next());
        assertEquals(Integer.valueOf(6), filtered.next());
        assertEquals(Integer.valueOf(9), filtered.next());
        assertEquals(Integer.valueOf(12), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.next());
        assertEquals(Integer.valueOf(15), filtered.previous());
        assertEquals(Integer.valueOf(12), filtered.previous());
        assertEquals(Integer.valueOf(9), filtered.previous());
    }
}
