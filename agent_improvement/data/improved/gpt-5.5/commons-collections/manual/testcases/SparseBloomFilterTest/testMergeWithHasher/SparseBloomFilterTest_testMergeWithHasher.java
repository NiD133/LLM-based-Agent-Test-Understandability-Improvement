package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testMergeWithHasher {

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * The shape of the Bloom filters for testing.
     *
     * <ul>
     *   <li>Hash functions (k) = 17</li>
     *   <li>Number of bits (m) = 72</li>
     * </ul>
     *
     * @return the testing shape.
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    @Test
    void testMergeWithHasher() {
        for (int i = 0; i < 5; i++) {
            final BloomFilter<?> filter = createEmptyFilter(getTestShape());
            final int[] expected = DefaultIndexExtractorTest.generateIntArray(
                    getTestShape().getNumberOfHashFunctions(),
                    getTestShape().getNumberOfBits());
            final Hasher hasher = new ArrayHasher(expected);

            filter.merge(hasher);

            // A SparseBloomFilter stores generated indices in sorted unique order.
            assertArrayEquals(DefaultIndexExtractorTest.unique(expected), filter.asIndexArray());
        }
    }
}
