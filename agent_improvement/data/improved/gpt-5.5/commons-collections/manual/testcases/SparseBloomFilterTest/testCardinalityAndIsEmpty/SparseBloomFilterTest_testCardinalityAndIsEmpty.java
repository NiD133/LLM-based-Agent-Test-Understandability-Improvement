package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testCardinalityAndIsEmpty {

    private static final int HASH_FUNCTIONS = 17;
    private static final int NUMBER_OF_BITS = 72;

    protected SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    /**
     * The shape of the Bloom filters for testing.
     *
     * @return the testing shape.
     */
    protected Shape getTestShape() {
        return Shape.fromKM(HASH_FUNCTIONS, NUMBER_OF_BITS);
    }

    /**
     * Tests cardinality and isEmpty. Bloom filter must be able to accept multiple
     * IndexExtractor merges until all the bits are populated.
     *
     * @param bf The Bloom filter to test.
     */
    protected void testCardinalityAndIsEmpty(final BloomFilter bf) {
        final Shape testShape = getTestShape();

        assertTrue(bf.isEmpty());
        assertEquals(0, bf.cardinality());
        mergeEachIndexAndAssertIsEmptyFirst(bf, testShape);

        bf.clear();
        assertEquals(0, bf.cardinality());
        assertTrue(bf.isEmpty());
        mergeEachIndexAndAssertCardinalityFirst(bf, testShape);
    }

    private void mergeEachIndexAndAssertIsEmptyFirst(final BloomFilter bf, final Shape shape) {
        for (int i = 0; i < shape.getNumberOfBits(); i++) {
            bf.merge(IndexExtractor.fromIndexArray(i));
            assertFalse(bf.isEmpty(), "Wrong value at " + i);
            assertEquals(i + 1, bf.cardinality(), "Wrong value at " + i);
        }
    }

    private void mergeEachIndexAndAssertCardinalityFirst(final BloomFilter bf, final Shape shape) {
        for (int i = 0; i < shape.getNumberOfBits(); i++) {
            bf.merge(IndexExtractor.fromIndexArray(i));
            assertEquals(i + 1, bf.cardinality(), "Wrong value at " + i);
            assertFalse(bf.isEmpty(), "Wrong value at " + i);
        }
    }

    @Test
    void testCardinalityAndIsEmpty() {
        testCardinalityAndIsEmpty(createEmptyFilter(getTestShape()));
    }
}
