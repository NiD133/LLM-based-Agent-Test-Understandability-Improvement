package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link SimpleBloomFilter#merge(Hasher)} correctly sets exactly the bits
 * indicated by the hasher's index output, deduplicating and sorting them.
 */
public class SimpleBloomFilterTest_testMergeWithHasher {

    /** Shape used across all tests: k=17 hash functions, m=72 bits. */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /**
     * Verifies that merging a {@link Hasher} into an empty {@link SimpleBloomFilter}
     * produces a filter whose bit indices exactly match the unique, sorted indices
     * that the hasher would produce.
     *
     * <p>The test repeats five times with independently generated random index sets
     * to guard against accidental passes caused by a lucky collision.
     */
    @Test
    void testMergeWithHasher() {
        for (int i = 0; i < 5; i++) {
            // Generate a random array of hash indices within the valid bit range.
            final int[] rawIndices = DefaultIndexExtractorTest.generateIntArray(
                    TEST_SHAPE.getNumberOfHashFunctions(),
                    TEST_SHAPE.getNumberOfBits());

            // ArrayHasher replays those raw indices (mod numberOfBits) when queried.
            final Hasher hasher = new ArrayHasher(rawIndices);

            // Merge the hasher into a fresh filter.
            final BloomFilter filter = new SimpleBloomFilter(TEST_SHAPE);
            filter.merge(hasher);

            // The filter must expose exactly the deduplicated, sorted set of indices.
            final int[] expectedIndices = DefaultIndexExtractorTest.unique(rawIndices);
            assertArrayEquals(expectedIndices, filter.asIndexArray(),
                    "Filter bit indices must match the unique sorted indices from the hasher");
        }
    }
}
