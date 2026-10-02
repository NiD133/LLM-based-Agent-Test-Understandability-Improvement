package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testIndexExtractorMerge {

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    private SimpleBloomFilter createFilter(final Shape shape, final IndexExtractor extractor) {
        final SimpleBloomFilter bf = createEmptyFilter(shape);
        bf.merge(extractor);
        return bf;
    }

    /**
     * Verifies that merging {@code inputIndices} into a filter produces exactly
     * the bits described by {@code expectedIndices} (duplicates collapsed).
     */
    private void assertIndexExtractorMerge(final Shape shape, final int[] inputIndices, final int[] expectedIndices) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(inputIndices);
        final SimpleBloomFilter filter = createFilter(shape, indices);

        final List<Integer> actualIndices = new ArrayList<>();
        filter.processIndices(idx -> {
            actualIndices.add(idx);
            return true;
        });

        assertEquals(expectedIndices.length, actualIndices.size(),
                "Number of set bits after merge should match expected count");
        for (final int expected : expectedIndices) {
            assertTrue(actualIndices.contains(expected),
                    "Bit index " + expected + " should be set in the filter");
        }
    }

    /**
     * Verifies that attempting to merge invalid indices (negative or out-of-range)
     * throws an {@link IllegalArgumentException}.
     */
    private void assertFailedIndexExtractorConstructor(final Shape shape, final int[] invalidValues) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(invalidValues);
        assertThrows(IllegalArgumentException.class, () -> createFilter(shape, indices));
    }

    @Test
    void testIndexExtractorMerge() {
        // Shape with 5 hash functions and 10 bits
        final Shape shape = Shape.fromKM(5, 10);

        // All distinct, valid indices are set correctly
        assertIndexExtractorMerge(shape, new int[] { 0, 2, 4, 6, 8 }, new int[] { 0, 2, 4, 6, 8 });

        // Duplicate index 2 is collapsed: only one bit is set for it
        assertIndexExtractorMerge(shape, new int[] { 0, 2, 4, 2, 8 }, new int[] { 0, 2, 4, 8 });

        // Negative index -2 must be rejected
        assertFailedIndexExtractorConstructor(shape, new int[] { 0, 2, 4, -2, 8 });

        // Index 12 exceeds the shape's 10-bit limit and must be rejected
        assertFailedIndexExtractorConstructor(shape, new int[] { 0, 2, 4, 12, 8 });

        // Empty index array produces an empty (no bits set) filter
        assertIndexExtractorMerge(shape, new int[0], new int[0]);
    }
}
