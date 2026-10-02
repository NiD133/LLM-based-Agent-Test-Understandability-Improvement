package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.BitSet;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testMergeWithIndexExtractor {

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    private SimpleBloomFilter createFilter(final Shape shape, final IndexExtractor extractor) {
        final SimpleBloomFilter bloomFilter = createEmptyFilter(shape);
        bloomFilter.merge(extractor);
        return bloomFilter;
    }

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    private void assertFilterContainsOnlyGeneratedIndices(final int[] values) {
        final Shape shape = getTestShape();
        final BloomFilter<?> filter = createFilter(shape, IndexExtractor.fromIndexArray(values));
        final BitSet uniqueValues = DefaultIndexExtractorTest.uniqueSet(values);

        assertTrue(filter.processIndices(index -> {
            final boolean wasExpectedIndex = uniqueValues.get(index);
            uniqueValues.clear(index);
            return wasExpectedIndex;
        }));
        assertTrue(uniqueValues.isEmpty());
    }

    private void assertInvalidMergeIndex(final int index) {
        final BloomFilter<?> filter = createEmptyFilter(getTestShape());

        assertThrows(IllegalArgumentException.class, () -> filter.merge(IndexExtractor.fromIndexArray(index)));
    }

    @Test
    void testMergeWithIndexExtractor() {
        for (int i = 0; i < 5; i++) {
            final int[] values = DefaultIndexExtractorTest.generateIntArray(
                    getTestShape().getNumberOfHashFunctions(),
                    getTestShape().getNumberOfBits());

            assertFilterContainsOnlyGeneratedIndices(values);
        }

        assertInvalidMergeIndex(getTestShape().getNumberOfBits());
        assertInvalidMergeIndex(-1);
    }
}
