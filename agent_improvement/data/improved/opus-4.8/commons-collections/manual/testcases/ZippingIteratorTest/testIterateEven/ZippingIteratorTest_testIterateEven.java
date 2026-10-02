package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link ZippingIterator} wrapping a single child iterator
 * simply replays that child's elements, in order, without any interleaving.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testIterateEven {

    /** The even numbers 0, 2, 4, ... 18 — the single child iterator's contents. */
    private List<Integer> evens;

    @BeforeEach
    public void setUp() {
        evens = new ArrayList<>();
        for (int i = 0; i < 20; i += 2) {
            evens.add(i);
        }
    }

    @Test
    void testIterateEven() {
        @SuppressWarnings("unchecked")
        final ZippingIterator<Integer> iter = new ZippingIterator<>(evens.iterator());

        // With only one child iterator, zipping yields that child's elements unchanged.
        for (final Integer expected : evens) {
            assertTrue(iter.hasNext());
            assertEquals(expected, iter.next());
        }
        assertFalse(iter.hasNext());
    }
}
