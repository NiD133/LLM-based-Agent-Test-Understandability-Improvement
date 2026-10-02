package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link SparseBloomFilter#estimateN()} returns the expected estimate of the
 * number of items merged into the filter, for a fixed test {@link Shape} (k = 17, m = 72).
 */
public class SparseBloomFilterTest_testEstimateN {

    /** Shape of the Bloom filters under test: 17 hash functions over 72 bits. */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /**
     * Creates an empty {@link SparseBloomFilter} using the {@link #TEST_SHAPE}.
     *
     * @return a new, empty filter.
     */
    private SparseBloomFilter createEmptyFilter() {
        return new SparseBloomFilter(TEST_SHAPE);
    }

    /**
     * Creates a filter populated by merging the given hasher into an empty filter.
     *
     * @param hasher the hasher used to populate the filter.
     * @return the populated filter.
     */
    private SparseBloomFilter createFilter(final Hasher hasher) {
        final SparseBloomFilter filter = createEmptyFilter();
        filter.merge(hasher);
        return filter;
    }

    /**
     * Tests that the size estimate is correctly calculated as items are merged in.
     */
    @Test
    final void testEstimateN() {
        // A single hasher yields an estimate of one item.
        final BloomFilter filter = createFilter(TestingHashers.FROM1);
        assertEquals(1, filter.estimateN());

        // Merging an overlapping hasher does not change the estimate.
        filter.merge(new IncrementingHasher(4, 1));
        assertEquals(1, filter.estimateN());

        // Merging a disjoint hasher raises the estimate to three.
        filter.merge(new IncrementingHasher(17, 1));
        assertEquals(3, filter.estimateN());

        // A completely saturated filter yields the maximum estimate.
        final BloomFilter fullFilter = TestingHashers.populateEntireFilter(createEmptyFilter());
        assertEquals(Integer.MAX_VALUE, fullFilter.estimateN());
    }
}
