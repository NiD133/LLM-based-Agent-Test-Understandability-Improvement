package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SparseBloomFilter#cardinality()} and {@link SparseBloomFilter#isEmpty()}.
 *
 * <p>A freshly created filter must report empty with zero cardinality. As distinct
 * bit indices are merged in one at a time, the cardinality must grow by exactly one
 * per index and the filter must stop reporting empty. Clearing the filter must reset
 * it back to the empty state.</p>
 */
public class SparseBloomFilterTest_testCardinalityAndIsEmpty {

    /** Shape used for the filter under test: k = 17 hash functions, m = 72 bits. */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /**
     * Merges the indices {@code 0, 1, ... numberOfBits-1} one at a time and verifies
     * that after each merge the filter is non-empty and its cardinality equals the
     * number of indices added so far.
     *
     * @param filter the filter to populate; expected to start empty.
     */
    private void mergeEachIndexAndAssertGrowingCardinality(final BloomFilter filter) {
        for (int index = 0; index < TEST_SHAPE.getNumberOfBits(); index++) {
            filter.merge(IndexExtractor.fromIndexArray(index));
            assertFalse(filter.isEmpty(), "Filter should be non-empty after merging index " + index);
            assertEquals(index + 1, filter.cardinality(), "Wrong cardinality after merging index " + index);
        }
    }

    @Test
    void testCardinalityAndIsEmpty() {
        final BloomFilter filter = new SparseBloomFilter(TEST_SHAPE);

        // A newly created filter is empty.
        assertTrue(filter.isEmpty());
        assertEquals(0, filter.cardinality());

        // Populating it one index at a time grows the cardinality.
        mergeEachIndexAndAssertGrowingCardinality(filter);

        // Clearing resets the filter back to empty.
        filter.clear();
        assertEquals(0, filter.cardinality());
        assertTrue(filter.isEmpty());

        // Populating again behaves identically.
        mergeEachIndexAndAssertGrowingCardinality(filter);
    }
}
