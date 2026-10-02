package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link SparseBloomFilter#estimateUnion(BloomFilter)} returns the
 * correct estimated number of distinct items represented by the union of two
 * Bloom filters.
 */
public class SparseBloomFilterTest_testEstimateUnion {

    // Shape matching the standard test configuration: k=17 hash functions, m=72 bits
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SparseBloomFilter createEmptyFilter() {
        return new SparseBloomFilter(TEST_SHAPE);
    }

    private SparseBloomFilter createFilter(final Hasher hasher) {
        final SparseBloomFilter bf = createEmptyFilter();
        bf.merge(hasher);
        return bf;
    }

    /**
     * Verifies that estimateUnion correctly computes the estimated number of
     * distinct items across two populated filters and is symmetric, and that
     * the union with an empty filter equals the non-empty filter's own estimate.
     */
    @Test
    void testEstimateUnion() {
        // Two filters each populated with a single distinct item (FROM1 and FROM11)
        final BloomFilter filterA = createFilter(TestingHashers.FROM1);
        final BloomFilter filterB = createFilter(TestingHashers.FROM11);

        // Union of two single-item filters should estimate 2 distinct items
        assertEquals(2, filterA.estimateUnion(filterB), "Union of two distinct single-item filters should estimate 2");
        // estimateUnion must be symmetric
        assertEquals(2, filterB.estimateUnion(filterA), "estimateUnion must be symmetric");

        // Union with an empty filter should equal the non-empty filter's estimate (1 item)
        final BloomFilter emptyFilter = createEmptyFilter();
        assertEquals(1, filterA.estimateUnion(emptyFilter), "Union with empty filter should equal the non-empty filter's estimate");
        assertEquals(1, emptyFilter.estimateUnion(filterA), "Union with empty filter must be symmetric");
    }
}
