package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testMergeShortBitMapExtractor {

    private static final int NUMBER_OF_HASH_FUNCTIONS = 17;
    private static final int NUMBER_OF_BITS = 72;
    private static final long SINGLE_BITMAP_WITH_ONE_SET_BIT = 2L;

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    protected Shape getTestShape() {
        return Shape.fromKM(NUMBER_OF_HASH_FUNCTIONS, NUMBER_OF_BITS);
    }

    @Test
    void testMergeShortBitMapExtractor() {
        final SimpleBloomFilter filter = createEmptyFilter(getTestShape());
        final BitMapExtractor bitMapExtractor = predicate -> predicate.test(SINGLE_BITMAP_WITH_ONE_SET_BIT);

        assertTrue(filter.merge(bitMapExtractor));
        assertEquals(1, filter.cardinality());
    }
}
