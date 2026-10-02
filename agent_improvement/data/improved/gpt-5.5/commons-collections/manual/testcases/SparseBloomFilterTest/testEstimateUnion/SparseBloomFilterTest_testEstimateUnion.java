package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testEstimateUnion {

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Tests that estimated union calculations are symmetric and handle an empty
     * filter without changing the expected estimate.
     */
    @Test
    final void testEstimateUnion() {
        final BloomFilter<?> bf = createFilter(getTestShape(), TestingHashers.FROM1);
        final BloomFilter<?> bf2 = createFilter(getTestShape(), TestingHashers.FROM11);
        assertEquals(2, bf.estimateUnion(bf2));
        assertEquals(2, bf2.estimateUnion(bf));

        final BloomFilter<?> bf3 = createEmptyFilter(getTestShape());
        assertEquals(1, bf.estimateUnion(bf3));
        assertEquals(1, bf3.estimateUnion(bf));
    }
}
