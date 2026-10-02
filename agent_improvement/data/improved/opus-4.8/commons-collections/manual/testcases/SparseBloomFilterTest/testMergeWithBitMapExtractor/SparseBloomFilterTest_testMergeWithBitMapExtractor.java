package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SparseBloomFilter#merge(BitMapExtractor)}.
 */
public class SparseBloomFilterTest_testMergeWithBitMapExtractor {

    /**
     * The shape shared by the filters under test: 17 hash functions over 72 bits.
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Creates an empty {@link SparseBloomFilter} for the given shape.
     */
    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * Creates a filter for the given shape and merges the bit maps from the extractor into it.
     */
    private SparseBloomFilter createFilter(final Shape shape, final BitMapExtractor extractor) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(extractor);
        return filter;
    }

    @Test
    void testMergeWithBitMapExtractor() {
        final Shape shape = getTestShape();
        final int bitMapCount = BitMaps.numberOfBitMaps(shape);

        // For several random index sets: build a bit map array, merge it into a filter,
        // then confirm the filter reproduces exactly those same bit maps.
        for (int i = 0; i < 5; i++) {
            final long[] expectedBitMaps = new long[bitMapCount];
            final int[] randomIndices = DefaultIndexExtractorTest.generateIntArray(
                    shape.getNumberOfHashFunctions(), shape.getNumberOfBits());
            for (final int idx : randomIndices) {
                BitMaps.set(expectedBitMaps, idx);
            }

            final BloomFilter filter = createFilter(shape, BitMapExtractor.fromBitMapArray(expectedBitMaps));

            // Collect the expected bit maps, then remove each one the filter produces.
            // Every produced bit map must match, and none must be left over.
            final List<Long> remaining = new ArrayList<>();
            for (final long bitMap : expectedBitMaps) {
                remaining.add(bitMap);
            }
            assertTrue(filter.processBitMaps(bitMap -> remaining.remove(Long.valueOf(bitMap))));
            assertTrue(remaining.isEmpty());
        }

        // Merging bit maps whose set bits exceed the shape's number of bits must fail.
        final long[] tooLargeValues = new long[bitMapCount];
        Arrays.fill(tooLargeValues, Long.MAX_VALUE);
        final BitMapExtractor tooLargeExtractor = BitMapExtractor.fromBitMapArray(tooLargeValues);
        final BloomFilter filterForTooLarge = createEmptyFilter(shape);
        assertThrows(IllegalArgumentException.class, () -> filterForTooLarge.merge(tooLargeExtractor));

        // Same failure even when the bit map array length matches the shape:
        // here the highest bit (index 63) exceeds the 32-bit shape.
        final BitMapExtractor outOfRangeExtractor = BitMapExtractor.fromBitMapArray(0x80_00_00_00_00_00_00_00L);
        final BloomFilter filterForOutOfRange = createEmptyFilter(Shape.fromKM(3, 32));
        assertThrows(IllegalArgumentException.class, () -> filterForOutOfRange.merge(outOfRangeExtractor));
    }
}
