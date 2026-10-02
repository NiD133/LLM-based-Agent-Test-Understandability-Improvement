package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testIndexExtractorMerge {

    private SparseBloomFilter createFilter(final Shape shape, final IndexExtractor indices) {
        final SparseBloomFilter filter = new SparseBloomFilter(shape);
        filter.merge(indices);
        return filter;
    }

    private void assertRejectedIndices(final Shape shape, final int[] indices) {
        final IndexExtractor extractor = IndexExtractor.fromIndexArray(indices);

        assertThrows(IllegalArgumentException.class, () -> createFilter(shape, extractor));
    }

    private void assertMergedIndices(final Shape shape, final int[] indices, final int[] expectedIndices) {
        final IndexExtractor extractor = IndexExtractor.fromIndexArray(indices);
        final BloomFilter<?> filter = createFilter(shape, extractor);
        final List<Integer> actualIndices = new ArrayList<>();

        filter.processIndices(index -> {
            actualIndices.add(index);
            return true;
        });

        assertEquals(expectedIndices.length, actualIndices.size());
        for (final int expectedIndex : expectedIndices) {
            assertTrue(actualIndices.contains(Integer.valueOf(expectedIndex)), "Missing " + expectedIndex);
        }
    }

    @Test
    void testIndexExtractorMerge() {
        final Shape shape = Shape.fromKM(5, 10);

        assertMergedIndices(shape, new int[] { 0, 2, 4, 6, 8 }, new int[] { 0, 2, 4, 6, 8 });
        assertMergedIndices(shape, new int[] { 0, 2, 4, 2, 8 }, new int[] { 0, 2, 4, 8 });

        assertRejectedIndices(shape, new int[] { 0, 2, 4, -2, 8 });
        assertRejectedIndices(shape, new int[] { 0, 2, 4, 12, 8 });

        assertMergedIndices(shape, new int[0], new int[0]);
    }
}
