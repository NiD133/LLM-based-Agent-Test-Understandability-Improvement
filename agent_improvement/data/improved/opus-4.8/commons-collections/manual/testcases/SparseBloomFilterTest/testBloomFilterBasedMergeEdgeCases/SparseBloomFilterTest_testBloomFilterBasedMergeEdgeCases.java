package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that merging a {@link BloomFilter} into a {@link SparseBloomFilter}
 * reproduces exactly the same bits as the source filter.
 */
public class SparseBloomFilterTest_testBloomFilterBasedMergeEdgeCases {

    /** Shape used for every filter in this test: 17 hash functions over 72 bits. */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    @Test
    void testBloomFilterBasedMergeEdgeCases() {
        // The target filter under test, initially empty.
        final SparseBloomFilter target = new SparseBloomFilter(TEST_SHAPE);

        // A populated source filter built from a known hasher.
        final BloomFilter source = new SimpleBloomFilter(TEST_SHAPE);
        source.merge(TestingHashers.FROM1);

        // Merge the source into the target.
        target.merge(source);

        // After merging, every bit-map word of the source must match the target.
        assertTrue(source.processBitMapPairs(target, (sourceWord, targetWord) -> sourceWord == targetWord));
    }
}
