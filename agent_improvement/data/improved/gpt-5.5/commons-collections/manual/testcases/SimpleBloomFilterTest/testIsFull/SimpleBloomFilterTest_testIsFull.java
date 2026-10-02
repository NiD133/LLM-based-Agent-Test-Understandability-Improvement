package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testIsFull {

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    protected final SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter bloomFilter = createEmptyFilter(shape);
        bloomFilter.merge(hasher);
        return bloomFilter;
    }

    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Tests that isFull() returns the proper values.
     */
    @Test
    final void testIsFull() {
        BloomFilter filter = createEmptyFilter(getTestShape());
        assertFalse(filter.isFull(), "Should not be full");

        filter = TestingHashers.populateEntireFilter(filter);
        assertTrue(filter.isFull(), "Should be full");

        filter = createFilter(getTestShape(), new IncrementingHasher(1, 3));
        assertFalse(filter.isFull(), "Should not be full");
    }
}
