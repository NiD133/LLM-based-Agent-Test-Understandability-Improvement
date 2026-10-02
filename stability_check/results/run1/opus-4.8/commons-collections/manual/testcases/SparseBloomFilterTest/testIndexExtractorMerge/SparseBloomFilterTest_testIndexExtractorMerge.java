package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link SparseBloomFilter} correctly merges the indices supplied by an
 * {@link IndexExtractor}.
 */
public class SparseBloomFilterTest_testIndexExtractorMerge {

    /**
     * Builds a {@link SparseBloomFilter} of the given shape and merges the given indices into it.
     *
     * @param shape   the shape of the filter.
     * @param indices the indices to merge into the filter.
     * @return the populated filter.
     */
    private SparseBloomFilter createFilter(final Shape shape, final IndexExtractor indices) {
        final SparseBloomFilter filter = new SparseBloomFilter(shape);
        filter.merge(indices);
        return filter;
    }

    /**
     * Asserts that merging the given (invalid) index values into a filter of the given shape is
     * rejected with an {@link IllegalArgumentException}.
     */
    private void assertFailedIndexExtractorConstructor(final Shape shape, final int[] values) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(values);
        assertThrows(IllegalArgumentException.class, () -> createFilter(shape, indices));
    }

    /**
     * Asserts that merging {@code values} into a filter of the given shape yields exactly the
     * {@code expected} set of indices (order independent).
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
        for (final int expectedIndex : expected) {
            assertTrue(actualIndices.contains(Integer.valueOf(expectedIndex)), "Missing " + expectedIndex);
        }
    }

    @Test
    void testIndexExtractorMerge() {
        final Shape shape = Shape.fromKM(5, 10);

        // distinct in-range indices are all retained
        assertIndexExtractorMerge(shape, new int[] { 0, 2, 4, 6, 8 }, new int[] { 0, 2, 4, 6, 8 });
        // duplicate values are collapsed into a single index
        assertIndexExtractorMerge(shape, new int[] { 0, 2, 4, 2, 8 }, new int[] { 0, 2, 4, 8 });
        // a negative index is rejected
        assertFailedIndexExtractorConstructor(shape, new int[] { 0, 2, 4, -2, 8 });
        // an index that is too large for the shape is rejected
        assertFailedIndexExtractorConstructor(shape, new int[] { 0, 2, 4, 12, 8 });
        // an empty set of indices produces an empty filter
        assertIndexExtractorMerge(shape, new int[0], new int[0]);
    }
}
