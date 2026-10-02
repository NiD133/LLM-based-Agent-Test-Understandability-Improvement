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

    private BloomFilter createFilter(final Shape shape, final IndexExtractor extractor) {
        final SparseBloomFilter bf = createEmptyFilter(shape);
        bf.merge(extractor);
        return bf;
    }

    /**
     * Verifies that merging the given index values into a filter of the given shape
     * results in exactly the expected set of active indices.
     */
    private void assertIndexExtractorMerge(final Shape shape, final int[] values, final int[] expected) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(values);
        final BloomFilter filter = createFilter(shape, indices);

        final List<Integer> actualIndices = new ArrayList<>();
        filter.processIndices(index -> {
            actualIndices.add(index);
            return true;
        });

        assertEquals(expected.length, actualIndices.size());
        for (final int value : expected) {
            assertTrue(actualIndices.contains(Integer.valueOf(value)), "Missing " + value);
        }
    }

    /**
     * Verifies that constructing a filter from the given index values throws
     * IllegalArgumentException (e.g. for negative or out-of-range indices).
     */
    private void assertFailedIndexExtractorConstructor(final Shape shape, final int[] values) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(values);
        assertThrows(IllegalArgumentException.class, () -> createFilter(shape, indices));
    }

    @Test
    void testIndexExtractorMerge() {
        final Shape shape = Shape.fromKM(5, 10);

        // normal distinct indices are stored as-is
        assertIndexExtractorMerge(shape, new int[] { 0, 2, 4, 6, 8 }, new int[] { 0, 2, 4, 6, 8 });

        // duplicate index values are deduplicated
        assertIndexExtractorMerge(shape, new int[] { 0, 2, 4, 2, 8 }, new int[] { 0, 2, 4, 8 });

        // negative indices are rejected
        assertFailedIndexExtractorConstructor(shape, new int[] { 0, 2, 4, -2, 8 });

        // indices exceeding the shape's bit count are rejected
        assertFailedIndexExtractorConstructor(shape, new int[] { 0, 2, 4, 12, 8 });

        // empty index array produces an empty filter
        assertIndexExtractorMerge(shape, new int[0], new int[0]);
    }
}
