package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SparseBloomFilter#processBitMaps} always hands the consumer
 * exactly {@link BitMaps#numberOfBitMaps(Shape)} bit maps. This must hold whether the
 * filter has been populated with bits or is still empty, because the number of bit maps
 * is a property of the filter's shape, not of how many bits are set.
 */
public class SparseBloomFilterTest_testBitMapExtractorSize {

    /**
     * The shape used for testing: 17 hash functions (k) over 72 bits (m).
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    private SparseBloomFilter createPopulatedFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    /**
     * Counts how many bit maps {@code filter} passes to its {@code processBitMaps} consumer.
     */
    private int countBitMaps(final SparseBloomFilter filter) {
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

        // A populated filter must report one bit map per long in the shape.
        final SparseBloomFilter populatedFilter = createPopulatedFilter(shape, TestingHashers.FROM1);
        assertEquals(expectedBitMaps, countBitMaps(populatedFilter));

        // An empty filter must report the same number of (zeroed) bit maps.
        final SparseBloomFilter emptyFilter = createEmptyFilter(shape);
        assertEquals(expectedBitMaps, countBitMaps(emptyFilter));
    }
}
