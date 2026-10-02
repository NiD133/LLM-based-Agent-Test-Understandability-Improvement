package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SimpleBloomFilter#asBitMapArray()}.
 */
public class SimpleBloomFilterTest_testAsBitMapArray {

    /**
     * Builds a {@link SimpleBloomFilter} of the given shape whose bits are populated from the hasher.
     *
     * @param shape  the shape of the filter.
     * @param hasher the hasher used to set the filter's bits.
     * @return the populated filter.
     */
    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter filter = new SimpleBloomFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    /**
     * Verifies that {@code asBitMapArray()} returns one long per 64 bits of the shape,
     * with the bits set by the hasher reported in the correct word.
     *
     * <p>The shape spans 72 bits, so the result holds two longs. The hasher sets bit 63
     * (the most significant bit of word 0) and bit 64 (the least significant bit of word 1).</p>
     */
    @Test
    final void testAsBitMapArray() {
        final IncrementingHasher hasher = new IncrementingHasher(63, 1);
        final BloomFilter bf = createFilter(Shape.fromKM(2, 72), hasher);

        final long[] bitMaps = bf.asBitMapArray();

        assertEquals(2, bitMaps.length);
        assertEquals(0x8000000000000000L, bitMaps[0]);
        assertEquals(0x1, bitMaps[1]);
    }
}
