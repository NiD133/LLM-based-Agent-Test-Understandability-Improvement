package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SegmentConstantPoolArrayCache#indexesForArrayKey(String[], String)}
 * reports the correct index for a key that appears exactly once in a simple,
 * duplicate-free array.
 */
public class SegmentConstantPoolArrayCacheTest_testSingleSimpleArray {

    @Test
    void testSingleSimpleArray() {
        // Given: a cache and an array whose elements are all distinct.
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();
        final String[] words = { "Zero", "One", "Two", "Three", "Four" };

        // When: looking up the only key that equals "Three".
        final List<Integer> matchingIndexes = arrayCache.indexesForArrayKey(words, "Three");

        // Then: exactly one index is returned, pointing at position 3 in the array.
        assertEquals(1, matchingIndexes.size(), "\"Three\" occurs once, so one index is expected");
        assertEquals(3, matchingIndexes.get(0).intValue(), "\"Three\" is stored at index 3");
    }
}
