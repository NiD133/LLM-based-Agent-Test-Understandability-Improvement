package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SparseBloomFilter#estimateIntersection(BloomFilter)}.
 */
public class SparseBloomFilterTest_testEstimateIntersection {

    /**
     * The shape shared by every filter in this test: 17 hash functions (k) over 72 bits (m).
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Creates an empty {@link SparseBloomFilter} with the test shape.
     */
    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * Creates a filter populated by the given hasher.
     */
    private BloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    /**
     * Verifies that {@code estimateIntersection} reports the expected number of shared elements,
     * including the edge cases for an empty filter, disjoint filters, and saturated ("infinite") filters.
     */
    @Test
    final void testEstimateIntersection() {
        // Filters that share exactly one source element (FROM1).
        final BloomFilter bf = createFilter(getTestShape(), TestingHashers.FROM1);
        final BloomFilter bf2 = TestingHashers.populateFromHashersFrom1AndFrom11(createEmptyFilter(getTestShape()));
        // A fully populated filter (every bit set).
        final BloomFilter bf3 = TestingHashers.populateEntireFilter(createEmptyFilter(getTestShape()));

        // Filters overlapping in a single element estimate an intersection of 1, regardless of order.
        assertEquals(1, bf.estimateIntersection(bf2));
        assertEquals(1, bf2.estimateIntersection(bf));
        assertEquals(1, bf.estimateIntersection(bf3));
        assertEquals(1, bf2.estimateIntersection(bf));
        assertEquals(2, bf3.estimateIntersection(bf2));

        // Intersection with an empty filter is always 0.
        final BloomFilter bf4 = createEmptyFilter(getTestShape());
        assertEquals(0, bf.estimateIntersection(bf4));
        assertEquals(0, bf4.estimateIntersection(bf));

        // Two filters covering disjoint halves of the bit range cannot be compared: the
        // estimate is undefined and an IllegalArgumentException is thrown.
        final int midPoint = getTestShape().getNumberOfBits() / 2;
        final BloomFilter bf5 = TestingHashers.populateRange(createEmptyFilter(getTestShape()), 0, midPoint);
        final BloomFilter bf6 = TestingHashers.populateRange(createEmptyFilter(getTestShape()), midPoint + 1, getTestShape().getNumberOfBits() - 1);
        assertThrows(IllegalArgumentException.class, () -> bf5.estimateIntersection(bf6));

        // A saturated filter intersected with itself estimates an infinite count (Integer.MAX_VALUE).
        assertEquals(Integer.MAX_VALUE, bf3.estimateIntersection(bf3));
    }
}
