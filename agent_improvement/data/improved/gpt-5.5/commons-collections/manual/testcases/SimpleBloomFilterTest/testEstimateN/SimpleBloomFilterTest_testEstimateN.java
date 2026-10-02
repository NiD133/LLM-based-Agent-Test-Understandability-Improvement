package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testEstimateN {

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /**
     * Creates the BloomFilter implementation being tested and initializes it
     * with the supplied hasher.
     *
     * @param shape the shape of the filter.
     * @param hasher the hasher used to populate the filter.
     * @return a populated BloomFilter implementation.
     */
    protected final SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter bloomFilter = createEmptyFilter(shape);
        bloomFilter.merge(hasher);
        return bloomFilter;
    }

    /**
     * The shape of the Bloom filters for testing.
     *
     * @return the testing shape.
     */
    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Tests that the size estimate is correctly calculated.
     */
    @Test
    final void testEstimateN() {
        BloomFilter<?> filter = createFilter(getTestShape(), TestingHashers.FROM1);
        assertEquals(1, filter.estimateN());

        // These hashers intentionally do not produce estimates equal to the
        // number of merged inputs.
        filter.merge(new IncrementingHasher(4, 1));
        assertEquals(1, filter.estimateN());

        filter.merge(new IncrementingHasher(17, 1));
        assertEquals(3, filter.estimateN());

        filter = TestingHashers.populateEntireFilter(createEmptyFilter(getTestShape()));
        assertEquals(Integer.MAX_VALUE, filter.estimateN());
    }
}
