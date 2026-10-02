package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringStringInt {

    private static void assertResults(LevenshteinResults result, int distance, int inserts, int deletes, int substitutes) {
        assertEquals(distance, result.getDistance());
        assertEquals(inserts, result.getInsertCount());
        assertEquals(deletes, result.getDeleteCount());
        assertEquals(substitutes, result.getSubstituteCount());
    }

    @Test
    void emptyStringsWithZeroThreshold_returnsZeroDistance() {
        LevenshteinResults result = new LevenshteinDetailedDistance(0).apply("", "");
        assertResults(result, 0, 0, 0, 0);
    }

    @Test
    void identicalStrings_returnsZeroDistance() {
        assertResults(new LevenshteinDetailedDistance(0).apply("aa", "aa"), 0, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(2).apply("aa", "aa"), 0, 0, 0, 0);
    }

    @Test
    void thresholdExceededByDistance_returnsNegativeOne() {
        assertResults(new LevenshteinDetailedDistance(6).apply("aaapppp", ""),   -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(0).apply("b", "a"),        -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(0).apply("a", "b"),        -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(2).apply("aaa", "bbb"),    -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(2).apply("a", "bbb"),      -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(2).apply("bbb", "a"),      -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(6).apply("aaapppp", "b"),  -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(1).apply("a", "bbb"),      -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(1).apply("bbb", "a"),      -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(1).apply("12345", "1234567"),   -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(1).apply("1234567", "12345"),   -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(6).apply("elephant", "hippo"),  -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(6).apply("hippo", "elephant"),  -1, 0, 0, 0);
    }

    @Test
    void deletionsOnly_withSufficientThreshold() {
        assertResults(new LevenshteinDetailedDistance(8).apply("aaapppp", ""), 7, 0, 7, 0);
        assertResults(new LevenshteinDetailedDistance(7).apply("aaapppp", ""), 7, 0, 7, 0);
        assertResults(new LevenshteinDetailedDistance(1).apply("frog", "fog"),  1, 0, 1, 0);
    }

    @Test
    void substitutionsOnly_withSufficientThreshold() {
        assertResults(new LevenshteinDetailedDistance(3).apply("aaa", "bbb"),   3, 0, 0, 3);
        assertResults(new LevenshteinDetailedDistance(3).apply("fly", "ant"),   3, 0, 0, 3);
        assertResults(new LevenshteinDetailedDistance(1).apply("hello", "hallo"), 1, 0, 0, 1);
    }

    @Test
    void mixedOperations_withSufficientThreshold() {
        assertResults(new LevenshteinDetailedDistance(10).apply("aaaaaa", "b"),       6, 0, 5, 1);
        assertResults(new LevenshteinDetailedDistance(8).apply("aaapppp", "b"),       7, 0, 6, 1);
        assertResults(new LevenshteinDetailedDistance(7).apply("aaapppp", "b"),       7, 0, 6, 1);
        assertResults(new LevenshteinDetailedDistance(4).apply("a", "bbb"),           3, 2, 0, 1);
        assertResults(new LevenshteinDetailedDistance(3).apply("a", "bbb"),           3, 2, 0, 1);
        assertResults(new LevenshteinDetailedDistance(7).apply("elephant", "hippo"),  7, 0, 3, 4);
        assertResults(new LevenshteinDetailedDistance(7).apply("hippo", "elephant"),  7, 3, 0, 4);
        assertResults(new LevenshteinDetailedDistance(7).apply("hippo", "elephant"),  7, 3, 0, 4);
        assertResults(new LevenshteinDetailedDistance(8).apply("hippo", "zzzzzzzz"),  8, 3, 0, 5);
        assertResults(new LevenshteinDetailedDistance(8).apply("zzzzzzzz", "hippo"),  8, 0, 3, 5);
    }

    @Test
    void unlimitedThreshold_producesCorrectDetailedResults() {
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("frog", "fog"),       1, 0, 1, 0);
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("fly", "ant"),        3, 0, 0, 3);
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("elephant", "hippo"), 7, 0, 3, 4);
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("hippo", "elephant"), 7, 3, 0, 4);
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("hippo", "zzzzzzzz"), 8, 3, 0, 5);
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("zzzzzzzz", "hippo"), 8, 0, 3, 5);
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("hello", "hallo"),    1, 0, 0, 1);
    }
}
