package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringString {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    /** Convenience wrapper to reduce per-assertion boilerplate. */
    private int distance(final Class<?> cls, final String left, final String right) {
        return UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, left),
                SimilarityInputTest.build(cls, right));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringString(final Class<?> cls) {
        // Both inputs empty — no edits needed
        assertEquals(0, distance(cls, "", ""));

        // One side empty — distance equals the length of the non-empty side
        assertEquals(1, distance(cls, "", "a"));
        assertEquals(7, distance(cls, "aaapppp", ""));

        // Single deletion
        assertEquals(1, distance(cls, "frog", "fog"));

        // Completely different short words — all characters must be replaced
        assertEquals(3, distance(cls, "fly", "ant"));

        // Longer words with large edit distance, symmetric
        assertEquals(7, distance(cls, "elephant", "hippo"));
        assertEquals(7, distance(cls, "hippo", "elephant"));

        // One word much shorter — many insertions/deletions required, symmetric
        assertEquals(8, distance(cls, "hippo", "zzzzzzzz"));
        assertEquals(8, distance(cls, "zzzzzzzz", "hippo"));

        // Single substitution ("e" -> "a")
        assertEquals(1, distance(cls, "hello", "hallo"));
    }
}
