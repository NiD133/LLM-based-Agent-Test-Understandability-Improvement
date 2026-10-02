package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.BitSet;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testMergeWithIndexExtractor {

    // Shape used across all tests: k=17 hash functions, m=72 bits
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    private SparseBloomFilter createFilter(final Shape shape, final IndexExtractor extractor) {
        final SparseBloomFilter bf = createEmptyFilter(shape);
        bf.merge(extractor);
        return bf;
    }

    @Test
    void testMergeWithIndexExtractor() {
        // Verify that a filter built from random indices contains exactly those indices
        for (int i = 0; i < 5; i++) {
            final int[] values = DefaultIndexExtractorTest.generateIntArray(
                    TEST_SHAPE.getNumberOfHashFunctions(), TEST_SHAPE.getNumberOfBits());
            final BloomFilter filter = createFilter(TEST_SHAPE, IndexExtractor.fromIndexArray(values));

            // Track which unique indices we expect; clear each one as it is visited
            final BitSet expectedIndices = DefaultIndexExtractorTest.uniqueSet(values);
            assertTrue(filter.processIndices(idx -> {
                final boolean present = expectedIndices.get(idx);
                expectedIndices.clear(idx);
                return present;
            }), "Filter contained an index not in the original values");
            assertTrue(expectedIndices.isEmpty(), "Not all expected indices were found in the filter");
        }

        // An index equal to the number of bits (out-of-range high) must be rejected
        final BloomFilter filterWithOversizedIndex = createEmptyFilter(TEST_SHAPE);
        assertThrows(IllegalArgumentException.class,
                () -> filterWithOversizedIndex.merge(IndexExtractor.fromIndexArray(TEST_SHAPE.getNumberOfBits())));

        // A negative index must be rejected
        final BloomFilter filterWithNegativeIndex = createEmptyFilter(TEST_SHAPE);
        assertThrows(IllegalArgumentException.class,
                () -> filterWithNegativeIndex.merge(IndexExtractor.fromIndexArray(-1)));
    }
}
