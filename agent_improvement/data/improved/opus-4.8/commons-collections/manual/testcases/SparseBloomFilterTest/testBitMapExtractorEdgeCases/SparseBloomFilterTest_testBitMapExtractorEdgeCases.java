package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.LongPredicate;

import org.junit.jupiter.api.Test;

/**
 * Tests the edge cases of {@link SparseBloomFilter#processBitMaps(LongPredicate)}.
 *
 * <p>The test shape has 72 bits, which {@link SparseBloomFilter} stores across
 * two 64-bit "bit map" longs:</p>
 * <ul>
 *   <li>bit map 0 covers indices 0..63</li>
 *   <li>bit map 1 covers indices 64..71</li>
 * </ul>
 *
 * <p>{@code processBitMaps} feeds each bit map to the supplied predicate in turn and
 * stops as soon as the predicate returns {@code false}, returning {@code false} itself
 * in that case. The scenarios below exercise the early-exit behaviour around the
 * boundary between the two bit maps.</p>
 */
public class SparseBloomFilterTest_testBitMapExtractorEdgeCases {

    /** Hash functions (k) = 17, number of bits (m) = 72, giving two bit map longs. */
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    /** Indices spread across both bit maps: 1..9 in bit map 0, and 65..71 in bit map 1. */
    private static final int[] INDICES_IN_BOTH_BIT_MAPS =
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 65, 66, 67, 68, 69, 70, 71 };

    /** Indices that all fall inside the first bit map (0..63). */
    private static final int[] INDICES_IN_FIRST_BIT_MAP_ONLY = { 1, 2, 3, 4 };

    /**
     * Builds a {@link SparseBloomFilter} for the test shape populated with the given indices.
     */
    private SparseBloomFilter createFilterWithIndices(final int[] indices) {
        final SparseBloomFilter filter = new SparseBloomFilter(TEST_SHAPE);
        filter.merge(IndexExtractor.fromIndexArray(indices));
        return filter;
    }

    @Test
    void testBitMapExtractorEdgeCases() {
        // Counts how many bit maps the predicate was invoked with.
        final int[] invocationCount = new int[1];

        // Scenario 1: predicate rejects the very first bit map (before the bit map boundary).
        // processBitMaps must stop immediately and report failure after a single invocation.
        SparseBloomFilter filter = createFilterWithIndices(INDICES_IN_BOTH_BIT_MAPS);
        invocationCount[0] = 0;
        assertFalse(filter.processBitMaps(bitMap -> {
            invocationCount[0]++;
            return false;
        }));
        assertEquals(1, invocationCount[0]);

        // Scenario 2: predicate accepts the first bit map but rejects the second
        // (the rejection happens exactly at the bit map boundary).
        filter = createFilterWithIndices(INDICES_IN_BOTH_BIT_MAPS);
        invocationCount[0] = 0;
        assertFalse(filter.processBitMaps(bitMap -> {
            final boolean acceptFirstOnly = invocationCount[0] == 0;
            if (acceptFirstOnly) {
                invocationCount[0]++;
            }
            return acceptFirstOnly;
        }));
        assertEquals(1, invocationCount[0]);

        // Scenario 3: all indices live in the first bit map, yet the second (empty) bit map
        // is still generated. With an always-accepting predicate, both bit maps are visited
        // and processBitMaps reports success.
        filter = createFilterWithIndices(INDICES_IN_FIRST_BIT_MAP_ONLY);
        invocationCount[0] = 0;
        assertTrue(filter.processBitMaps(bitMap -> {
            invocationCount[0]++;
            return true;
        }));
        assertEquals(2, invocationCount[0]);

        // Scenario 4: all indices live in the first bit map, and the predicate rejects the
        // second (empty) bit map. processBitMaps must report failure after the first invocation.
        filter = createFilterWithIndices(INDICES_IN_FIRST_BIT_MAP_ONLY);
        invocationCount[0] = 0;
        assertFalse(filter.processBitMaps(bitMap -> {
            final boolean acceptFirstOnly = invocationCount[0] == 0;
            if (acceptFirstOnly) {
                invocationCount[0]++;
            }
            return acceptFirstOnly;
        }));
        assertEquals(1, invocationCount[0]);
    }
}
