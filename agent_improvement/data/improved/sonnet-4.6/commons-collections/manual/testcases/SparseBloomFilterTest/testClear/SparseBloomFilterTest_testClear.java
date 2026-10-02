package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testClear {

    /** Shape used across all tests: k=17 hash functions, m=72 bits. */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /** Creates an empty SparseBloomFilter for the given shape. */
    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /** Creates a SparseBloomFilter pre-populated via the given hasher. */
    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter bf = createEmptyFilter(shape);
        bf.merge(hasher);
        return bf;
    }

    /**
     * Verifies that {@code clear()} removes all set bits so that cardinality
     * returns 0 after a non-empty filter is cleared.
     */
    @Test
    void testClear() {
        final BloomFilter bf1 = createFilter(getTestShape(), TestingHashers.FROM1);
        // Confirm the filter is non-empty before clearing
        assertNotEquals(0, bf1.cardinality());

        bf1.clear();

        // After clear(), the filter must report zero set bits
        assertEquals(0, bf1.cardinality());
    }
}
