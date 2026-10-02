package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SimpleBloomFilter#estimateIntersection(BloomFilter)}, which estimates
 * the number of distinct items shared between two Bloom filters.
 */
public class SimpleBloomFilterTest_testEstimateIntersection {

    /**
     * The shape shared by every filter under test:
     * <ul>
     *   <li>k = 17 hash functions</li>
     *   <li>m = 72 bits</li>
     * </ul>
     */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /**
     * Creates an empty {@link SimpleBloomFilter} using {@link #TEST_SHAPE}.
     *
     * @return a new empty filter.
     */
    private SimpleBloomFilter createEmptyFilter() {
        return new SimpleBloomFilter(TEST_SHAPE);
    }

    /**
     * Creates a filter using {@link #TEST_SHAPE} and populates it with the given hasher.
     *
     * @param hasher the hasher used to set bits in the filter.
     * @return a new populated filter.
     */
    private BloomFilter createFilter(final Hasher hasher) {
        final SimpleBloomFilter filter = createEmptyFilter();
        filter.merge(hasher);
        return filter;
    }

    /**
     * Tests that the estimated intersection calculations are correct across several
     * combinations: overlapping filters, an empty filter, fully populated filters,
     * and disjoint filters.
     */
    @Test
    final void testEstimateIntersection() {
        // A filter holding the single "FROM1" item, and one holding both "FROM1" and "FROM11".
        final BloomFilter filterFrom1 = createFilter(TestingHashers.FROM1);
        final BloomFilter filterFrom1And11 =
                TestingHashers.populateFromHashersFrom1AndFrom11(createEmptyFilter());
        // A completely full filter (every bit set).
        final BloomFilter fullFilter = TestingHashers.populateEntireFilter(createEmptyFilter());

        // filterFrom1 and filterFrom1And11 share exactly the single "FROM1" item.
        assertEquals(1, filterFrom1.estimateIntersection(filterFrom1And11));
        assertEquals(1, filterFrom1And11.estimateIntersection(filterFrom1));

        // filterFrom1's single item is also contained in the full filter.
        assertEquals(1, filterFrom1.estimateIntersection(fullFilter));
        assertEquals(1, filterFrom1And11.estimateIntersection(filterFrom1));

        // The full filter and filterFrom1And11 share both of the latter's items.
        assertEquals(2, fullFilter.estimateIntersection(filterFrom1And11));

        // Intersecting with an empty filter yields zero in both directions.
        final BloomFilter emptyFilter = createEmptyFilter();
        assertEquals(0, filterFrom1.estimateIntersection(emptyFilter));
        assertEquals(0, emptyFilter.estimateIntersection(filterFrom1));

        // Two disjoint filters covering the lower and upper halves of the bit range.
        final int midPoint = TEST_SHAPE.getNumberOfBits() / 2;
        final BloomFilter lowerHalfFilter =
                TestingHashers.populateRange(createEmptyFilter(), 0, midPoint);
        final BloomFilter upperHalfFilter =
                TestingHashers.populateRange(createEmptyFilter(), midPoint + 1, TEST_SHAPE.getNumberOfBits() - 1);
        // Estimating the intersection of disjoint filters is not supported.
        assertThrows(IllegalArgumentException.class,
                () -> lowerHalfFilter.estimateIntersection(upperHalfFilter));

        // Intersecting a full filter with itself estimates an "infinite" count.
        assertEquals(Integer.MAX_VALUE, fullFilter.estimateIntersection(fullFilter));
    }
}
