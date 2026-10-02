package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import org.apache.commons.collections4.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class FilterListIteratorTest_testManual {

    private ArrayList<Integer> list;

    private Predicate<Integer> threePred;

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(Integer.valueOf(i));
        }
        threePred = x -> x % 3 == 0;
    }

    private void assertNextValues(final FilterListIterator<Integer> filtered, final int... expectedValues) {
        for (final int expectedValue : expectedValues) {
            assertEquals(Integer.valueOf(expectedValue), filtered.next());
        }
    }

    private void assertPreviousValues(final FilterListIterator<Integer> filtered, final int... expectedValues) {
        for (final int expectedValue : expectedValues) {
            assertEquals(Integer.valueOf(expectedValue), filtered.previous());
        }
    }

    @Test
    void testManual() {
        final FilterListIterator<Integer> filtered = new FilterListIterator<>(list.listIterator(), threePred);

        assertNextValues(filtered, 0, 3, 6, 9, 12, 15, 18);
        assertPreviousValues(filtered, 18, 15, 12, 9, 6, 3, 0);
        assertFalse(filtered.hasPrevious());

        assertNextValues(filtered, 0, 3, 6, 9, 12, 15, 18);
        assertFalse(filtered.hasNext());

        assertPreviousValues(filtered, 18, 15, 12, 9, 6, 3, 0);

        assertNextValues(filtered, 0);
        assertPreviousValues(filtered, 0);
        assertNextValues(filtered, 0, 3, 6);
        assertPreviousValues(filtered, 6, 3);
        assertNextValues(filtered, 3, 6, 9, 12, 15);
        assertPreviousValues(filtered, 15, 12, 9);
    }
}
