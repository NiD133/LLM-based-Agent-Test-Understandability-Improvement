package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testAsBitMapArray {

    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter bloomFilter = new SparseBloomFilter(shape);
        bloomFilter.merge(hasher);
        return bloomFilter;
    }

    /**
     * Tests that asBitMapArray works correctly when populated bits span more
     * than one bitmap word.
     */
    @Test
    final void testAsBitMapArray() {
        final IncrementingHasher hasher = new IncrementingHasher(63, 1);
        final BloomFilter<?> bloomFilter = createFilter(Shape.fromKM(2, 72), hasher);

        final long[] bitMaps = bloomFilter.asBitMapArray();

        assertEquals(2, bitMaps.length);
        assertEquals(0x8000000000000000L, bitMaps[0]);
        assertEquals(0x1, bitMaps[1]);
    }
}
