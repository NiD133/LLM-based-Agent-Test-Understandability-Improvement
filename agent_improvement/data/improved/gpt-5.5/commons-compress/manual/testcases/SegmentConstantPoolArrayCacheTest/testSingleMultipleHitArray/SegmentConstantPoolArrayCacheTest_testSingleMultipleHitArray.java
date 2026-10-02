package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class SegmentConstantPoolArrayCacheTest_testSingleMultipleHitArray {

    @Test
    void testSingleMultipleHitArray() {
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();
        final String repeatedValue = "OneThreeFour";
        final String[] arrayWithThreeMatchingValues = { "Zero", repeatedValue, "Two", repeatedValue, repeatedValue };

        final List<Integer> matchingIndexes = arrayCache.indexesForArrayKey(arrayWithThreeMatchingValues, repeatedValue);

        assertEquals(3, matchingIndexes.size());
        assertEquals(1, matchingIndexes.get(0).intValue());
        assertEquals(3, matchingIndexes.get(1).intValue());
        assertEquals(4, matchingIndexes.get(2).intValue());
    }
}
