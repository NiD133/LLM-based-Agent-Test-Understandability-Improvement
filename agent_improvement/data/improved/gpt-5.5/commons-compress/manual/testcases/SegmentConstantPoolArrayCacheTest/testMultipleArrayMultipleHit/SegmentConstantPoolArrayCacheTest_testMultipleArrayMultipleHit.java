package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class SegmentConstantPoolArrayCacheTest_testMultipleArrayMultipleHit {

    private static final String SHARED_KEY = "Shared";

    @Test
    void testMultipleArrayMultipleHit() {
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();
        final String[] arrayOne = { "Zero", SHARED_KEY, "Two", SHARED_KEY, SHARED_KEY };
        final String[] arrayTwo = { SHARED_KEY, "One", SHARED_KEY, SHARED_KEY, SHARED_KEY };

        List<Integer> arrayOneIndexes = arrayCache.indexesForArrayKey(arrayOne, SHARED_KEY);
        List<Integer> arrayTwoIndexes = arrayCache.indexesForArrayKey(arrayTwo, SHARED_KEY);

        arrayOneIndexes = arrayCache.indexesForArrayKey(arrayOne, "Two");
        arrayTwoIndexes = arrayCache.indexesForArrayKey(arrayTwo, SHARED_KEY);
        assertIndexes(arrayOneIndexes, 2);

        arrayOneIndexes = arrayCache.indexesForArrayKey(arrayOne, SHARED_KEY);
        assertIndexes(arrayOneIndexes, 1, 3, 4);
        assertIndexes(arrayTwoIndexes, 0, 2, 3, 4);

        final List<Integer> missingKeyIndexes = arrayCache.indexesForArrayKey(arrayOne, "Not found");
        assertEquals(0, missingKeyIndexes.size());
    }

    private static void assertIndexes(final List<Integer> indexes, final int... expectedIndexes) {
        assertEquals(expectedIndexes.length, indexes.size());
        for (int i = 0; i < expectedIndexes.length; i++) {
            assertEquals(expectedIndexes[i], indexes.get(i).intValue());
        }
    }
}
