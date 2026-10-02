package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies how a {@link SimpleBloomFilter} reports {@link BloomFilter#cardinality()}
 * and {@link BloomFilter#isEmpty()} as individual bits are merged in and then cleared.
 */
public class SimpleBloomFilterTest_testCardinalityAndIsEmpty {

    /** Number of hash functions (k) for the test filter's shape. */
    private static final int NUMBER_OF_HASH_FUNCTIONS = 17;

    /** Number of bits (m) for the test filter's shape. */
    private static final int NUMBER_OF_BITS = 72;

    /** The shape shared by every filter in this test. */
    private static final Shape TEST_SHAPE = Shape.fromKM(NUMBER_OF_HASH_FUNCTIONS, NUMBER_OF_BITS);

    /**
     * A freshly created filter must report itself as empty with zero cardinality.
     * Setting one bit at a time must grow the cardinality by exactly one per bit,
     * and the filter must stop being empty after the first bit is set. After a
     * {@link BloomFilter#clear()} the filter must return to its empty state, and
     * the same growth behaviour must hold again.
     */
    @Test
    void testCardinalityAndIsEmpty() {
        final BloomFilter bf = new SimpleBloomFilter(TEST_SHAPE);

        // A brand new filter holds no bits.
        assertTrue(bf.isEmpty());
        assertEquals(0, bf.cardinality());

        // Setting each bit in turn raises the cardinality by one and leaves the filter non-empty.
        for (int bitIndex = 0; bitIndex < NUMBER_OF_BITS; bitIndex++) {
            bf.merge(IndexExtractor.fromIndexArray(bitIndex));
            assertFalse(bf.isEmpty(), "Wrong value at " + bitIndex);
            assertEquals(bitIndex + 1, bf.cardinality(), "Wrong value at " + bitIndex);
        }

        // Clearing resets the filter back to empty with zero cardinality.
        bf.clear();
        assertEquals(0, bf.cardinality());
        assertTrue(bf.isEmpty());

        // Repeat the growth check, asserting cardinality before emptiness this time.
        for (int bitIndex = 0; bitIndex < NUMBER_OF_BITS; bitIndex++) {
            bf.merge(IndexExtractor.fromIndexArray(bitIndex));
            assertEquals(bitIndex + 1, bf.cardinality(), "Wrong value at " + bitIndex);
            assertFalse(bf.isEmpty(), "Wrong value at " + bitIndex);
        }
    }
}
