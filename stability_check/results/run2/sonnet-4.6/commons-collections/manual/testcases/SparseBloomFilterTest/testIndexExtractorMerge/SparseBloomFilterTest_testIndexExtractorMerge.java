package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests that merging an IndexExtractor into a SparseBloomFilter correctly
 * sets bits, deduplicates indices, and rejects out-of-range values.
 */
public class SparseBloomFilterTest_testIndexExtractorMerge {

    // -----------------------------------------------------------------------
    // Factory helpers
    // -----------------------------------------------------------------------

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * Builds a SparseBloomFilter from an IndexExtractor by merging into an
     * otherwise-empty filter.
     */
    private SparseBloomFilter createFilter(final Shape shape, final IndexExtractor extractor) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(extractor);
        return filter;
    }

    // -----------------------------------------------------------------------
    // Assertion helpers
    // -----------------------------------------------------------------------

    /**
     * Asserts that merging {@code inputIndices} into a fresh filter produces
     * exactly the bits listed in {@code expectedIndices} (order-independent).
     */
    private void assertIndexExtractorMerge(
            final Shape shape,
            final int[] inputIndices,
            final int[] expectedIndices) {

        final IndexExtractor extractor = IndexExtractor.fromIndexArray(inputIndices);
        final BloomFilter filter = createFilter(shape, extractor);

        final List<Integer> actualIndices = new ArrayList<>();
        filter.processIndices(idx -> {
            actualIndices.add(idx);
            return true;
        });

        assertEquals(expectedIndices.length, actualIndices.size(),
                "Cardinality mismatch after merge");

        for (final int expected : expectedIndices) {
            assertTrue(actualIndices.contains(expected),
                    "Expected bit " + expected + " to be set, but it was not");
        }
    }

    /**
     * Asserts that building a filter from {@code invalidIndices} throws
     * {@link IllegalArgumentException} because at least one index is out of range.
     */
    private void assertMergeThrowsForInvalidIndices(final Shape shape, final int[] invalidIndices) {
        final IndexExtractor extractor = IndexExtractor.fromIndexArray(invalidIndices);
        assertThrows(IllegalArgumentException.class,
                () -> createFilter(shape, extractor),
                "Expected IllegalArgumentException for out-of-range indices");
    }

    // -----------------------------------------------------------------------
    // Test
    // -----------------------------------------------------------------------

    /**
     * Verifies that merging an IndexExtractor into a SparseBloomFilter:
     * <ul>
     *   <li>sets the correct bits for a typical set of distinct indices</li>
     *   <li>deduplicates repeated indices</li>
     *   <li>rejects negative indices</li>
     *   <li>rejects indices that exceed the filter's bit count</li>
     *   <li>handles an empty index array gracefully</li>
     * </ul>
     *
     * Shape used: k=5 hash functions, m=10 bits (indices 0-9 are valid).
     */
    @Test
    void testIndexExtractorMerge() {
        final Shape shape = Shape.fromKM(5, 10);

        // All five distinct even indices should appear in the filter unchanged.
        assertIndexExtractorMerge(
                shape,
                new int[] {0, 2, 4, 6, 8},
                new int[] {0, 2, 4, 6, 8});

        // Index 2 appears twice; the filter should deduplicate it.
        assertIndexExtractorMerge(
                shape,
                new int[] {0, 2, 4, 2, 8},
                new int[] {0, 2, 4, 8});

        // Negative index (-2) is below the valid range [0, 9].
        assertMergeThrowsForInvalidIndices(shape, new int[] {0, 2, 4, -2, 8});

        // Index 12 exceeds the maximum valid index (9) for a 10-bit filter.
        assertMergeThrowsForInvalidIndices(shape, new int[] {0, 2, 4, 12, 8});

        // An empty extractor should produce an empty filter with zero set bits.
        assertIndexExtractorMerge(shape, new int[0], new int[0]);
    }
}
