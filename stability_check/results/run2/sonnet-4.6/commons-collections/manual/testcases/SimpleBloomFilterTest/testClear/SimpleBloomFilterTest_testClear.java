package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testClear {

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    protected SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter bf = createEmptyFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Verifies that clear() resets the filter cardinality to zero.
     * A non-empty filter is created, confirmed non-empty, cleared, and confirmed empty.
     */
    @Test
    void testClear() {
        final BloomFilter bf1 = createFilter(getTestShape(), TestingHashers.FROM1);
        assertNotEquals(0, bf1.cardinality());
        bf1.clear();
        assertEquals(0, bf1.cardinality());
    }
}
