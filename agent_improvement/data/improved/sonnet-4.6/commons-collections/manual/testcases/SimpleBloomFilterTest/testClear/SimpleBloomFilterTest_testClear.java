package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testClear {

    /** Shape used across all tests: 17 hash functions, 72 bits. */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /** Creates a SimpleBloomFilter pre-populated with the given hasher's indices. */
    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter bf = new SimpleBloomFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    /**
     * Verifies that {@code clear()} resets the filter to empty (cardinality == 0)
     * after it has been populated with at least one element.
     */
    @Test
    void testClear() {
        final Shape shape = getTestShape();
        final SimpleBloomFilter bf = createFilter(shape, TestingHashers.FROM1);

        // Confirm the filter is non-empty before clearing.
        assertNotEquals(0, bf.cardinality());

        bf.clear();

        // After clearing, the filter must report zero set bits.
        assertEquals(0, bf.cardinality());
    }
}
