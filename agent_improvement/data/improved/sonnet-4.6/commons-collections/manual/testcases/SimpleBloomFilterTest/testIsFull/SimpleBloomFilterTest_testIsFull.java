package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testIsFull {

    /**
     * The test shape: k=17 hash functions, m=72 bits.
     */
    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    protected SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter bf = createEmptyFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    /**
     * Tests that isFull() correctly reports whether all bits in the filter are set.
     *
     * Verifies three states:
     * 1. A newly created empty filter is not full.
     * 2. A filter with every bit set is full.
     * 3. A partially filled filter (only some bits set) is not full.
     */
    @Test
    final void testIsFull() {
        // An empty filter should not be full
        BloomFilter filter = createEmptyFilter(getTestShape());
        assertFalse(filter.isFull(), "Should not be full");

        // After setting every bit, the filter should be full
        filter = TestingHashers.populateEntireFilter(filter);
        assertTrue(filter.isFull(), "Should be full");

        // A filter with only some bits set (via IncrementingHasher(1,3)) should not be full
        filter = createFilter(getTestShape(), new IncrementingHasher(1, 3));
        assertFalse(filter.isFull(), "Should not be full");
    }
}
