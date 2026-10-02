package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SimpleBloomFilter#estimateIntersection(BloomFilter)} never
 * returns a negative value.
 *
 * <p>The intersection estimate is derived from {@code estimateN(filter1) +
 * estimateN(filter2) - estimateN(union)}. For certain bit patterns this raw
 * formula yields a negative number; the implementation is expected to clamp the
 * result to {@code 0} rather than report a negative cardinality.</p>
 */
public class SimpleBloomFilterTest_testNegativeIntersection {

    /** Shape used for every filter in this test: 17 hash functions over 72 bits. */
    private static final Shape SHAPE = Shape.fromKM(17, 72);

    /** Creates an empty Bloom filter with the test {@link #SHAPE}. */
    private SimpleBloomFilter createEmptyFilter() {
        return new SimpleBloomFilter(SHAPE);
    }

    @Test
    final void testNegativeIntersection() {
        // Two index sets whose estimated intersection would compute as negative.
        final IndexExtractor indices1 = IndexExtractor.fromIndexArray(
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 20, 26, 28, 30, 32,
                34, 35, 36, 37, 39, 40, 41, 42, 43, 45, 46, 48, 49, 50, 51, 52, 53,
                54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71);
        final IndexExtractor indices2 = IndexExtractor.fromIndexArray(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 21, 22, 23, 24, 25, 26, 27);

        final BloomFilter filter1 = createEmptyFilter();
        filter1.merge(indices1);

        final BloomFilter filter2 = createEmptyFilter();
        filter2.merge(indices2);

        // The estimate must be clamped to 0, never negative.
        assertEquals(0, filter1.estimateIntersection(filter2));
    }
}
