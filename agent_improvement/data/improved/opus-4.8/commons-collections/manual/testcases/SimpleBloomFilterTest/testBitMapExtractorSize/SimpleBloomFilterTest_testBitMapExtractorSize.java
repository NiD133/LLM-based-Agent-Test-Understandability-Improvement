package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SimpleBloomFilter#processBitMaps} always visits exactly
 * one bit map per long word required by the filter's {@link Shape}, regardless
 * of whether the filter is populated or empty.
 */
public class SimpleBloomFilterTest_testBitMapExtractorSize {

    /**
     * The shape used for every filter in this test:
     * 17 hash functions (k) over 72 bits (m).
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /** Creates an empty filter of the given shape. */
    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /** Creates a filter of the given shape populated from the given hasher. */
    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    /** Counts how many bit maps {@code processBitMaps} passes to its consumer. */
    private int countBitMaps(final SimpleBloomFilter filter) {
        final int[] count = new int[1];
        filter.processBitMaps(bitMap -> {
            count[0]++;
            return true;
        });
        return count[0];
    }

    @Test
    void testBitMapExtractorSize() {
        final Shape shape = getTestShape();
        final int expectedBitMaps = BitMaps.numberOfBitMaps(shape);

        // A populated filter reports one bit map per long word in the shape.
        final SimpleBloomFilter populated = createFilter(shape, TestingHashers.FROM1);
        assertEquals(expectedBitMaps, countBitMaps(populated));

        // An empty filter reports the same number of bit maps.
        final SimpleBloomFilter empty = createEmptyFilter(shape);
        assertEquals(expectedBitMaps, countBitMaps(empty));
    }
}
