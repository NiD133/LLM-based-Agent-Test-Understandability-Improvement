package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testEstimateUnion {

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Tests that the estimated union calculations are correct.
     */
    @Test
    final void testEstimateUnion() {
        final Shape shape = getTestShape();
        final BloomFilter<?> firstFilter = createFilter(shape, TestingHashers.FROM1);
        final BloomFilter<?> secondFilter = createFilter(shape, TestingHashers.FROM11);

        assertEquals(2, firstFilter.estimateUnion(secondFilter));
        assertEquals(2, secondFilter.estimateUnion(firstFilter));

        final BloomFilter<?> emptyFilter = createEmptyFilter(shape);
        assertEquals(1, firstFilter.estimateUnion(emptyFilter));
        assertEquals(1, emptyFilter.estimateUnion(firstFilter));
    }
}
