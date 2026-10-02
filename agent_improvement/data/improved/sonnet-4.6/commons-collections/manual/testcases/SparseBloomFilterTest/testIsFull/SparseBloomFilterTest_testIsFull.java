package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testIsFull {

    /**
     * Returns the standard test shape: k=17 hash functions, m=72 bits.
     */
    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Creates a new empty SparseBloomFilter for the given shape.
     */
    protected SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * Creates a SparseBloomFilter pre-populated from the given hasher.
     */
    protected BloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final BloomFilter bf = createEmptyFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    /**
     * Verifies that {@code isFull()} correctly reports:
     * <ul>
     *   <li>false for a newly created, empty filter</li>
     *   <li>true after every bit in the shape has been set</li>
     *   <li>false for a filter that has only some bits set</li>
     * </ul>
     */
    @Test
    final void testIsFull() {
        // An empty filter must not be full.
        BloomFilter filter = createEmptyFilter(getTestShape());
        assertFalse(filter.isFull(), "Should not be full");

        // After populating every bit the filter must be full.
        filter = TestingHashers.populateEntireFilter(filter);
        assertTrue(filter.isFull(), "Should be full");

        // A filter built from a partial hasher must not be full.
        filter = createFilter(getTestShape(), new IncrementingHasher(1, 3));
        assertFalse(filter.isFull(), "Should not be full");
    }
}
