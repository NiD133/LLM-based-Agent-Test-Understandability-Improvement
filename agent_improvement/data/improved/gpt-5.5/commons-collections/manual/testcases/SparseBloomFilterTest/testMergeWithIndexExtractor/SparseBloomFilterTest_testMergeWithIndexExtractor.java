package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.BitSet;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testMergeWithIndexExtractor {

    private static final int MERGE_TRIALS = 5;

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    private SparseBloomFilter createFilter(final Shape shape, final IndexExtractor indexExtractor) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(indexExtractor);
        return filter;
    }

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    @Test
    void testMergeWithIndexExtractor() {
        final Shape shape = getTestShape();

        assertGeneratedIndicesAreMerged(shape);
        assertOutOfRangeIndicesAreRejected(shape);
    }

    private void assertGeneratedIndicesAreMerged(final Shape shape) {
        for (int i = 0; i < MERGE_TRIALS; i++) {
            final int[] values = DefaultIndexExtractorTest.generateIntArray(shape.getNumberOfHashFunctions(), shape.getNumberOfBits());
            final BloomFilter<?> filter = createFilter(shape, IndexExtractor.fromIndexArray(values));
            final BitSet uniqueValues = DefaultIndexExtractorTest.uniqueSet(values);

            assertTrue(filter.processIndices(index -> {
                final boolean wasExpected = uniqueValues.get(index);
                uniqueValues.clear(index);
                return wasExpected;
            }));
            assertTrue(uniqueValues.isEmpty());
        }
    }

    private void assertOutOfRangeIndicesAreRejected(final Shape shape) {
        final BloomFilter<?> tooLargeIndexFilter = createEmptyFilter(shape);
        assertThrows(IllegalArgumentException.class, () -> tooLargeIndexFilter.merge(IndexExtractor.fromIndexArray(shape.getNumberOfBits())));

        final BloomFilter<?> negativeIndexFilter = createEmptyFilter(shape);
        assertThrows(IllegalArgumentException.class, () -> negativeIndexFilter.merge(IndexExtractor.fromIndexArray(-1)));
    }
}
