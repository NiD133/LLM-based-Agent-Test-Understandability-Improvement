package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SimpleBloomFilter#merge(BitMapExtractor)} accepts a
 * {@link BitMapExtractor} that supplies fewer bit maps than the filter's shape
 * allows.
 */
public class SimpleBloomFilterTest_testMergeShortBitMapExtractor {

    /** Shape with 17 hash functions (k) over 72 bits (m), which spans two long bit maps. */
    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    @Test
    void testMergeShortBitMapExtractor() {
        final SimpleBloomFilter filter = createEmptyFilter(getTestShape());

        // The 72-bit shape spans two long bit maps, but this extractor supplies
        // only one (with a single bit set). Merging a "short" extractor is allowed.
        final BitMapExtractor shortBitMapExtractor = predicate -> predicate.test(2L);

        assertTrue(filter.merge(shortBitMapExtractor), "merge should report success");
        assertEquals(1, filter.cardinality(), "exactly one bit should be set");
    }
}
