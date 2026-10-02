package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link SimpleBloomFilter} populated by merging an {@link IndexExtractor}
 * ends up holding exactly the expected set of bit indices, and that invalid indices
 * are rejected.
 */
public class SimpleBloomFilterTest_testIndexExtractorMerge {

    /**
     * Builds a new, empty {@link SimpleBloomFilter} and merges the given indices into it.
     *
     * @param shape   the shape of the filter.
     * @param indices the indices to merge.
     * @return the populated filter.
     */
    private SimpleBloomFilter createFilterFromIndices(final Shape shape, final IndexExtractor indices) {
        final SimpleBloomFilter filter = new SimpleBloomFilter(shape);
        filter.merge(indices);
        return filter;
    }

    /**
     * Asserts that merging {@code values} into a filter of the given shape results in a
     * filter whose set bits are exactly {@code expected} (order independent, duplicates collapsed).
     */
    private void assertIndexExtractorMerge(final Shape shape, final int[] values, final int[] expected) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(values);
        final BloomFilter filter = createFilterFromIndices(shape, indices);

        final List<Integer> setIndices = new ArrayList<>();
        filter.processIndices(index -> {
            setIndices.add(index);
            return true;
        });

        assertEquals(expected.length, setIndices.size());
        for (final int expectedIndex : expected) {
            assertTrue(setIndices.contains(Integer.valueOf(expectedIndex)), "Missing " + expectedIndex);
        }
    }

    /**
     * Asserts that merging an index that is out of range for the shape (negative or too large)
     * is rejected with an {@link IllegalArgumentException}.
     */
    private void assertMergeRejectsInvalidIndices(final Shape shape, final int[] values) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(values);
        assertThrows(IllegalArgumentException.class, () -> createFilterFromIndices(shape, indices));
    }

    @Test
    void testIndexExtractorMerge() {
        final Shape shape = Shape.fromKM(5, 10);

        // distinct in-range indices are all retained
        assertIndexExtractorMerge(shape, new int[] { 0, 2, 4, 6, 8 }, new int[] { 0, 2, 4, 6, 8 });

        // duplicate values collapse to a single set bit
        assertIndexExtractorMerge(shape, new int[] { 0, 2, 4, 2, 8 }, new int[] { 0, 2, 4, 8 });

        // a negative index is rejected
        assertMergeRejectsInvalidIndices(shape, new int[] { 0, 2, 4, -2, 8 });

        // an index at or beyond the number of bits is rejected
        assertMergeRejectsInvalidIndices(shape, new int[] { 0, 2, 4, 12, 8 });

        // merging no indices leaves the filter empty
        assertIndexExtractorMerge(shape, new int[0], new int[0]);
    }
}
