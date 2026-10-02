package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SegmentConstantPoolArrayCacheTest_testSingleSimpleArray {

    /**
     * Verifies that looking up a key that appears exactly once in a simple array
     * returns a single-element list containing the correct index of that element.
     *
     * Array layout:  index 0="Zero", 1="One", 2="Two", 3="Three", 4="Four"
     * Querying "Three" should yield exactly one hit at index 3.
     */
    @Test
    void testSingleSimpleArray() {
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();

        // An array where every element is distinct, so any lookup returns at most one index.
        final String[] array = { "Zero", "One", "Two", "Three", "Four" };

        final String searchKey = "Three";
        final int expectedIndex = 3;

        final List<Integer> matchingIndexes = arrayCache.indexesForArrayKey(array, searchKey);

        assertEquals(1, matchingIndexes.size(),
                "A key that appears exactly once should produce a list with one entry");
        assertEquals(expectedIndex, matchingIndexes.get(0).intValue(),
                "The returned index should point to the position of \"" + searchKey + "\" in the array");
    }
}
