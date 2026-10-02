package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SimpleBloomFilter#clear()} resets a populated filter back to empty.
 */
public class SimpleBloomFilterTest_testClear {

    /**
     * The shape used for the filter under test: 17 hash functions (k) over 72 bits (m).
     */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /**
     * Builds a Bloom filter of the test shape and populates it using the given hasher.
     *
     * @param hasher the hasher whose indices are merged into the filter.
     * @return a populated Bloom filter.
     */
    private BloomFilter createPopulatedFilter(final Hasher hasher) {
        final SimpleBloomFilter filter = new SimpleBloomFilter(TEST_SHAPE);
        filter.merge(hasher);
        return filter;
    }

    @Test
    void testClear() {
        final BloomFilter filter = createPopulatedFilter(TestingHashers.FROM1);

        // The filter starts out with bits set.
        assertNotEquals(0, filter.cardinality());

        // Clearing must remove every set bit.
        filter.clear();
        assertEquals(0, filter.cardinality());
    }
}
