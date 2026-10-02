package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SimpleBloomFilterTest_testCardinalityAndIsEmpty {

    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /**
     * Verifies that the filter reports empty before any bits are merged, and that
     * cardinality increases by one as each index is added.
     */
    private void assertCardinalityAndEmptyState(final BloomFilter<?> bloomFilter) {
        assertTrue(bloomFilter.isEmpty());
        assertEquals(0, bloomFilter.cardinality());
        for (int index = 0; index < TEST_SHAPE.getNumberOfBits(); index++) {
            bloomFilter.merge(IndexExtractor.fromIndexArray(index));
            assertFalse(bloomFilter.isEmpty(), "Wrong value at " + index);
            assertEquals(index + 1, bloomFilter.cardinality(), "Wrong value at " + index);
        }

        bloomFilter.clear();
        assertEquals(0, bloomFilter.cardinality());
        assertTrue(bloomFilter.isEmpty());
        for (int index = 0; index < TEST_SHAPE.getNumberOfBits(); index++) {
            bloomFilter.merge(IndexExtractor.fromIndexArray(index));
            assertEquals(index + 1, bloomFilter.cardinality(), "Wrong value at " + index);
            assertFalse(bloomFilter.isEmpty(), "Wrong value at " + index);
        }
    }

    @Test
    void testCardinalityAndIsEmpty() {
        assertCardinalityAndEmptyState(createEmptyFilter(TEST_SHAPE));
    }
}
