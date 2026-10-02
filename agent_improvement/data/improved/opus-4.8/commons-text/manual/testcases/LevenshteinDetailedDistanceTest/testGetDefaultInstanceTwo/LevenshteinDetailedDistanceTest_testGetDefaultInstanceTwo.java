package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that the default (unlimited-threshold) {@link LevenshteinDetailedDistance}
 * instance produces consistent results regardless of how the inputs are supplied.
 *
 * <p>The two text fragments below differ enough to require 20 edit operations to
 * transform one into the other. The test confirms that:</p>
 * <ol>
 *   <li>applying the distance to the two raw {@link String}s yields a distance of 20, and</li>
 *   <li>wrapping the same text in every supported {@code SimilarityInput} type produces an
 *       identical {@link LevenshteinResults} object.</li>
 * </ol>
 */
public class LevenshteinDetailedDistanceTest_testGetDefaultInstanceTwo {

    /** First text fragment to compare. */
    private static final String LEFT_TEXT = "Distance: 2147483647, Insert: 0, Delete: 0, Substitute: 0";

    /** Second text fragment to compare; differs from {@link #LEFT_TEXT}. */
    private static final String RIGHT_TEXT = "Distance: 0, Insert: 2147483647, Delete: 0, Substitute: 0";

    /** Expected Levenshtein edit distance between {@link #LEFT_TEXT} and {@link #RIGHT_TEXT}. */
    private static final int EXPECTED_DISTANCE = 20;

    /**
     * @param inputType the concrete {@code SimilarityInput} implementation class to wrap the
     *                  text with, supplied by {@link SimilarityInputTest#similarityInputs()}.
     */
    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetDefaultInstanceTwo(final Class<?> inputType) {
        final LevenshteinDetailedDistance distance = LevenshteinDetailedDistance.getDefaultInstance();

        // Baseline: compute the detailed distance directly from the two Strings.
        final LevenshteinResults resultFromStrings = distance.apply(LEFT_TEXT, RIGHT_TEXT);
        assertEquals(EXPECTED_DISTANCE, resultFromStrings.getDistance());

        // The same text wrapped in the given SimilarityInput type must give an equal result.
        final LevenshteinResults resultFromSimilarityInputs = distance.apply(
                SimilarityInputTest.build(inputType, LEFT_TEXT),
                SimilarityInputTest.build(inputType, RIGHT_TEXT));
        assertEquals(resultFromStrings, resultFromSimilarityInputs);
    }
}
