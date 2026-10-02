package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testEstimateIntersection {

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    private SparseBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SparseBloomFilter filter = createEmptyFilter(shape);
        filter.merge(hasher);
        return filter;
    }

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Tests that the estimated intersection calculations are correct.
     */
    @Test
    final void testEstimateIntersection() {
        final Shape testShape = getTestShape();

        final BloomFilter<?> fromOne = createFilter(testShape, TestingHashers.FROM1);
        final BloomFilter<?> fromOneAndEleven = TestingHashers.populateFromHashersFrom1AndFrom11(createEmptyFilter(testShape));
        final BloomFilter<?> fullFilter = TestingHashers.populateEntireFilter(createEmptyFilter(testShape));

        assertEquals(1, fromOne.estimateIntersection(fromOneAndEleven));
        assertEquals(1, fromOneAndEleven.estimateIntersection(fromOne));
        assertEquals(1, fromOne.estimateIntersection(fullFilter));
        assertEquals(1, fromOneAndEleven.estimateIntersection(fromOne));
        assertEquals(2, fullFilter.estimateIntersection(fromOneAndEleven));

        final BloomFilter<?> emptyFilter = createEmptyFilter(testShape);
        assertEquals(0, fromOne.estimateIntersection(emptyFilter));
        assertEquals(0, emptyFilter.estimateIntersection(fromOne));

        final int midPoint = testShape.getNumberOfBits() / 2;
        final BloomFilter<?> lowerHalf = TestingHashers.populateRange(createEmptyFilter(testShape), 0, midPoint);
        final BloomFilter<?> upperHalf = TestingHashers.populateRange(createEmptyFilter(testShape), midPoint + 1,
                testShape.getNumberOfBits() - 1);
        assertThrows(IllegalArgumentException.class, () -> lowerHalf.estimateIntersection(upperHalf));

        // A completely full filter has an infinite cardinality estimate.
        assertEquals(Integer.MAX_VALUE, fullFilter.estimateIntersection(fullFilter));
    }
}
