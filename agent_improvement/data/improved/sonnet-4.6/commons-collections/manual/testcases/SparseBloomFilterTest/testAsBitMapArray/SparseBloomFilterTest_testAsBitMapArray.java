package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link SparseBloomFilter#asBitMapArray()} for correct bit-to-word mapping
 * when the set indices span more than one 64-bit long.
 */
public class SparseBloomFilterTest_testAsBitMapArray {

    /**
     * Creates a {@link SparseBloomFilter} for {@code shape} and populates it
     * with the indices produced by {@code hasher}.
     */
    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter bf = new SparseBloomFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    /**
     * Verifies that {@code asBitMapArray} returns the correct bit layout when
     * the set indices straddle a word boundary.
     *
     * <p>Setup: {@code IncrementingHasher(63, 1)} with {@code k=2} hash functions
     * produces indices {@code {63, 64}}:
     * <ul>
     *   <li>Index 63 is the most-significant bit of the first  64-bit word → {@code 0x8000000000000000L}</li>
     *   <li>Index 64 is the least-significant bit of the second 64-bit word → {@code 0x0000000000000001L}</li>
     * </ul>
     * A shape of {@code m=72} bits requires {@code ceil(72 / 64) = 2} words.
     */
    @Test
    final void testAsBitMapArray() {
        // IncrementingHasher(start=63, increment=1) with k=2 yields bit indices {63, 64}
        final IncrementingHasher hasher = new IncrementingHasher(63, 1);

        // Shape: k=2 hash functions, m=72 bits → two 64-bit words in the bitmap array
        final BloomFilter bf = createFilter(Shape.fromKM(2, 72), hasher);

        final long[] bitMapArray = bf.asBitMapArray();

        // 72 bits require exactly 2 longs
        assertEquals(2, bitMapArray.length);

        // Bit 63 occupies the MSB of the first word
        assertEquals(0x8000000000000000L, bitMapArray[0]);

        // Bit 64 occupies the LSB of the second word
        assertEquals(0x1, bitMapArray[1]);
    }
}
