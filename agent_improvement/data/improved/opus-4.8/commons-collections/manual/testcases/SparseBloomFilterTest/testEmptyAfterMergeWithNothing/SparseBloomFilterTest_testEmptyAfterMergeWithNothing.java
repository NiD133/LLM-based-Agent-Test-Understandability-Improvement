package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test that a {@link SparseBloomFilter} reports itself as empty after merging
 * an empty {@link IndexExtractor} into it.
 */
public class SparseBloomFilterTest_testEmptyAfterMergeWithNothing {

    /** Shape used for the filter under test: 17 hash functions, 72 bits. */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /** Creates an empty SparseBloomFilter with the given shape. */
    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    @Test
    void testEmptyAfterMergeWithNothing() {
        // Merging an extractor that supplies no indices must leave the filter empty.
        final BloomFilter bf = createEmptyFilter(getTestShape());
        bf.merge(IndexExtractor.fromIndexArray());
        assertTrue(bf.isEmpty());
    }
}
