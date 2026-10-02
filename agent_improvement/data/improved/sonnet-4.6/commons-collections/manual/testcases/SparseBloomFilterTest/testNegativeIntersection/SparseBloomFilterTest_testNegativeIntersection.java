package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link SparseBloomFilter#estimateIntersection} clamps its result
 * to zero when the probabilistic formula would otherwise yield a negative value.
 *
 * <p>The estimation is based on the Bloom-filter cardinality formula:
 * {@code estimateIntersection = estimateN(A) + estimateN(B) - estimateN(A ∪ B)}.
 * For highly saturated filters the union estimate can exceed the sum of the
 * individual estimates, producing a mathematically negative intersection.
 * The implementation must return 0 in that case rather than a negative number.
 */
public class SparseBloomFilterTest_testNegativeIntersection {

    /** Shape used throughout this test: k=17 hash functions, m=72 bits. */
    private static final Shape SHAPE = Shape.fromKM(17, 72);

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    @Test
    void testNegativeIntersection() {
        // A dense filter occupying most of the bit space (54 out of 72 bits set).
        // Its high saturation causes the individual cardinality estimate to be large.
        final IndexExtractor p1 = IndexExtractor.fromIndexArray(
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
                20, 26, 28, 30, 32, 34, 35, 36, 37, 39, 40, 41, 42, 43,
                45, 46, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
                60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71);

        // A second filter covering the lower half of the bit space (27 bits set).
        // Together the two filters cover nearly all 72 bits, making the union
        // estimate so large that the intersection formula goes negative.
        final IndexExtractor p2 = IndexExtractor.fromIndexArray(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
                15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);

        final BloomFilter filter1 = createEmptyFilter(SHAPE);
        filter1.merge(p1);

        final BloomFilter filter2 = createEmptyFilter(SHAPE);
        filter2.merge(p2);

        // The probabilistic formula yields a negative value here; the implementation
        // must clamp it to 0 instead of returning a negative estimate.
        assertEquals(0, filter1.estimateIntersection(filter2));
    }
}
