package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class ZippingIteratorTest_testIterateOddEven {

    private static final int VALUE_COUNT = 20;

    private List<Integer> evens;
    private List<Integer> odds;

    @BeforeEach
    public void setUp() {
        evens = new ArrayList<>();
        odds = new ArrayList<>();

        for (int value = 0; value < VALUE_COUNT; value++) {
            if (value % 2 == 0) {
                evens.add(value);
            } else {
                odds.add(value);
            }
        }
    }

    @Test
    void testIterateOddEven() {
        final ZippingIterator<Integer> iterator = new ZippingIterator<>(odds.iterator(), evens.iterator());

        for (int position = 0, pairIndex = 0; position < VALUE_COUNT; position++) {
            assertTrue(iterator.hasNext());

            final int actualValue = iterator.next();
            if (position % 2 == 0) {
                assertEquals(odds.get(pairIndex).intValue(), actualValue);
            } else {
                assertEquals(evens.get(pairIndex).intValue(), actualValue);
                pairIndex++;
            }
        }

        assertFalse(iterator.hasNext());
    }
}
