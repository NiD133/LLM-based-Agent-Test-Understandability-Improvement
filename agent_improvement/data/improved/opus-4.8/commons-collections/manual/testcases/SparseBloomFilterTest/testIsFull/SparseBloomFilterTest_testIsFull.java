package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SparseBloomFilter#isFull()}.
 *
 * <p>A filter is "full" only when every bit defined by its shape is set. The test
 * shape has 72 bits (see {@link #getTestShape()}).</p>
 */
public class SparseBloomFilterTest_testIsFull {

    /**
     * Shape used for every filter in this test: 17 hash functions (k) over 72 bits (m).
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Creates an empty {@link SparseBloomFilter} for the given shape.
     */
    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * Creates a filter for the given shape and populates it from the supplied hasher.
     */
    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    @Test
    final void testIsFull() {
        // A brand-new filter has no bits set, so it is not full.
        BloomFilter filter = createEmptyFilter(getTestShape());
        assertFalse(filter.isFull(), "A newly created filter should not be full");

        // Setting every bit defined by the shape makes the filter full.
        filter = TestingHashers.populateEntireFilter(filter);
        assertTrue(filter.isFull(), "A filter with every bit set should be full");

        // A filter populated from a single hasher sets only a few bits, so it is not full.
        filter = createFilter(getTestShape(), new IncrementingHasher(1, 3));
        assertFalse(filter.isFull(), "A partially populated filter should not be full");
    }
}
