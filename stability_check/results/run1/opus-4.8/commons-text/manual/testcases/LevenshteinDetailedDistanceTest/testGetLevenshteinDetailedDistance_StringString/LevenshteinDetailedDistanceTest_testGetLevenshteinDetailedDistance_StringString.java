package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests the unlimited (threshold-free) Levenshtein distance and its detailed
 * breakdown into insert, delete and substitute operation counts.
 *
 * <p>The same set of word pairs is exercised against every {@link SimilarityInput}
 * implementation supplied by {@code SimilarityInputTest#similarityInputs()}, which
 * verifies that the result is independent of the concrete input type.</p>
 */
public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringString {

    /** The default instance performs an unlimited (no threshold) comparison. */
    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    /** The SimilarityInput implementation under test for the current invocation. */
    private Class<?> inputType;

    /**
     * Computes the detailed distance between {@code left} and {@code right} (wrapped in
     * the current {@link #inputType}) and asserts the total distance together with the
     * expected number of insert, delete and substitute operations.
     */
    private void assertDetailedDistance(final String left, final String right,
            final int expectedDistance, final int expectedInsertCount,
            final int expectedDeleteCount, final int expectedSubstituteCount) {
        final LevenshteinResults result = UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(inputType, left),
                SimilarityInputTest.build(inputType, right));
        assertEquals(expectedDistance, result.getDistance(), "distance");
        assertEquals(expectedInsertCount, result.getInsertCount(), "insert count");
        assertEquals(expectedDeleteCount, result.getDeleteCount(), "delete count");
        assertEquals(expectedSubstituteCount, result.getSubstituteCount(), "substitute count");
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDetailedDistance_StringString(final Class<?> cls) {
        inputType = cls;

        //                        left,        right,        distance, insert, delete, substitute
        assertDetailedDistance("",          "",                  0,      0,      0,      0);
        assertDetailedDistance("",          "a",                 1,      1,      0,      0);
        assertDetailedDistance("aaapppp",   "",                  7,      0,      7,      0);
        assertDetailedDistance("frog",      "fog",               1,      0,      1,      0);
        assertDetailedDistance("fly",       "ant",               3,      0,      0,      3);
        assertDetailedDistance("elephant",  "hippo",             7,      0,      3,      4);
        assertDetailedDistance("hippo",     "elephant",          7,      3,      0,      4);
        assertDetailedDistance("hippo",     "zzzzzzzz",          8,      3,      0,      5);
        assertDetailedDistance("zzzzzzzz",  "hippo",             8,      0,      3,      5);
        assertDetailedDistance("hello",     "hallo",             1,      0,      0,      1);
    }
}
