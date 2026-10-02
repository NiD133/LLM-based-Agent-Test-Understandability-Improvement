package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SparseBloomFilter#clear()}.
 */
public class SparseBloomFilterTest_testClear {

    /**
     * The shape used for the filters under test: 17 hash functions over 72 bits.
     */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /**
     * Builds a filter for the given shape populated by merging the supplied hasher.
     *
     * @param shape  the shape of the filter.
     * @param hasher the hasher used to populate the filter.
     * @return a populated filter.
     */
    private BloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter filter = new SparseBloomFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    @Test
    void testClear() {
        final BloomFilter filter = createFilter(TEST_SHAPE, TestingHashers.FROM1);
        // The filter starts out populated.
        assertNotEquals(0, filter.cardinality());

        filter.clear();

        // After clearing, no bits remain set.
        assertEquals(0, filter.cardinality());
    }
}
