package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SimpleBloomFilter#merge(Hasher)} enables exactly the bits
 * produced by the hasher.
 */
public class SimpleBloomFilterTest_testMergeWithHasher {

    /** Number of times the merge scenario is repeated with fresh random indices. */
    private static final int REPETITIONS = 5;

    /**
     * The shape used for every filter in this test: 17 hash functions (k) over 72 bits (m).
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Creates an empty Bloom filter of the given shape.
     */
    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    @Test
    void testMergeWithHasher() {
        final Shape shape = getTestShape();

        for (int repetition = 0; repetition < REPETITIONS; repetition++) {
            // Generate the indices the hasher will produce, then merge them into an empty filter.
            final int[] hashedIndices = DefaultIndexExtractorTest.generateIntArray(
                    shape.getNumberOfHashFunctions(), shape.getNumberOfBits());
            final Hasher hasher = new ArrayHasher(hashedIndices);

            final BloomFilter filter = createEmptyFilter(shape);
            filter.merge(hasher);

            // The filter must contain exactly the sorted, de-duplicated set of hashed indices.
            final int[] expectedIndices = DefaultIndexExtractorTest.unique(hashedIndices);
            assertArrayEquals(expectedIndices, filter.asIndexArray());
        }
    }
}
