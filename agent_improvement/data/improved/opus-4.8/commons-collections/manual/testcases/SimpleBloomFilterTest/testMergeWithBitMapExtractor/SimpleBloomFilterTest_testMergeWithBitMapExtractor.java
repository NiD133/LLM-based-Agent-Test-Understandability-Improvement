package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SimpleBloomFilter#merge(BitMapExtractor)}.
 */
public class SimpleBloomFilterTest_testMergeWithBitMapExtractor {

    /**
     * The shape used for the filters under test: 17 hash functions over 72 bits.
     */
    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Creates an empty filter of the implementation under test.
     *
     * @param shape the shape of the filter.
     * @return an empty {@link SimpleBloomFilter}.
     */
    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /**
     * Creates a filter and merges the given bit maps into it.
     *
     * @param shape     the shape of the filter.
     * @param extractor the bit maps to merge into the filter.
     * @return the populated {@link SimpleBloomFilter}.
     */
    protected SimpleBloomFilter createFilter(final Shape shape, final BitMapExtractor extractor) {
        final SimpleBloomFilter filter = createEmptyFilter(shape);
        filter.merge(extractor);
        return filter;
    }

    @Test
    void testMergeWithBitMapExtractor() {
        final Shape shape = getTestShape();
        final int bitMapCount = BitMaps.numberOfBitMaps(shape);

        // Merging a BitMapExtractor must reproduce exactly the bit maps it supplied.
        for (int trial = 0; trial < 5; trial++) {
            // Build a set of expected bit maps from a handful of random indices.
            final long[] expectedBitMaps = new long[bitMapCount];
            final int[] randomIndices = DefaultIndexExtractorTest.generateIntArray(
                    shape.getNumberOfHashFunctions(), shape.getNumberOfBits());
            for (final int index : randomIndices) {
                BitMaps.set(expectedBitMaps, index);
            }

            final BloomFilter filter = createFilter(shape, BitMapExtractor.fromBitMapArray(expectedBitMaps));

            // Each bit map the filter emits must match one of the expected bit maps,
            // and every expected bit map must be accounted for.
            final List<Long> remainingExpected = new ArrayList<>();
            for (final long bitMap : expectedBitMaps) {
                remainingExpected.add(bitMap);
            }
            assertTrue(filter.processBitMaps(bitMap -> remainingExpected.remove(Long.valueOf(bitMap))));
            assertTrue(remainingExpected.isEmpty());
        }

        // A BitMapExtractor that sets bits beyond the shape's range must be rejected.
        final long[] allBitsSet = new long[bitMapCount];
        Arrays.fill(allBitsSet, Long.MAX_VALUE);
        final BitMapExtractor extractorWithExcessBits = BitMapExtractor.fromBitMapArray(allBitsSet);
        final BloomFilter filter = createEmptyFilter(shape);
        assertThrows(IllegalArgumentException.class, () -> filter.merge(extractorWithExcessBits));

        // Rejection still applies when the bit map array has the correct length but a
        // bit beyond the shape's range (here, the highest bit) is set.
        final BitMapExtractor extractorWithBitOutOfRange = BitMapExtractor.fromBitMapArray(0x80_00_00_00_00_00_00_00L);
        final BloomFilter smallFilter = createEmptyFilter(Shape.fromKM(3, 32));
        assertThrows(IllegalArgumentException.class, () -> smallFilter.merge(extractorWithBitOutOfRange));
    }
}
