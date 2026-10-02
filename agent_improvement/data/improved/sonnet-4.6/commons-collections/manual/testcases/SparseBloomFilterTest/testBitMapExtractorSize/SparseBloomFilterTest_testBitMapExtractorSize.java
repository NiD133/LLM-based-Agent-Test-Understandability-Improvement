package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testBitMapExtractorSize {

    protected SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    protected SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter bf = createEmptyFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Verifies that processBitMaps invokes the consumer exactly numberOfBitMaps times,
     * both for a populated filter and for an empty filter.
     */
    @Test
    void testBitMapExtractorSize() {
        final Shape shape = getTestShape();
        final int expectedBitMapCount = BitMaps.numberOfBitMaps(shape);

        // Count bitmap segments produced by a populated filter
        final int[] populatedFilterBitMapCount = new int[1];
        createFilter(shape, TestingHashers.FROM1).processBitMaps(bitMap -> {
            populatedFilterBitMapCount[0]++;
            return true;
        });
        assertEquals(expectedBitMapCount, populatedFilterBitMapCount[0],
                "A populated filter must produce exactly numberOfBitMaps bitmap segments");

        // Count bitmap segments produced by an empty filter
        final int[] emptyFilterBitMapCount = new int[1];
        createEmptyFilter(shape).processBitMaps(bitMap -> {
            emptyFilterBitMapCount[0]++;
            return true;
        });
        assertEquals(expectedBitMapCount, emptyFilterBitMapCount[0],
                "An empty filter must produce exactly numberOfBitMaps bitmap segments");
    }
}
