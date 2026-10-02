package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testCopy {

    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter bloomFilter = new SimpleBloomFilter(shape);
        bloomFilter.merge(hasher);
        return bloomFilter;
    }

    @Test
    void testCopy() {
        final BloomFilter<?> original = createFilter(TEST_SHAPE, TestingHashers.FROM1);

        assertNotEquals(0, original.cardinality());

        final BloomFilter<?> copy = original.copy();

        assertNotSame(original, copy);
        assertArrayEquals(original.asBitMapArray(), copy.asBitMapArray());
        assertArrayEquals(original.asIndexArray(), copy.asIndexArray());
        assertEquals(original.cardinality(), copy.cardinality());
        assertEquals(original.characteristics(), copy.characteristics());
        assertEquals(original.estimateN(), copy.estimateN());
        assertEquals(original.getClass(), copy.getClass());
        assertEquals(original.getShape(), copy.getShape());
        assertEquals(original.isEmpty(), copy.isEmpty());
        assertEquals(original.isFull(), copy.isFull());
    }
}
