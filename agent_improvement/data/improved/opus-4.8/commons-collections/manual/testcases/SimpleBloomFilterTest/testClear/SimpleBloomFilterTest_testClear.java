package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SimpleBloomFilter#clear()} resets the filter back to an
 * empty state.
 */
public class SimpleBloomFilterTest_testClear {

    /**
     * The shape used for the test filters: 17 hash functions (k) over 72 bits (m).
     */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /**
     * Builds a populated filter, then confirms that {@code clear()} drops its
     * cardinality back to zero.
     */
    @Test
    void testClear() {
        // Create a filter and merge a hasher so that some bits are set.
        final SimpleBloomFilter filter = new SimpleBloomFilter(TEST_SHAPE);
        filter.merge(TestingHashers.FROM1);

        // Sanity check: the filter is non-empty before clearing.
        assertNotEquals(0, filter.cardinality());

        // Clearing must remove every set bit.
        filter.clear();
        assertEquals(0, filter.cardinality());
    }
}
