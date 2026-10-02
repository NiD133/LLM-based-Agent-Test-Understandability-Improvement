package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testBitMapExtractorSize {

    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    private int countProcessedBitMaps(final SimpleBloomFilter filter) {
        final int[] processedBitMaps = new int[1];
        filter.processBitMaps(bitMap -> {
            processedBitMaps[0]++;
            return true;
        });
        return processedBitMaps[0];
    }

    @Test
    void testBitMapExtractorSize() {
        final int expectedBitMapCount = BitMaps.numberOfBitMaps(TEST_SHAPE);

        assertEquals(expectedBitMapCount, countProcessedBitMaps(createFilter(TEST_SHAPE, TestingHashers.FROM1)));
        assertEquals(expectedBitMapCount, countProcessedBitMaps(createEmptyFilter(TEST_SHAPE)));
    }
}
