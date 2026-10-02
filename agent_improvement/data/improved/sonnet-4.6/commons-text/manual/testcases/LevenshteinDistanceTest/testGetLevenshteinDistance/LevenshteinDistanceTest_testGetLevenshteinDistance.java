package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    /** Convenience wrapper that builds inputs and applies the distance in one call. */
    private static int apply(final Class<?> cls, final String left, final String right) {
        return UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, left),
                SimilarityInputTest.build(cls, right));
    }

    @DisplayName("Levenshtein distance returns correct edit counts for various string pairs")
    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance(final Class<?> cls) {
        // identical empty strings require zero edits
        assertEquals(0, apply(cls, "", ""));

        // inserting one character into an empty string costs 1
        assertEquals(1, apply(cls, "", "a"));

        // deleting all 7 characters from "aaapppp" costs 7
        assertEquals(7, apply(cls, "aaapppp", ""));

        // "frog" → "fog": one deletion
        assertEquals(1, apply(cls, "frog", "fog"));

        // "fly" → "ant": three substitutions
        assertEquals(3, apply(cls, "fly", "ant"));

        // "elephant" → "hippo": 7 edits (asymmetric strings)
        assertEquals(7, apply(cls, "elephant", "hippo"));

        // distance is symmetric: swapping arguments yields the same result
        assertEquals(7, apply(cls, "hippo", "elephant"));

        // "hippo" → "zzzzzzzz": 8 edits (longer target)
        assertEquals(8, apply(cls, "hippo", "zzzzzzzz"));

        // symmetric counterpart of the previous case
        assertEquals(8, apply(cls, "zzzzzzzz", "hippo"));

        // "hello" → "hallo": one substitution (e → a)
        assertEquals(1, apply(cls, "hello", "hallo"));
    }
}
