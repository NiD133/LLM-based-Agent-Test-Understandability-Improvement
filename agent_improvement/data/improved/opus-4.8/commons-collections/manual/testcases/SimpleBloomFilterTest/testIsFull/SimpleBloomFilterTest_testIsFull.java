package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SimpleBloomFilter#isFull()}.
 */
public class SimpleBloomFilterTest_testIsFull {

    /**
     * The shape used for every filter in this test:
     * 17 hash functions (k) over 72 bits (m).
     */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /** Creates an empty filter with the test shape. */
    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /** Creates a filter populated by merging the given hasher. */
    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    /**
     * isFull() is false for an empty filter, true once every bit is set,
     * and false again for a filter that only sets some of the bits.
     */
    @Test
    final void testIsFull() {
        // An empty filter has no bits set, so it is not full.
        BloomFilter filter = createEmptyFilter(getTestShape());
        assertFalse(filter.isFull(), "Should not be full");

        // Setting every bit makes the filter full.
        filter = TestingHashers.populateEntireFilter(filter);
        assertTrue(filter.isFull(), "Should be full");

        // A filter populated from a single hasher sets only some bits, so it is not full.
        filter = createFilter(getTestShape(), new IncrementingHasher(1, 3));
        assertFalse(filter.isFull(), "Should not be full");
    }
}
