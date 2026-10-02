package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testMergeWithBitMapExtractor {

    private static final int SAMPLE_COUNT = 5;

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    private SimpleBloomFilter createFilter(final Shape shape, final BitMapExtractor extractor) {
        final SimpleBloomFilter filter = createEmptyFilter(shape);
        filter.merge(extractor);
        return filter;
    }

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    @Test
    void testMergeWithBitMapExtractor() {
        final Shape testShape = getTestShape();
        final int bitMapCount = BitMaps.numberOfBitMaps(testShape);

        for (int sample = 0; sample < SAMPLE_COUNT; sample++) {
            final long[] values = new long[bitMapCount];
            for (final int index : DefaultIndexExtractorTest.generateIntArray(testShape.getNumberOfHashFunctions(), testShape.getNumberOfBits())) {
                BitMaps.set(values, index);
            }

            final BloomFilter<?> filter = createFilter(testShape, BitMapExtractor.fromBitMapArray(values));
            final List<Long> expectedBitMaps = new ArrayList<>();
            for (final long bitMap : values) {
                expectedBitMaps.add(bitMap);
            }

            assertTrue(filter.processBitMaps(bitMap -> expectedBitMaps.remove(Long.valueOf(bitMap))));
            assertTrue(expectedBitMaps.isEmpty());
        }

        final long[] oversizedBitMaps = new long[bitMapCount];
        Arrays.fill(oversizedBitMaps, Long.MAX_VALUE);
        final BitMapExtractor oversizedExtractor = BitMapExtractor.fromBitMapArray(oversizedBitMaps);
        final BloomFilter<?> filterForOversizedBitMaps = createEmptyFilter(testShape);
        assertThrows(IllegalArgumentException.class, () -> filterForOversizedBitMaps.merge(oversizedExtractor));

        final BitMapExtractor sameLengthOutOfRangeExtractor = BitMapExtractor.fromBitMapArray(0x80_00_00_00_00_00_00_00L);
        final BloomFilter<?> smallerFilter = createEmptyFilter(Shape.fromKM(3, 32));
        assertThrows(IllegalArgumentException.class, () -> smallerFilter.merge(sameLengthOutOfRangeExtractor));
    }
}
