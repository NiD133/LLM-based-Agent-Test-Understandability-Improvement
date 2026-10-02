package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link SimpleBloomFilter#clear()} empties a populated filter.
 */
public class SimpleBloomFilterTest_testClear {

    /**
     * The shape used for the filter under test: 17 hash functions (k) over 72 bits (m).
     */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /**
     * Creates a {@link SimpleBloomFilter} of the given shape and populates it with the given hasher.
     *
     * @param shape  the shape of the filter.
     * @param hasher the hasher used to populate the filter.
     * @return the populated filter.
     */
    private SimpleBloomFilter createPopulatedFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter filter = new SimpleBloomFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    @Test
    void testClear() {
        final BloomFilter bf1 = createPopulatedFilter(TEST_SHAPE, TestingHashers.FROM1);

        // Precondition: merging the hasher must have set at least one bit.
        assertNotEquals(0, bf1.cardinality(), "Filter should contain bits before it is cleared");

        bf1.clear();

        // clear() must reset the filter to the empty state.
        assertEquals(0, bf1.cardinality(), "clear() should reset the cardinality to zero");
    }
}
