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
        final SimpleBloomFilter filter = createEmptyFilter(shape);
        filter.merge(extractor);
        return filter;
    }

    private void assertIndexExtractorMerge(final Shape shape, final int[] values, final int[] expected) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(values);
        final BloomFilter<?> filter = createFilter(shape, indices);
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

    private void assertFailedIndexExtractorConstructor(final Shape shape, final int[] values) {
        final IndexExtractor indices = IndexExtractor.fromIndexArray(values);
        assertThrows(IllegalArgumentException.class, () -> createFilter(shape, indices));
    }

    @Test
    void testIndexExtractorMerge() {
        final Shape shape = Shape.fromKM(5, 10);

        assertIndexExtractorMerge(shape, new int[] { 0, 2, 4, 6, 8 }, new int[] { 0, 2, 4, 6, 8 });
        assertIndexExtractorMerge(shape, new int[] { 0, 2, 4, 2, 8 }, new int[] { 0, 2, 4, 8 });
        assertFailedIndexExtractorConstructor(shape, new int[] { 0, 2, 4, -2, 8 });
        assertFailedIndexExtractorConstructor(shape, new int[] { 0, 2, 4, 12, 8 });
        assertIndexExtractorMerge(shape, new int[0], new int[0]);
    }
}
