package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testCardinalityAndIsEmpty {

    private SparseBloomFilter createEmptyFilter(final Shape shape) {
        return new SparseBloomFilter(shape);
    }

    private Shape getTestShape() {
        // k=17 hash functions, m=72 bits
        return Shape.fromKM(17, 72);
    }

    /**
     * Verifies that cardinality and isEmpty remain consistent as bits are set one by one,
     * then verifies the same properties hold after clearing the filter and repeating in
     * the opposite assertion order.
     */
    private void assertCardinalityAndIsEmptyConsistency(final BloomFilter bf) {
        // Initial state: filter must be empty with cardinality 0
        assertTrue(bf.isEmpty());
        assertEquals(0, bf.cardinality());

        // Set each bit individually; after each merge the filter must be non-empty
        // and cardinality must equal the number of bits set so far
        int numberOfBits = getTestShape().getNumberOfBits();
        for (int i = 0; i < numberOfBits; i++) {
            bf.merge(IndexExtractor.fromIndexArray(i));
            assertFalse(bf.isEmpty(), "Filter should be non-empty after setting bit " + i);
            assertEquals(i + 1, bf.cardinality(), "Cardinality should be " + (i + 1) + " after setting bit " + i);
        }

        // After clearing, the filter must again be empty with cardinality 0
        bf.clear();
        assertEquals(0, bf.cardinality());
        assertTrue(bf.isEmpty());

        // Repeat the same bit-by-bit population, this time checking cardinality before isEmpty
        for (int i = 0; i < numberOfBits; i++) {
            bf.merge(IndexExtractor.fromIndexArray(i));
            assertEquals(i + 1, bf.cardinality(), "Cardinality should be " + (i + 1) + " after setting bit " + i);
            assertFalse(bf.isEmpty(), "Filter should be non-empty after setting bit " + i);
        }
    }

    @Test
    void testCardinalityAndIsEmpty() {
        assertCardinalityAndIsEmptyConsistency(createEmptyFilter(getTestShape()));
    }
}
