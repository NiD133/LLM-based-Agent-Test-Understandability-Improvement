package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SparseBloomFilter#estimateUnion(BloomFilter)}.
 */
public class SparseBloomFilterTest_testEstimateUnion {

    /**
     * The shape used for every filter in this test: k = 17 hash functions over m = 72 bits.
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Creates an empty sparse filter with the given shape.
     */
    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * Creates a sparse filter populated by merging the given hasher.
     */
    private BloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    /**
     * The estimated size of a union should match the number of distinct items the
     * combined filters represent, and be symmetric in its two operands.
     */
    @Test
    final void testEstimateUnion() {
        final BloomFilter filterFrom1 = createFilter(getTestShape(), TestingHashers.FROM1);
        final BloomFilter filterFrom11 = createFilter(getTestShape(), TestingHashers.FROM11);

        // Two filters built from different single items -> union estimates two items.
        assertEquals(2, filterFrom1.estimateUnion(filterFrom11));
        assertEquals(2, filterFrom11.estimateUnion(filterFrom1));

        final BloomFilter emptyFilter = createEmptyFilter(getTestShape());

        // Union with an empty filter contributes nothing -> union estimates one item.
        assertEquals(1, filterFrom1.estimateUnion(emptyFilter));
        assertEquals(1, emptyFilter.estimateUnion(filterFrom1));
    }
}
