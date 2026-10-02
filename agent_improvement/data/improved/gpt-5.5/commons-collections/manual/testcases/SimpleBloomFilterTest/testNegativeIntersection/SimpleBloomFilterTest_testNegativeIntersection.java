package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testNegativeIntersection {

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    @Test
    final void testNegativeIntersection() {
        final IndexExtractor firstFilterIndices = IndexExtractor.fromIndexArray(
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                10, 11, 12, 13, 20, 26, 28, 30, 32, 34,
                35, 36, 37, 39, 40, 41, 42, 43, 45, 46,
                48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
                58, 59, 60, 61, 62, 63, 64, 65, 66, 67,
                68, 69, 70, 71);
        final IndexExtractor secondFilterIndices = IndexExtractor.fromIndexArray(
                1, 2, 3, 4, 5, 6, 7, 8, 9,
                10, 11, 12, 13, 14, 15, 16, 17, 18,
                19, 20, 21, 22, 23, 24, 25, 26, 27);

        final BloomFilter filter1 = createEmptyFilter(Shape.fromKM(17, 72));
        filter1.merge(firstFilterIndices);

        final BloomFilter filter2 = createEmptyFilter(Shape.fromKM(17, 72));
        filter2.merge(secondFilterIndices);

        assertEquals(0, filter1.estimateIntersection(filter2));
    }
}
