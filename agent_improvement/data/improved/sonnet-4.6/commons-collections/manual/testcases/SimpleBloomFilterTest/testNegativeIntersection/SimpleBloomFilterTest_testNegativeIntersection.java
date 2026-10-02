package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testNegativeIntersection {

    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /**
     * Verifies that estimateIntersection returns 0 when the two filters represent
     * disjoint populations, even though a few bit positions overlap.
     * This "negative" result arises from the probabilistic formula: the estimated
     * cardinality of the union exceeds the sum of the individual cardinalities,
     * which clamps the intersection estimate to zero.
     */
    @Test
    void testNegativeIntersection() {
        // filter1 spans most of the bit space (54 bits set)
        final IndexExtractor indicesForFilter1 = IndexExtractor.fromIndexArray(
                0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13,
                20, 26, 28, 30, 32, 34, 35, 36, 37, 39, 40, 41, 42, 43,
                45, 46, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
                60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71);

        // filter2 covers consecutive low bits (27 bits set)
        final IndexExtractor indicesForFilter2 = IndexExtractor.fromIndexArray(
                1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13,
                14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);

        final BloomFilter filter1 = createEmptyFilter(TEST_SHAPE);
        filter1.merge(indicesForFilter1);

        final BloomFilter filter2 = createEmptyFilter(TEST_SHAPE);
        filter2.merge(indicesForFilter2);

        // The probabilistic intersection estimate is zero despite shared bits.
        assertEquals(0, filter1.estimateIntersection(filter2));
    }
}
