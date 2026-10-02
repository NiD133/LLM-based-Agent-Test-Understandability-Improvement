package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testMergeWithBitMapExtractor {

    private static final int RANDOM_BITMAP_CASES = 5;

    protected SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * Creates the BloomFilter implementation we are testing.
     *
     * @param shape the shape of the filter.
     * @param extractor A BitMap extractor to build the filter with.
     * @return a BloomFilter implementation.
     */
    protected final SparseBloomFilter createFilter(final Shape shape, final BitMapExtractor extractor) {
        final SparseBloomFilter bloomFilter = createEmptyFilter(shape);
        bloomFilter.merge(extractor);
        return bloomFilter;
    }

    /**
     * The shape of the Bloom filters for testing.
     * <ul>
     *  <li>Hash functions (k) = 17
     *  <li>Number of bits (m) = 72
     * </ul>
     * @return the testing shape.
     */
    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    @Test
    void testMergeWithBitMapExtractor() {
        final Shape testShape = getTestShape();
        final int bitMapCount = BitMaps.numberOfBitMaps(testShape);

        for (int i = 0; i < RANDOM_BITMAP_CASES; i++) {
            final long[] expectedBitMaps = new long[bitMapCount];
            for (final int index : DefaultIndexExtractorTest.generateIntArray(testShape.getNumberOfHashFunctions(), testShape.getNumberOfBits())) {
                BitMaps.set(expectedBitMaps, index);
            }

            final BloomFilter<?> filter = createFilter(testShape, BitMapExtractor.fromBitMapArray(expectedBitMaps));
            final List<Long> unprocessedBitMaps = new ArrayList<>();
            for (final long bitMap : expectedBitMaps) {
                unprocessedBitMaps.add(bitMap);
            }

            assertTrue(filter.processBitMaps(bitMap -> unprocessedBitMaps.remove(Long.valueOf(bitMap))));
            assertTrue(unprocessedBitMaps.isEmpty());
        }

        final long[] outOfRangeBitMaps = new long[bitMapCount];
        Arrays.fill(outOfRangeBitMaps, Long.MAX_VALUE);
        final BitMapExtractor outOfRangeExtractor = BitMapExtractor.fromBitMapArray(outOfRangeBitMaps);
        final BloomFilter<?> bloomFilter = createEmptyFilter(testShape);
        assertThrows(IllegalArgumentException.class, () -> bloomFilter.merge(outOfRangeExtractor));

        final BitMapExtractor sameLengthOutOfRangeExtractor = BitMapExtractor.fromBitMapArray(0x80_00_00_00_00_00_00_00L);
        final BloomFilter<?> smallerBloomFilter = createEmptyFilter(Shape.fromKM(3, 32));
        assertThrows(IllegalArgumentException.class, () -> smallerBloomFilter.merge(sameLengthOutOfRangeExtractor));
    }
}
