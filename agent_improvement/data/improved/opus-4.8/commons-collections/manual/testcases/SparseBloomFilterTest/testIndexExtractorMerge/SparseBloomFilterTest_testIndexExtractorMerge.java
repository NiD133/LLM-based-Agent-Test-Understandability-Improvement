package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests merging an {@link IndexExtractor} into a {@link SparseBloomFilter}.
 *
 * <p>A {@code SparseBloomFilter} keeps the set of enabled bit indices. Merging an
 * {@code IndexExtractor} should:</p>
 * <ul>
 *   <li>turn on exactly the indices supplied, collapsing duplicates;</li>
 *   <li>reject indices that fall outside the filter's shape (negative, or beyond the
 *       number of bits) by throwing {@link IllegalArgumentException}.</li>
 * </ul>
 */
public class SparseBloomFilterTest_testIndexExtractorMerge {

    /** Shape used for all cases: 5 hash functions over 10 bits, so valid indices are 0..9. */
    private static final Shape SHAPE = Shape.fromKM(5, 10);

    /**
     * Creates a fresh, empty {@link SparseBloomFilter} and merges the given indices into it.
     *
     * @param shape   the shape of the filter.
     * @param indices the indices to merge.
     * @return the populated filter.
     */
    private SparseBloomFilter mergeIndicesIntoNewFilter(final Shape shape, final IndexExtractor indices) {
        final SparseBloomFilter filter = new SparseBloomFilter(shape);
        filter.merge(indices);
        return filter;
    }

    /**
     * Asserts that merging {@code values} produces a filter whose enabled indices are exactly
     * {@code expected} (order independent, duplicates collapsed).
     */
    private void assertMergedIndicesAre(final int[] values, final int[] expected) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(values);
        final BloomFilter filter = mergeIndicesIntoNewFilter(SHAPE, indices);

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
     * Asserts that merging {@code values} is rejected with an {@link IllegalArgumentException}
     * because at least one index is out of range for {@link #SHAPE}.
     */
    private void assertMergeRejectsOutOfRangeIndices(final int[] values) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(values);
        assertThrows(IllegalArgumentException.class, () -> mergeIndicesIntoNewFilter(SHAPE, indices));
    }

    @Test
    void testIndexExtractorMerge() {
        // Distinct, in-range indices are all enabled.
        assertMergedIndicesAre(new int[] { 0, 2, 4, 6, 8 }, new int[] { 0, 2, 4, 6, 8 });

        // Duplicate values collapse to a single enabled index.
        assertMergedIndicesAre(new int[] { 0, 2, 4, 2, 8 }, new int[] { 0, 2, 4, 8 });

        // A negative index is out of range and is rejected.
        assertMergeRejectsOutOfRangeIndices(new int[] { 0, 2, 4, -2, 8 });

        // An index >= the number of bits (10) is out of range and is rejected.
        assertMergeRejectsOutOfRangeIndices(new int[] { 0, 2, 4, 12, 8 });

        // No indices yields an empty filter.
        assertMergedIndicesAre(new int[0], new int[0]);
    }
}
