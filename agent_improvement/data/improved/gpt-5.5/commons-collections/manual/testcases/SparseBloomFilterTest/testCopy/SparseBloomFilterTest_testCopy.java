package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testCopy {

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter bloomFilter = new SparseBloomFilter(shape);
        bloomFilter.merge(hasher);
        return bloomFilter;
    }

    private void assertCopyMatchesOriginal(final boolean assertClass) {
        final BloomFilter<?> original = createFilter(getTestShape(), TestingHashers.FROM1);

        assertNotEquals(0, original.cardinality());

        final BloomFilter<?> copy = original.copy();

        assertNotSame(original, copy);
        assertArrayEquals(original.asBitMapArray(), copy.asBitMapArray());
        assertArrayEquals(original.asIndexArray(), copy.asIndexArray());
        assertEquals(original.cardinality(), copy.cardinality());
        assertEquals(original.characteristics(), copy.characteristics());
        assertEquals(original.estimateN(), copy.estimateN());
        if (assertClass) {
            assertEquals(original.getClass(), copy.getClass());
        }
        assertEquals(original.getShape(), copy.getShape());
        assertEquals(original.isEmpty(), copy.isEmpty());
        assertEquals(original.isFull(), copy.isFull());
    }

    @Test
    void testCopy() {
        assertCopyMatchesOriginal(true);
    }
}
