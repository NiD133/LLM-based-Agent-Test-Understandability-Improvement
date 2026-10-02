package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testBitMapExtractorSize {

    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    protected SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    protected SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter bloomFilter = createEmptyFilter(shape);
        bloomFilter.merge(hasher);
        return bloomFilter;
    }

    protected Shape getTestShape() {
        return TEST_SHAPE;
    }

    @Test
    void testBitMapExtractorSize() {
        final int expectedBitMapCount = BitMaps.numberOfBitMaps(getTestShape());

        assertEquals(expectedBitMapCount, countProcessedBitMaps(createFilter(getTestShape(), TestingHashers.FROM1)));
        assertEquals(expectedBitMapCount, countProcessedBitMaps(createEmptyFilter(getTestShape())));
    }

    private int countProcessedBitMaps(final BloomFilter<?> bloomFilter) {
        final int[] processedBitMaps = new int[1];
        bloomFilter.processBitMaps(bitMap -> {
            processedBitMaps[0]++;
            return true;
        });
        return processedBitMaps[0];
    }
}
