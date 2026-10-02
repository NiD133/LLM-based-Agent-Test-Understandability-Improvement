package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests the default (unlimited, no-threshold) Levenshtein distance calculation.
 *
 * <p>The Levenshtein distance is the number of single-character edits (insertions,
 * deletions or substitutions) needed to turn one sequence into another. Each case is
 * exercised against every {@code SimilarityInput} implementation supplied by
 * {@link SimilarityInputTest#similarityInputs()} to ensure the result is independent
 * of the concrete input type.</p>
 */
public class LevenshteinDistanceTest_testGetLevenshteinDistance {

    /** The default instance computes the exact distance without any threshold limit. */
    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance(final Class<?> inputType) {
        // Two empty sequences are identical: no edits required.
        assertEquals(0, distanceBetween(inputType, "", ""));

        // Inserting a single character costs one edit.
        assertEquals(1, distanceBetween(inputType, "", "a"));

        // Deleting every character equals the length of the non-empty sequence.
        assertEquals(7, distanceBetween(inputType, "aaapppp", ""));

        // Deleting one character ("frog" -> "fog").
        assertEquals(1, distanceBetween(inputType, "frog", "fog"));

        // Completely different sequences of the same length: substitute every character.
        assertEquals(3, distanceBetween(inputType, "fly", "ant"));

        // The distance is symmetric, so swapping the arguments yields the same result.
        assertEquals(7, distanceBetween(inputType, "elephant", "hippo"));
        assertEquals(7, distanceBetween(inputType, "hippo", "elephant"));

        // Distance is symmetric here as well.
        assertEquals(8, distanceBetween(inputType, "hippo", "zzzzzzzz"));
        assertEquals(8, distanceBetween(inputType, "zzzzzzzz", "hippo"));

        // A single substitution ("hello" -> "hallo").
        assertEquals(1, distanceBetween(inputType, "hello", "hallo"));
    }

    /**
     * Computes the unlimited Levenshtein distance between two texts, wrapping each text in
     * a {@code SimilarityInput} of the given implementation type.
     *
     * @param inputType the concrete {@code SimilarityInput} implementation to wrap the texts in.
     * @param left      the first text.
     * @param right     the second text.
     * @return the Levenshtein distance between {@code left} and {@code right}.
     */
    private static int distanceBetween(final Class<?> inputType, final String left, final String right) {
        return UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(inputType, left),
                SimilarityInputTest.build(inputType, right));
    }
}
