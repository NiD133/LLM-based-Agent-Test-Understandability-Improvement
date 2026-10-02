package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class SegmentConstantPoolArrayCacheTest_testSingleSimpleArray {

    private static final String LOOKUP_VALUE = "Three";
    private static final int EXPECTED_MATCH_COUNT = 1;
    private static final int EXPECTED_MATCH_INDEX = 3;

    @Test
    void testSingleSimpleArray() {
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();
        final String[] constantPoolValues = { "Zero", "One", "Two", LOOKUP_VALUE, "Four" };

        final List<Integer> matchingIndexes = arrayCache.indexesForArrayKey(constantPoolValues, LOOKUP_VALUE);

        assertEquals(EXPECTED_MATCH_COUNT, matchingIndexes.size());
        assertEquals(EXPECTED_MATCH_INDEX, matchingIndexes.get(0).intValue());
    }
}
