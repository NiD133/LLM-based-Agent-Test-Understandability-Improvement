package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies that when a key occurs several times in an array,
 * {@link SegmentConstantPoolArrayCache#indexesForArrayKey} returns every
 * index at which that key appears, in ascending order.
 */
public class SegmentConstantPoolArrayCacheTest_testSingleMultipleHitArray {

    @Test
    void testSingleMultipleHitArray() {
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();

        // "OneThreeFour" appears at indexes 1, 3 and 4.
        final String[] array = { "Zero", "OneThreeFour", "Two", "OneThreeFour", "OneThreeFour" };

        final List<Integer> matchingIndexes = arrayCache.indexesForArrayKey(array, "OneThreeFour");

        assertEquals(3, matchingIndexes.size(), "expected three matches for the repeated key");
        assertEquals(1, matchingIndexes.get(0).intValue(), "first occurrence is at index 1");
        assertEquals(3, matchingIndexes.get(1).intValue(), "second occurrence is at index 3");
        assertEquals(4, matchingIndexes.get(2).intValue(), "third occurrence is at index 4");
    }
}
