package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SegmentConstantPoolArrayCacheTest_testMultipleArrayMultipleHit {

    /**
     * Verifies that the cache correctly handles multiple arrays, each containing
     * the same key at multiple positions. The test exercises three scenarios:
     *   1. The cache is populated on the first lookup for each array.
     *   2. Subsequent lookups for a different key in an already-cached array
     *      return the right indices from the cached data.
     *   3. Lookups for a key that does not exist return an empty list.
     *
     * arrayOne layout: index -> value
     *   0 -> "Zero"   1 -> "Shared"   2 -> "Two"   3 -> "Shared"   4 -> "Shared"
     *
     * arrayTwo layout: index -> value
     *   0 -> "Shared"   1 -> "One"   2 -> "Shared"   3 -> "Shared"   4 -> "Shared"
     */
    @Test
    void testMultipleArrayMultipleHit() {
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();

        final String[] arrayOne = { "Zero", "Shared", "Two", "Shared", "Shared" };
        final String[] arrayTwo = { "Shared", "One", "Shared", "Shared", "Shared" };

        // --- Phase 1: Populate the cache by performing the first lookup for each array ---
        // The first call for each array triggers cache construction for that array.
        arrayCache.indexesForArrayKey(arrayOne, "Shared");
        arrayCache.indexesForArrayKey(arrayTwo, "Shared");

        // --- Phase 2: Look up different keys using the already-populated cache ---
        // Look up "Two" in arrayOne (appears only at index 2).
        List<Integer> indicesOfTwoInArrayOne = arrayCache.indexesForArrayKey(arrayOne, "Two");

        // Look up "Shared" in arrayTwo while the cache for arrayTwo is warm
        // (appears at indices 0, 2, 3, 4).
        List<Integer> indicesOfSharedInArrayTwo = arrayCache.indexesForArrayKey(arrayTwo, "Shared");

        // Verify "Two" is found only at index 2 in arrayOne.
        assertEquals(1, indicesOfTwoInArrayOne.size());
        assertEquals(2, indicesOfTwoInArrayOne.get(0).intValue());

        // --- Phase 3: Look up "Shared" in arrayOne (appears at indices 1, 3, 4) ---
        // This exercises a key-switch on an already-cached array.
        List<Integer> indicesOfSharedInArrayOne = arrayCache.indexesForArrayKey(arrayOne, "Shared");

        assertEquals(3, indicesOfSharedInArrayOne.size());
        assertEquals(1, indicesOfSharedInArrayOne.get(0).intValue());
        assertEquals(3, indicesOfSharedInArrayOne.get(1).intValue());
        assertEquals(4, indicesOfSharedInArrayOne.get(2).intValue());

        // Verify "Shared" indices for arrayTwo are unchanged (indices 0, 2, 3, 4).
        assertEquals(4, indicesOfSharedInArrayTwo.size());
        assertEquals(0, indicesOfSharedInArrayTwo.get(0).intValue());
        assertEquals(2, indicesOfSharedInArrayTwo.get(1).intValue());
        assertEquals(3, indicesOfSharedInArrayTwo.get(2).intValue());
        assertEquals(4, indicesOfSharedInArrayTwo.get(3).intValue());

        // --- Phase 4: Look up a key that is absent in arrayOne ---
        // The cache should return an empty list rather than null.
        final List<Integer> indicesOfMissingKey = arrayCache.indexesForArrayKey(arrayOne, "Not found");
        assertEquals(0, indicesOfMissingKey.size());
    }
}
