package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SimpleBloomFilter#estimateN()} reports the expected
 * estimated item count as bits are progressively merged into the filter.
 */
public class SimpleBloomFilterTest_testEstimateN {

    /**
     * The shape shared by every filter in this test: 17 hash functions (k)
     * spread over 72 bits (m).
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Creates an empty {@link SimpleBloomFilter} for the given shape.
     */
    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /**
     * Creates a filter for the given shape and populates it from the given hasher.
     */
    private BloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final BloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    /**
     * Tests that the size estimate is correctly calculated as bits are added.
     */
    @Test
    final void testEstimateN() {
        final Shape shape = getTestShape();

        // A filter built from a single hasher estimates one item.
        final BloomFilter filter = createFilter(shape, TestingHashers.FROM1);
        assertEquals(1, filter.estimateN());

        // Merging a hasher whose bits overlap the existing ones leaves the
        // estimate unchanged (the data above does not raise the estimate).
        filter.merge(new IncrementingHasher(4, 1));
        assertEquals(1, filter.estimateN());

        // Merging a hasher that adds further distinct bits raises the estimate.
        filter.merge(new IncrementingHasher(17, 1));
        assertEquals(3, filter.estimateN());

        // A fully populated filter saturates the estimate at Integer.MAX_VALUE.
        final BloomFilter fullFilter =
                TestingHashers.populateEntireFilter(createEmptyFilter(shape));
        assertEquals(Integer.MAX_VALUE, fullFilter.estimateN());
    }
}
