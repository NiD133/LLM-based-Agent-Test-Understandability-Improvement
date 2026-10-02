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

    private SparseBloomFilter createFilter(final Shape shape, final IndexExtractor extractor) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(extractor);
        return filter;
    }

    private void assertIndexExtractorIsRejected(final Shape shape, final int[] indexes) {
        final IndexExtractor extractor = IndexExtractor.fromIndexArray(indexes);

        assertThrows(IllegalArgumentException.class, () -> createFilter(shape, extractor));
    }

    private void assertMergedIndexes(final Shape shape, final int[] indexes, final int[] expectedIndexes) {
        final IndexExtractor extractor = IndexExtractor.fromIndexArray(indexes);
        final BloomFilter<?> filter = createFilter(shape, extractor);
        final List<Integer> actualIndexes = new ArrayList<>();

        filter.processIndices(index -> {
            actualIndexes.add(index);
            return true;
        });

        assertEquals(expectedIndexes.length, actualIndexes.size());
        for (final int expectedIndex : expectedIndexes) {
            assertTrue(actualIndexes.contains(Integer.valueOf(expectedIndex)), "Missing " + expectedIndex);
        }
    }

    @Test
    void testIndexExtractorMerge() {
        final Shape shape = Shape.fromKM(5, 10);

        assertMergedIndexes(shape, new int[] { 0, 2, 4, 6, 8 }, new int[] { 0, 2, 4, 6, 8 });
        assertMergedIndexes(shape, new int[] { 0, 2, 4, 2, 8 }, new int[] { 0, 2, 4, 8 });
        assertIndexExtractorIsRejected(shape, new int[] { 0, 2, 4, -2, 8 });
        assertIndexExtractorIsRejected(shape, new int[] { 0, 2, 4, 12, 8 });
        assertMergedIndexes(shape, new int[0], new int[0]);
    }
}
