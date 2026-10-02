package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringString {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    /**
     * Asserts all four fields of a LevenshteinResults at once, making each scenario a single readable call.
     */
    private void assertResults(LevenshteinResults result, int expectedDistance,
            int expectedInserts, int expectedDeletes, int expectedSubstitutes) {
        assertEquals(expectedDistance,    result.getDistance());
        assertEquals(expectedInserts,     result.getInsertCount());
        assertEquals(expectedDeletes,     result.getDeleteCount());
        assertEquals(expectedSubstitutes, result.getSubstituteCount());
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDetailedDistance_StringString(final Class<?> cls) {

        // Both strings empty — no edits needed
        assertResults(
            UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, "")),
            0, 0, 0, 0);

        // Empty left, single-char right — one insertion
        assertResults(
            UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, "a")),
            1, 1, 0, 0);

        // Non-empty left, empty right — all deletions
        assertResults(
            UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "")),
            7, 0, 7, 0);

        // One deletion transforms "frog" → "fog"
        assertResults(
            UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "frog"), SimilarityInputTest.build(cls, "fog")),
            1, 0, 1, 0);

        // All characters differ: "fly" → "ant" requires three substitutions
        assertResults(
            UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "fly"), SimilarityInputTest.build(cls, "ant")),
            3, 0, 0, 3);

        // Longer → shorter: "elephant" → "hippo" (4 substitutes + 3 deletes)
        assertResults(
            UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo")),
            7, 0, 3, 4);

        // Shorter → longer: "hippo" → "elephant" (4 substitutes + 3 inserts)
        assertResults(
            UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "elephant")),
            7, 3, 0, 4);

        // Shorter → much longer: "hippo" → "zzzzzzzz" (5 substitutes + 3 inserts)
        assertResults(
            UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "zzzzzzzz")),
            8, 3, 0, 5);

        // Much longer → shorter: "zzzzzzzz" → "hippo" (5 substitutes + 3 deletes)
        assertResults(
            UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo")),
            8, 0, 3, 5);

        // One vowel differs: "hello" → "hallo" (single substitution)
        assertResults(
            UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hello"), SimilarityInputTest.build(cls, "hallo")),
            1, 0, 0, 1);
    }
}
