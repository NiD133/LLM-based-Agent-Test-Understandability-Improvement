package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SparseBloomFilter#asBitMapArray()}.
 */
public class SparseBloomFilterTest_testAsBitMapArray {

    /**
     * Builds a {@link SparseBloomFilter} of the given shape populated by merging the
     * supplied hasher into it.
     *
     * @param shape  the shape of the filter.
     * @param hasher the hasher used to populate the filter.
     * @return the populated filter.
     */
    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter filter = new SparseBloomFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    /**
     * Verifies that {@code asBitMapArray} spans multiple long values when the filter's
     * shape requires more than 64 bits, and that the enabled bits land in the expected
     * words.
     */
    @Test
    final void testAsBitMapArray() {
        // A shape of 72 bits needs two longs (words) in the bit map array.
        // The hasher enables bits 63 and 64 (start at 63, increment by 1, k = 2):
        //   - bit 63 -> highest bit of word 0 -> 0x8000000000000000
        //   - bit 64 -> lowest bit of word 1  -> 0x1
        final IncrementingHasher hasher = new IncrementingHasher(63, 1);
        final BloomFilter bf = createFilter(Shape.fromKM(2, 72), hasher);

        final long[] bitMaps = bf.asBitMapArray();

        assertEquals(2, bitMaps.length);
        assertEquals(0x8000000000000000L, bitMaps[0]);
        assertEquals(0x1L, bitMaps[1]);
    }
}
