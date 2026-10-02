package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link SparseBloomFilter} merges indices supplied via an
 * {@link IndexExtractor}: duplicates are collapsed, out-of-range indices are
 * rejected, and an empty extractor leaves the filter empty.
 */
public class SparseBloomFilterTest_testIndexExtractorMerge {

    /** Shape with 5 hash functions over 10 bits, so valid indices are 0..9. */
    private static final Shape SHAPE = Shape.fromKM(5, 10);

    /**
     * Creates an empty {@link SparseBloomFilter} of the given shape and merges
     * the supplied indices into it.
     */
    private SparseBloomFilter mergeIndices(final Shape shape, final int[] indices) {
        final SparseBloomFilter filter = new SparseBloomFilter(shape);
        filter.merge(IndexExtractor.fromIndexArray(indices));
        return filter;
    }

    /**
     * Asserts that merging {@code indices} produces a filter holding exactly the
     * {@code expectedIndices} (order independent).
     */
    private void assertMergedIndices(final Shape shape, final int[] indices, final int[] expectedIndices) {
        final SparseBloomFilter filter = mergeIndices(shape, indices);

        final List<Integer> actualIndices = new ArrayList<>();
        filter.processIndices(index -> {
            actualIndices.add(index);
            return true;
        });

        assertEquals(expectedIndices.length, actualIndices.size());
        for (final int expected : expectedIndices) {
            assertTrue(actualIndices.contains(Integer.valueOf(expected)), "Missing " + expected);
        }
    }

    /**
     * Asserts that merging the given (invalid) indices fails with an
     * {@link IllegalArgumentException}.
     */
    private void assertMergeRejects(final Shape shape, final int[] indices) {
        assertThrows(IllegalArgumentException.class, () -> mergeIndices(shape, indices));
    }

    @Test
    void testIndexExtractorMerge() {
        // distinct in-range indices are all retained
        assertMergedIndices(SHAPE, new int[] { 0, 2, 4, 6, 8 }, new int[] { 0, 2, 4, 6, 8 });
        // duplicate indices collapse to a single entry
        assertMergedIndices(SHAPE, new int[] { 0, 2, 4, 2, 8 }, new int[] { 0, 2, 4, 8 });
        // negative indices are rejected
        assertMergeRejects(SHAPE, new int[] { 0, 2, 4, -2, 8 });
        // indices >= the number of bits are rejected
        assertMergeRejects(SHAPE, new int[] { 0, 2, 4, 12, 8 });
        // merging no indices leaves the filter empty
        assertMergedIndices(SHAPE, new int[0], new int[0]);
    }
}
