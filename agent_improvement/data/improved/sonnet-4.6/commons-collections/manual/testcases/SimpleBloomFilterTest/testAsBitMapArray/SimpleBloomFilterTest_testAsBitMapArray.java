package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testAsBitMapArray {

    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter bf = new SimpleBloomFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    /**
     * Tests that asBitMapArray correctly splits set bits across multiple longs.
     *
     * Setup: IncrementingHasher(63, 1) with k=2 sets bits at indices 63 and 64.
     *   - Bit 63 is the most-significant bit of the first long  → 0x8000000000000000L
     *   - Bit 64 is the least-significant bit of the second long → 0x1L
     * A shape of 72 bits spans two longs (bits 0-63 and bits 64-71), so the
     * returned array must have exactly 2 elements.
     */
    @Test
    void testAsBitMapArray() {
        // Hasher produces indices 63, 64 (start=63, increment=1, k=2)
        final IncrementingHasher hasher = new IncrementingHasher(63, 1);

        // 72-bit filter requires two 64-bit longs to hold all bits
        final Shape shape = Shape.fromKM(2, 72);
        final BloomFilter bf = createFilter(shape, hasher);

        final long[] bitMapArray = bf.asBitMapArray();

        assertEquals(2, bitMapArray.length, "A 72-bit filter must be represented by exactly 2 longs");

        // Bit 63 → MSB of the first long
        assertEquals(0x8000000000000000L, bitMapArray[0], "Bit 63 must be the most-significant bit of the first long");

        // Bit 64 → LSB of the second long
        assertEquals(0x1L, bitMapArray[1], "Bit 64 must be the least-significant bit of the second long");
    }
}
