package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that merging a {@link Hasher} into a {@link SparseBloomFilter} enables exactly
 * the bits the hasher produces.
 */
public class SparseBloomFilterTest_testMergeWithHasher {

    /** The shape under test: 17 hash functions over 72 bits. */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SparseBloomFilter createEmptyFilter() {
        return new SparseBloomFilter(TEST_SHAPE);
    }

    @Test
    void testMergeWithHasher() {
        // Repeat with several randomly generated index sets to exercise different bit patterns.
        for (int repetition = 0; repetition < 5; repetition++) {
            final SparseBloomFilter filter = createEmptyFilter();

            // Generate k random indices in the range [0, m) and merge them via an ArrayHasher.
            final int[] hashedIndices = DefaultIndexExtractorTest.generateIntArray(
                    TEST_SHAPE.getNumberOfHashFunctions(), TEST_SHAPE.getNumberOfBits());
            filter.merge(new ArrayHasher(hashedIndices));

            // The filter should hold the sorted, de-duplicated set of the merged indices.
            final int[] expectedIndices = DefaultIndexExtractorTest.unique(hashedIndices);
            assertArrayEquals(expectedIndices, filter.asIndexArray());
        }
    }
}
