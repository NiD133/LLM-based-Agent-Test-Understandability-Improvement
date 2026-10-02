package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testIndexExtractorMerge {

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * Builds a SparseBloomFilter from the given index array and merges it into a new filter.
     */
    private BloomFilter createFilter(final Shape shape, final IndexExtractor extractor) {
        final SparseBloomFilter bf = createEmptyFilter(shape);
        bf.merge(extractor);
        return bf;
    }

    /**
     * Asserts that creating a filter with the given indices throws IllegalArgumentException.
     * Used to verify that out-of-range (negative or too-large) indices are rejected.
     */
    private void assertFailedIndexExtractorConstructor(final Shape shape, final int[] values) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(values);
        assertThrows(IllegalArgumentException.class, () -> createFilter(shape, indices));
    }

    /**
     * Asserts that a filter built from {@code values} contains exactly the indices in
     * {@code expected} (duplicates in input are collapsed).
     */
    private void assertIndexExtractorMerge(final Shape shape, final int[] values, final int[] expected) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(values);
        final BloomFilter filter = createFilter(shape, indices);

        final List<Integer> actualIndices = new ArrayList<>();
        filter.processIndices(x -> {
            actualIndices.add(x);
            return true;
        });

        assertEquals(expected.length, actualIndices.size());
        for (final int value : expected) {
            assertTrue(actualIndices.contains(Integer.valueOf(value)), "Missing index: " + value);
        }
    }

    @Test
    void testIndexExtractorMerge() {
        // Shape: k=5 hash functions, m=10 bits → valid indices are 0..9
        final Shape shape = Shape.fromKM(5, 10);

        // Distinct valid indices are stored as-is
        assertIndexExtractorMerge(shape, new int[] { 0, 2, 4, 6, 8 }, new int[] { 0, 2, 4, 6, 8 });

        // Duplicate indices (2 appears twice) are deduplicated
        assertIndexExtractorMerge(shape, new int[] { 0, 2, 4, 2, 8 }, new int[] { 0, 2, 4, 8 });

        // Negative index must be rejected
        assertFailedIndexExtractorConstructor(shape, new int[] { 0, 2, 4, -2, 8 });

        // Index beyond shape capacity must be rejected
        assertFailedIndexExtractorConstructor(shape, new int[] { 0, 2, 4, 12, 8 });

        // Empty index array produces an empty filter
        assertIndexExtractorMerge(shape, new int[0], new int[0]);
    }
}
