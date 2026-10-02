package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testClear {

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter bloomFilter = createEmptyFilter(shape);
        bloomFilter.merge(hasher);
        return bloomFilter;
    }

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    @Test
    void testClear() {
        final BloomFilter<?> bloomFilter = createFilter(getTestShape(), TestingHashers.FROM1);

        assertNotEquals(0, bloomFilter.cardinality());
        bloomFilter.clear();
        assertEquals(0, bloomFilter.cardinality());
    }
}
