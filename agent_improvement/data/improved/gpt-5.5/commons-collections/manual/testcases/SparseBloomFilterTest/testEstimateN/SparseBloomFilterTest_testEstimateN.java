package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testEstimateN {

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    /**
     * The shared shape used by the original SparseBloomFilter tests:
     * k = 17 hash functions, m = 72 bits.
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Tests that the size estimate is correctly calculated as additional hashers
     * are merged and when every bit in the filter is populated.
     */
    @Test
    final void testEstimateN() {
        BloomFilter<?> filter1 = createFilter(getTestShape(), TestingHashers.FROM1);
        assertEquals(1, filter1.estimateN());

        // These data do not generate an estimate equivalent to the actual count.
        filter1.merge(new IncrementingHasher(4, 1));
        assertEquals(1, filter1.estimateN());

        filter1.merge(new IncrementingHasher(17, 1));
        assertEquals(3, filter1.estimateN());

        filter1 = TestingHashers.populateEntireFilter(createEmptyFilter(getTestShape()));
        assertEquals(Integer.MAX_VALUE, filter1.estimateN());
    }
}
