package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link SimpleBloomFilter#estimateUnion(BloomFilter)}.
 *
 * <p>The estimate counts how many distinct items the union of two filters is
 * expected to contain. Each filter built here represents a single item, so:</p>
 * <ul>
 *   <li>two filters seeded from different items estimate a union of 2;</li>
 *   <li>a filter combined with an empty filter estimates a union of 1.</li>
 * </ul>
 */
public class SimpleBloomFilterTest_testEstimateUnion {

    /** Shape used for every filter under test: k = 17 hash functions, m = 72 bits. */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /** Creates an empty filter with the given shape. */
    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /** Creates a filter with the given shape and populates it from the supplied hasher. */
    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    @Test
    final void testEstimateUnion() {
        final BloomFilter filterFrom1 = createFilter(getTestShape(), TestingHashers.FROM1);
        final BloomFilter filterFrom11 = createFilter(getTestShape(), TestingHashers.FROM11);

        // Two distinct single-item filters: the union is estimated at 2 items, regardless of order.
        assertEquals(2, filterFrom1.estimateUnion(filterFrom11));
        assertEquals(2, filterFrom11.estimateUnion(filterFrom1));

        // Union with an empty filter contributes nothing: the estimate stays at 1 item, regardless of order.
        final BloomFilter emptyFilter = createEmptyFilter(getTestShape());
        assertEquals(1, filterFrom1.estimateUnion(emptyFilter));
        assertEquals(1, emptyFilter.estimateUnion(filterFrom1));
    }
}
