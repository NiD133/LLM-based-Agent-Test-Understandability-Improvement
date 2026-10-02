package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that the default (unlimited) {@link LevenshteinDetailedDistance}
 * instance computes the correct edit distance, regardless of which concrete
 * {@link SimilarityInput} implementation wraps the compared text.
 */
public class LevenshteinDetailedDistanceTest_testGetDefaultInstanceOne {

    /** The default instance uses the unlimited algorithm (no distance threshold). */
    private static final LevenshteinDetailedDistance DEFAULT_DISTANCE =
            LevenshteinDetailedDistance.getDefaultInstance();

    /** First text to compare. */
    private static final String LEFT_TEXT = "Distance: -2147483643, Insert: 0, Delete: 0, Substitute: 0";

    /** Second text to compare; it differs from {@link #LEFT_TEXT} by 21 single-character edits. */
    private static final String RIGHT_TEXT = "Distance: 0, Insert: 2147483536, Delete: 0, Substitute: 0";

    /** The expected Levenshtein distance between {@link #LEFT_TEXT} and {@link #RIGHT_TEXT}. */
    private static final int EXPECTED_DISTANCE = 21;

    /**
     * The default instance must report the same distance for every supported
     * {@link SimilarityInput} type, so the test runs once per type provided by
     * {@code SimilarityInputTest#similarityInputs()}.
     *
     * @param inputType the concrete type used to wrap the two texts.
     */
    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void distanceIsIndependentOfSimilarityInputType(final Class<?> inputType) {
        final LevenshteinResults results = DEFAULT_DISTANCE.apply(
                SimilarityInputTest.build(inputType, LEFT_TEXT),
                SimilarityInputTest.build(inputType, RIGHT_TEXT));

        assertEquals(EXPECTED_DISTANCE, results.getDistance());
    }
}
