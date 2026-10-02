package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link ZippingIterator} which interleaves a single source
 * iterator with itself yields each element twice in succession.
 */
@SuppressWarnings("boxing")
public class ZippingIteratorTest_testIterateEvenEven {

    /** The even numbers in the range [0, 20): 0, 2, 4, ..., 18. */
    private List<Integer> evens;

    @BeforeEach
    public void setUp() {
        evens = new ArrayList<>();
        for (int i = 0; i < 20; i += 2) {
            evens.add(i);
        }
    }

    /**
     * Zipping the {@code evens} iterator with a second iterator over the same
     * list interleaves the two streams. Because both streams are identical and
     * advance in lock-step, every value appears twice back-to-back before the
     * combined iterator is exhausted.
     */
    @Test
    void testIterateEvenEven() {
        final ZippingIterator<Integer> iter =
                new ZippingIterator<>(evens.iterator(), evens.iterator());

        for (final Integer even : evens) {
            assertTrue(iter.hasNext());
            assertEquals(even, iter.next());
            assertTrue(iter.hasNext());
            assertEquals(even, iter.next());
        }
        assertFalse(iter.hasNext());
    }
}
