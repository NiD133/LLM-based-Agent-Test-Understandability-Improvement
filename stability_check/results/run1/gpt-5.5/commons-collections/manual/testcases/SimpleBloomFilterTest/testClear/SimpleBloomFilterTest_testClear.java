package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testClear {

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    @Test
    void testClear() {
        final BloomFilter<?> filter = createFilter(getTestShape(), TestingHashers.FROM1);

        assertNotEquals(0, filter.cardinality());
        filter.clear();
        assertEquals(0, filter.cardinality());
    }
}
