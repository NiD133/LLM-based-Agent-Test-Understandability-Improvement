package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SegmentConstantPoolArrayCache} returns the correct index
 * lists when the same cache is queried repeatedly for several keys across two
 * different arrays. The first lookup of any (array, key) pair builds the cache;
 * subsequent lookups are expected to return the cached results.
 */
public class SegmentConstantPoolArrayCacheTest_testMultipleArrayMultipleHit {

    @Test
    void testMultipleArrayMultipleHit() {
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();

        // Index:                            0        1         2        3         4
        final String[] arrayOne = { "Zero", "Shared", "Two", "Shared", "Shared" };
        final String[] arrayTwo = { "Shared", "One", "Shared", "Shared", "Shared" };

        // Prime the cache for both arrays with the key "Shared".
        arrayCache.indexesForArrayKey(arrayOne, "Shared");
        List<Integer> listTwo = arrayCache.indexesForArrayKey(arrayTwo, "Shared");

        // Look up "Two" in arrayOne: it occurs only at index 2.
        List<Integer> listOne = arrayCache.indexesForArrayKey(arrayOne, "Two");
        // Re-query arrayTwo for "Shared" (served from the cache).
        listTwo = arrayCache.indexesForArrayKey(arrayTwo, "Shared");

        assertEquals(1, listOne.size());
        assertEquals(2, listOne.get(0).intValue());

        // Look up "Shared" in arrayOne: it occurs at indexes 1, 3 and 4.
        listOne = arrayCache.indexesForArrayKey(arrayOne, "Shared");
        assertEquals(3, listOne.size());
        assertEquals(1, listOne.get(0).intValue());
        assertEquals(3, listOne.get(1).intValue());
        assertEquals(4, listOne.get(2).intValue());

        // "Shared" in arrayTwo occurs at indexes 0, 2, 3 and 4.
        assertEquals(4, listTwo.size());
        assertEquals(0, listTwo.get(0).intValue());
        assertEquals(2, listTwo.get(1).intValue());
        assertEquals(3, listTwo.get(2).intValue());
        assertEquals(4, listTwo.get(3).intValue());

        // A key that is absent from arrayOne yields an empty list.
        final List<Integer> listThree = arrayCache.indexesForArrayKey(arrayOne, "Not found");
        assertEquals(0, listThree.size());
    }
}
