package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testEstimateIntersection {

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    private SimpleBloomFilter createFilter(final Shape shape, final Hasher hasher) {
        final SimpleBloomFilter bloomFilter = createEmptyFilter(shape);
        bloomFilter.merge(hasher);
        return bloomFilter;
    }

    private Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    /**
     * Tests that the estimated intersection calculations are correct.
     */
    @Test
    final void testEstimateIntersection() {
        final Shape shape = getTestShape();
        final BloomFilter bf = createFilter(shape, TestingHashers.FROM1);
        final BloomFilter bf2 = TestingHashers.populateFromHashersFrom1AndFrom11(createEmptyFilter(shape));
        final BloomFilter bf3 = TestingHashers.populateEntireFilter(createEmptyFilter(shape));

        assertEquals(1, bf.estimateIntersection(bf2));
        assertEquals(1, bf2.estimateIntersection(bf));
        assertEquals(1, bf.estimateIntersection(bf3));
        assertEquals(1, bf2.estimateIntersection(bf));
        assertEquals(2, bf3.estimateIntersection(bf2));

        final BloomFilter bf4 = createEmptyFilter(shape);
        assertEquals(0, bf.estimateIntersection(bf4));
        assertEquals(0, bf4.estimateIntersection(bf));

        final int midPoint = shape.getNumberOfBits() / 2;
        final BloomFilter bf5 = TestingHashers.populateRange(createEmptyFilter(shape), 0, midPoint);
        final BloomFilter bf6 = TestingHashers.populateRange(createEmptyFilter(shape), midPoint + 1, shape.getNumberOfBits() - 1);
        assertThrows(IllegalArgumentException.class, () -> bf5.estimateIntersection(bf6));

        // infinite with infinite
        assertEquals(Integer.MAX_VALUE, bf3.estimateIntersection(bf3));
    }
}
