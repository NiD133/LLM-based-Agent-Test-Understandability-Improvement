package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringStringInt {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    /**
     * Computes the threshold-limited Levenshtein distance between two strings.
     * Returns -1 if the actual distance exceeds the threshold.
     */
    private int limitedDistance(final Class<?> cls, final String left, final String right, final int threshold) {
        return new LevenshteinDistance(threshold).apply(
            SimilarityInputTest.build(cls, left),
            SimilarityInputTest.build(cls, right)
        );
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringStringInt(final Class<?> cls) {
        // empty strings
        assertEquals(0, limitedDistance(cls, "", "", 0));
        assertEquals(7, limitedDistance(cls, "aaapppp", "", 8));
        assertEquals(7, limitedDistance(cls, "aaapppp", "", 7));
        assertEquals(-1, limitedDistance(cls, "aaapppp", "", 6));

        // unequal strings, zero threshold
        assertEquals(-1, limitedDistance(cls, "b", "a", 0));
        assertEquals(-1, limitedDistance(cls, "a", "b", 0));

        // equal strings
        assertEquals(0, limitedDistance(cls, "aa", "aa", 0));
        assertEquals(0, limitedDistance(cls, "aa", "aa", 2));

        // same length strings
        assertEquals(-1, limitedDistance(cls, "aaa", "bbb", 2));
        assertEquals(3,  limitedDistance(cls, "aaa", "bbb", 3));

        // big stripe (threshold much larger than actual distance)
        assertEquals(6, limitedDistance(cls, "aaaaaa", "b", 10));

        // distance less than threshold
        assertEquals(7, limitedDistance(cls, "aaapppp", "b", 8));
        assertEquals(3, limitedDistance(cls, "a", "bbb", 4));

        // distance equal to threshold
        assertEquals(7, limitedDistance(cls, "aaapppp", "b", 7));
        assertEquals(3, limitedDistance(cls, "a", "bbb", 3));

        // distance greater than threshold
        assertEquals(-1, limitedDistance(cls, "a", "bbb", 2));
        assertEquals(-1, limitedDistance(cls, "bbb", "a", 2));
        assertEquals(-1, limitedDistance(cls, "aaapppp", "b", 6));

        // stripe runs off the DP matrix, strings not similar
        assertEquals(-1, limitedDistance(cls, "a", "bbb", 1));
        assertEquals(-1, limitedDistance(cls, "bbb", "a", 1));

        // stripe runs off the DP matrix, strings are similar (length difference exceeds threshold)
        assertEquals(-1, limitedDistance(cls, "12345", "1234567", 1));
        assertEquals(-1, limitedDistance(cls, "1234567", "12345", 1));

        // classic word-pair cases — threshold exactly meets the true distance
        assertEquals(1, limitedDistance(cls, "frog", "fog", 1));
        assertEquals(3, limitedDistance(cls, "fly", "ant", 3));
        assertEquals(7, limitedDistance(cls, "elephant", "hippo", 7));
        assertEquals(-1, limitedDistance(cls, "elephant", "hippo", 6));
        assertEquals(7, limitedDistance(cls, "hippo", "elephant", 7));
        assertEquals(-1, limitedDistance(cls, "hippo", "elephant", 6));
        assertEquals(8, limitedDistance(cls, "hippo", "zzzzzzzz", 8));
        assertEquals(8, limitedDistance(cls, "zzzzzzzz", "hippo", 8));
        assertEquals(1, limitedDistance(cls, "hello", "hallo", 1));

        // same classic cases with a very large threshold (effectively unlimited)
        assertEquals(1, limitedDistance(cls, "frog", "fog", Integer.MAX_VALUE));
        assertEquals(3, limitedDistance(cls, "fly", "ant", Integer.MAX_VALUE));
        assertEquals(7, limitedDistance(cls, "elephant", "hippo", Integer.MAX_VALUE));
        assertEquals(7, limitedDistance(cls, "hippo", "elephant", Integer.MAX_VALUE));
        assertEquals(8, limitedDistance(cls, "hippo", "zzzzzzzz", Integer.MAX_VALUE));
        assertEquals(8, limitedDistance(cls, "zzzzzzzz", "hippo", Integer.MAX_VALUE));
        assertEquals(1, limitedDistance(cls, "hello", "hallo", Integer.MAX_VALUE));

        // transposition ("abc" -> "acb") requires 2 edits, exceeds threshold of 1
        assertEquals(-1, limitedDistance(cls, "abc", "acb", 1));
    }
}
