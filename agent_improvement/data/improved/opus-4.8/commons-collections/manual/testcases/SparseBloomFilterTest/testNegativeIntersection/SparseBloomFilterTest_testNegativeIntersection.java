package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link SparseBloomFilter} intersection estimation when the two
 * filters share too few bits for the estimator to report any overlap.
 */
public class SparseBloomFilterTest_testNegativeIntersection {

    /** Shape shared by every filter in this test: k = 17 hash functions, m = 72 bits. */
    private static final Shape SHAPE = Shape.fromKM(17, 72);

    /** Creates an empty {@link SparseBloomFilter} with the test {@link #SHAPE}. */
    private SparseBloomFilter newFilter(final IndexExtractor indices) {
        final SparseBloomFilter filter = new SparseBloomFilter(SHAPE);
        filter.merge(indices);
        return filter;
    }

    /**
     * Two filters whose enabled bits barely overlap should estimate an
     * intersection cardinality of zero.
     */
    @Test
    final void testNegativeIntersection() {
        final SparseBloomFilter filter1 = newFilter(IndexExtractor.fromIndexArray(
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 20, 26, 28, 30, 32, 34,
                35, 36, 37, 39, 40, 41, 42, 43, 45, 46, 48, 49, 50, 51, 52, 53, 54, 55,
                56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71));
        final SparseBloomFilter filter2 = newFilter(IndexExtractor.fromIndexArray(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27));

        assertEquals(0, filter1.estimateIntersection(filter2));
    }
}
