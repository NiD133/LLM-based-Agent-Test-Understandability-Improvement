package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testMergeWithHasher {

    private static final int HASH_FUNCTION_COUNT = 17;
    private static final int BIT_COUNT = 72;
    private static final int SAMPLE_COUNT = 5;

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    private Shape getTestShape() {
        return Shape.fromKM(HASH_FUNCTION_COUNT, BIT_COUNT);
    }

    @Test
    void testMergeWithHasher() {
        for (int i = 0; i < SAMPLE_COUNT; i++) {
            final BloomFilter f = createEmptyFilter(getTestShape());
            final int[] expected = DefaultIndexExtractorTest.generateIntArray(
                    getTestShape().getNumberOfHashFunctions(),
                    getTestShape().getNumberOfBits());
            final Hasher hasher = new ArrayHasher(expected);

            f.merge(hasher);

            assertArrayEquals(DefaultIndexExtractorTest.unique(expected), f.asIndexArray());
        }
    }
}
