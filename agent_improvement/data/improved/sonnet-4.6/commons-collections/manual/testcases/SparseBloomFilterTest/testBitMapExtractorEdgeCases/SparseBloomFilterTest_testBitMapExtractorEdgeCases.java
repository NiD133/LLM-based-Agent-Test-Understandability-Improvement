package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SparseBloomFilterTest_testBitMapExtractorEdgeCases {

    // Shape with k=17 hash functions and m=72 bits → requires 2 bitmaps (each 64 bits).
    private static final Shape TEST_SHAPE = Shape.fromKM(17, 72);

    private SparseBloomFilter createFilter(final int... indices) {
        final SparseBloomFilter filter = new SparseBloomFilter(TEST_SHAPE);
        filter.merge(IndexExtractor.fromIndexArray(indices));
        return filter;
    }

    @Test
    void testBitMapExtractorEdgeCases() {
        // Indices 1-9 land in bitmap[0] (bits 0-63); indices 65-71 land in bitmap[1] (bits 64-127).
        // This filter spans both bitmaps of the 72-bit shape.
        final int[] twoMapValues = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 65, 66, 67, 68, 69, 70, 71 };

        // Scenario 1: predicate returns false immediately on the first bitmap call.
        // processBitMaps must stop and return false after exactly 1 invocation.
        BloomFilter bf = createFilter(twoMapValues);
        final int[] callCount = new int[1];
        assertFalse(bf.processBitMaps(l -> {
            callCount[0]++;
            return false; // stop on the very first bitmap
        }));
        assertEquals(1, callCount[0], "Should stop after the first bitmap when predicate returns false immediately");

        // Scenario 2: predicate accepts bitmap[0] but rejects bitmap[1] (the boundary).
        // processBitMaps must stop at that boundary and return false.
        bf = createFilter(twoMapValues);
        callCount[0] = 0;
        assertFalse(bf.processBitMaps(l -> {
            final boolean continueProcessing = callCount[0] == 0;
            if (continueProcessing) {
                callCount[0]++;
            }
            return continueProcessing; // false on the second call (bitmap[1])
        }));
        assertEquals(1, callCount[0], "Should stop at the bitmap boundary when predicate rejects bitmap[1]");

        // Scenario 3: all set bits (1-4) fit inside bitmap[0] (< 64).
        // Even though no bits are set in bitmap[1], the shape requires 2 bitmaps,
        // so processBitMaps must still emit a trailing zero bitmap for bitmap[1].
        final int[] firstMapOnlyValues = { 1, 2, 3, 4 };
        bf = createFilter(firstMapOnlyValues);
        callCount[0] = 0;
        assertTrue(bf.processBitMaps(l -> {
            callCount[0]++;
            return true; // always continue
        }));
        assertEquals(2, callCount[0], "Should emit bitmap[0] (with data) then a trailing zero bitmap[1]");

        // Scenario 4: same single-map filter, but the predicate rejects the trailing zero bitmap.
        // processBitMaps must return false after the predicate rejects bitmap[1].
        bf = createFilter(firstMapOnlyValues);
        callCount[0] = 0;
        assertFalse(bf.processBitMaps(l -> {
            final boolean continueProcessing = callCount[0] == 0;
            if (continueProcessing) {
                callCount[0]++;
            }
            return continueProcessing; // false on the second call (trailing zero bitmap)
        }));
        assertEquals(1, callCount[0], "Should stop when predicate rejects the trailing zero bitmap");
    }
}
