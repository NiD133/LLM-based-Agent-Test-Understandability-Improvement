package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link SimpleBloomFilter#cardinality()} and {@link SimpleBloomFilter#isEmpty()}
 * by setting bits one at a time and verifying that both methods track the state correctly.
 */
public class SimpleBloomFilterTest_testCardinalityAndIsEmpty {

    /** Shape used throughout this test: k=17 hash functions, m=72 bits. */
    protected Shape getTestShape() {
        return Shape.fromKM(17, 72);
    }

    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /**
     * Exercises {@code cardinality()} and {@code isEmpty()} while setting bits one at a time.
     *
     * <p>Phase 1 (isEmpty before cardinality): after each merge the filter must not be
     * empty and its cardinality must equal the number of bits set so far.
     *
     * <p>Phase 2 (cardinality before isEmpty): after a {@code clear()}, the same loop
     * runs again but the assertion order is reversed to confirm neither check interferes
     * with the other.
     *
     * @param bf an empty BloomFilter whose shape matches {@link #getTestShape()}
     */
    protected void testCardinalityAndIsEmpty(final BloomFilter bf) {
        final int totalBits = getTestShape().getNumberOfBits();

        // Verify the filter starts completely empty.
        assertTrue(bf.isEmpty());
        assertEquals(0, bf.cardinality());

        // Phase 1: set bits 0..totalBits-1 one at a time.
        // Check isEmpty() first, then cardinality().
        for (int i = 0; i < totalBits; i++) {
            bf.merge(IndexExtractor.fromIndexArray(i));
            assertFalse(bf.isEmpty(), "Wrong value at " + i);
            assertEquals(i + 1, bf.cardinality(), "Wrong value at " + i);
        }

        // Reset the filter and confirm it is empty again before Phase 2.
        bf.clear();
        assertEquals(0, bf.cardinality());
        assertTrue(bf.isEmpty());

        // Phase 2: same bit-by-bit merges, but check cardinality() before isEmpty()
        // to ensure neither method corrupts cached state read by the other.
        for (int i = 0; i < totalBits; i++) {
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
