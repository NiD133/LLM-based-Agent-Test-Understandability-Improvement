package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testClear {

    private static final int HASH_FUNCTION_COUNT = 17;
    private static final int BIT_COUNT = 72;

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    protected final SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter bloomFilter = createEmptyFilter(shape);
        bloomFilter.merge(hasher);
        return bloomFilter;
    }

    protected Shape getTestShape() {
        return Shape.fromKM(HASH_FUNCTION_COUNT, BIT_COUNT);
    }

    @Test
    void testClear() {
        final BloomFilter<?> bloomFilter = createFilter(getTestShape(), TestingHashers.FROM1);

        assertNotEquals(0, bloomFilter.cardinality());
        bloomFilter.clear();
        assertEquals(0, bloomFilter.cardinality());
    }
}
