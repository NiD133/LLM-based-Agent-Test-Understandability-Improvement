package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link SegmentConstantPoolArrayCache#indexesForArrayKey} correctly
 * finds all positions of a key that appears more than once in the same array.
 */
public class SegmentConstantPoolArrayCacheTest_testSingleMultipleHitArray {

    /** The key that appears at multiple positions in the test array. */
    private static final String REPEATED_KEY = "OneThreeFour";

    /** Expected number of times {@code REPEATED_KEY} appears in the array. */
    private static final int EXPECTED_HIT_COUNT = 3;

    /** First index (0-based) at which {@code REPEATED_KEY} appears. */
    private static final int FIRST_HIT_INDEX = 1;

    /** Second index at which {@code REPEATED_KEY} appears. */
    private static final int SECOND_HIT_INDEX = 3;

    /** Third index at which {@code REPEATED_KEY} appears. */
    private static final int THIRD_HIT_INDEX = 4;

    /**
     * Verifies that when a key appears multiple times in an array,
     * {@code indexesForArrayKey} returns every matching index in ascending order.
     *
     * <p>Array layout:
     * <pre>
     *  index 0 : "Zero"         – not a match
     *  index 1 : "OneThreeFour" – match
     *  index 2 : "Two"          – not a match
     *  index 3 : "OneThreeFour" – match
     *  index 4 : "OneThreeFour" – match
     * </pre>
     */
    @Test
    void testSingleMultipleHitArray() {
        // Arrange
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();
        final String[] array = { "Zero", "OneThreeFour", "Two", "OneThreeFour", "OneThreeFour" };

        // Act
        final List<Integer> matchingIndexes = arrayCache.indexesForArrayKey(array, REPEATED_KEY);

        // Assert – result contains exactly the three positions of REPEATED_KEY
        assertEquals(EXPECTED_HIT_COUNT, matchingIndexes.size(),
                "Number of matches for '" + REPEATED_KEY + "' should be " + EXPECTED_HIT_COUNT);

        assertAll("Matching indexes should be returned in ascending order",
                () -> assertEquals(FIRST_HIT_INDEX,  matchingIndexes.get(0).intValue(),
                        "First match should be at index " + FIRST_HIT_INDEX),
                () -> assertEquals(SECOND_HIT_INDEX, matchingIndexes.get(1).intValue(),
                        "Second match should be at index " + SECOND_HIT_INDEX),
                () -> assertEquals(THIRD_HIT_INDEX,  matchingIndexes.get(2).intValue(),
                        "Third match should be at index " + THIRD_HIT_INDEX)
        );
    }
}
