package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testGetLevenshteinDistance {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    /** Convenience wrapper that builds SimilarityInput objects and applies the unlimited distance. */
    private static int distance(final Class<?> cls, final String left, final String right) {
        return UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, left),
                SimilarityInputTest.build(cls, right));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance(final Class<?> cls) {
        // Both strings empty — distance is zero
        assertEquals(0, distance(cls, "", ""));

        // One string empty — distance equals the length of the other
        assertEquals(1, distance(cls, "", "a"));
        assertEquals(7, distance(cls, "aaapppp", ""));

        // Single deletion
        assertEquals(1, distance(cls, "frog", "fog"));

        // All characters differ
        assertEquals(3, distance(cls, "fly", "ant"));

        // Distance is symmetric: elephant ↔ hippo
        assertEquals(7, distance(cls, "elephant", "hippo"));
        assertEquals(7, distance(cls, "hippo", "elephant"));

        // Distance is symmetric: hippo ↔ zzzzzzzz
        assertEquals(8, distance(cls, "hippo", "zzzzzzzz"));
        assertEquals(8, distance(cls, "zzzzzzzz", "hippo"));

        // Single substitution
        assertEquals(1, distance(cls, "hello", "hallo"));
    }
}
