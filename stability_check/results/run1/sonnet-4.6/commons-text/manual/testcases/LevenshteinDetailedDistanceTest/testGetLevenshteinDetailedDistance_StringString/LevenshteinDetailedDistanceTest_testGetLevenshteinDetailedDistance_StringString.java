package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringString {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    /**
     * Asserts all four fields of a LevenshteinResults in one call, reducing
     * boilerplate and making the intent of each scenario immediately visible.
     */
    private static void assertResult(LevenshteinResults result,
                                     int expectedDistance,
                                     int expectedInserts,
                                     int expectedDeletes,
                                     int expectedSubstitutes) {
        assertEquals(expectedDistance,    result.getDistance(),       "distance");
        assertEquals(expectedInserts,     result.getInsertCount(),    "inserts");
        assertEquals(expectedDeletes,     result.getDeleteCount(),    "deletes");
        assertEquals(expectedSubstitutes, result.getSubstituteCount(), "substitutes");
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDetailedDistance_StringString(final Class<?> cls) {

        // Both strings empty — no operations needed
        assertResult(
            UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, ""),
                SimilarityInputTest.build(cls, "")),
            0, 0, 0, 0);

        // Empty left, single-char right — one insertion
        assertResult(
            UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, ""),
                SimilarityInputTest.build(cls, "a")),
            1, 1, 0, 0);

        // Non-empty left, empty right — all deletions
        assertResult(
            UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, "aaapppp"),
                SimilarityInputTest.build(cls, "")),
            7, 0, 7, 0);

        // One character deleted from the middle ("frog" → "fog")
        assertResult(
            UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, "frog"),
                SimilarityInputTest.build(cls, "fog")),
            1, 0, 1, 0);

        // All characters substituted ("fly" → "ant")
        assertResult(
            UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, "fly"),
                SimilarityInputTest.build(cls, "ant")),
            3, 0, 0, 3);

        // Mixed deletes and substitutes ("elephant" → "hippo")
        assertResult(
            UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, "elephant"),
                SimilarityInputTest.build(cls, "hippo")),
            7, 0, 3, 4);

        // Mixed inserts and substitutes — reverse of previous ("hippo" → "elephant")
        assertResult(
            UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, "hippo"),
                SimilarityInputTest.build(cls, "elephant")),
            7, 3, 0, 4);

        // Shorter string expanded with inserts and substitutes ("hippo" → "zzzzzzzz")
        assertResult(
            UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, "hippo"),
                SimilarityInputTest.build(cls, "zzzzzzzz")),
            8, 3, 0, 5);

        // Longer string shrunk with deletes and substitutes ("zzzzzzzz" → "hippo")
        assertResult(
            UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, "zzzzzzzz"),
                SimilarityInputTest.build(cls, "hippo")),
            8, 0, 3, 5);

        // Single vowel substitution ("hello" → "hallo")
        assertResult(
            UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, "hello"),
                SimilarityInputTest.build(cls, "hallo")),
            1, 0, 0, 1);
    }
}
