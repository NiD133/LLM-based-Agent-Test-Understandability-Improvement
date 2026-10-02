package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link SparseBloomFilter#merge(Hasher)} correctly populates the
 * filter's internal index set from a hasher's output.
 *
 * <p>The filter under test uses {@link Shape#fromKM(int, int)} with
 * k=17 hash functions and m=72 bits.
 *
 * <p>After merging a hasher, the filter must contain exactly the
 * distinct, sorted bit-indices that the hasher produced — duplicates
 * are deduplicated and order is normalised, matching
 * {@link BloomFilter#asIndexArray()}.
 */
public class SparseBloomFilterTest_testMergeWithHasher {

    /** The shape shared by all filters in this test class: k=17, m=72. */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * Verifies that merging a {@link Hasher} into an empty
     * {@link SparseBloomFilter} sets exactly the bit-indices produced by the
     * hasher, deduplicated and in ascending order.
     *
     * <p>The loop runs five times with independently generated random index
     * arrays so the assertion is exercised across different input patterns.
     */
    @Test
    void testMergeWithHasher() {
        for (int i = 0; i < 5; i++) {
            // Generate a random array of raw indices with one entry per hash function.
            final int[] rawIndices = DefaultIndexExtractorTest.generateIntArray(
                    TEST_SHAPE.getNumberOfHashFunctions(),
                    TEST_SHAPE.getNumberOfBits());

            // Wrap the raw indices in a Hasher so the filter can consume them.
            final Hasher hasher = new ArrayHasher(rawIndices);

            final SparseBloomFilter filter = createEmptyFilter(TEST_SHAPE);
            filter.merge(hasher);

            // The filter must store the same indices as the hasher produced,
            // but deduplicated and sorted — matching asIndexArray() contract.
            final int[] expectedIndices = DefaultIndexExtractorTest.unique(rawIndices);
            assertArrayEquals(expectedIndices, filter.asIndexArray());
        }
    }
}
