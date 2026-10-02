package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class ZippingIteratorTest_testIterateEven {

    private ArrayList<Integer> evens;

    @BeforeEach
    public void setUp() {
        evens = new ArrayList<>();
        for (int value = 0; value < 20; value++) {
            if (value % 2 == 0) {
                evens.add(value);
            }
        }
    }

    @Test
    void testIterateEven() {
        final ZippingIterator<Integer> iterator = new ZippingIterator<>(evens.iterator());

        for (final Integer expectedEven : evens) {
            assertTrue(iterator.hasNext());
            assertEquals(expectedEven, iterator.next());
        }
        assertFalse(iterator.hasNext());
    }
}
