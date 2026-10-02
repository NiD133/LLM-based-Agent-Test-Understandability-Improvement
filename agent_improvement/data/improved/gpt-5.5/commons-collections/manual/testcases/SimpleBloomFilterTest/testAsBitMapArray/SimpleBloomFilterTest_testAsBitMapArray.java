package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testAsBitMapArray {

    private static final Shape TWO_HASHES_OVER_SEVENTY_TWO_BITS = Shape.fromKM(2, 72);

    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter filter = new SimpleBloomFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    /**
     * Tests that asBitMapArray works correctly when the filter spans multiple
     * long values.
     */
    @Test
    final void testAsBitMapArray() {
        final IncrementingHasher hasher = new IncrementingHasher(63, 1);
        final BloomFilter<?> filter = createFilter(TWO_HASHES_OVER_SEVENTY_TWO_BITS, hasher);

        assertArrayEquals(new long[] {0x8000000000000000L, 0x1}, filter.asBitMapArray());
    }
}
