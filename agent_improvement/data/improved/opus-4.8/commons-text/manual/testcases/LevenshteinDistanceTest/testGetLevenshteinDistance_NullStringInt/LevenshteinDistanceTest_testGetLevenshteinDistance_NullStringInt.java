package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link LevenshteinDistance#apply(SimilarityInput, SimilarityInput)} rejects a
 * {@code null} input.
 *
 * <p>The check is repeated for every {@link SimilarityInput} implementation supplied by
 * {@link SimilarityInputTest#similarityInputs()}, ensuring the null guard holds regardless of the
 * concrete input type.</p>
 */
public class LevenshteinDistanceTest_testGetLevenshteinDistance_NullStringInt {

    /** Default instance: computes the unbounded Levenshtein distance (no threshold). */
    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    /**
     * Applying the distance with a {@code null} left input (and a non-null right input) must raise
     * {@link IllegalArgumentException}, for each kind of {@link SimilarityInput}.
     *
     * @param inputType the concrete {@link SimilarityInput} implementation under test.
     */
    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void applyWithNullLeftInputThrowsIllegalArgumentException(final Class<?> inputType) {
        @SuppressWarnings("rawtypes")
        final SimilarityInput nullLeftInput = SimilarityInputTest.build(inputType, null);
        @SuppressWarnings("rawtypes")
        final SimilarityInput nonNullRightInput = SimilarityInputTest.build(inputType, "a");

        assertThrows(IllegalArgumentException.class,
                () -> UNLIMITED_DISTANCE.apply(nullLeftInput, nonNullRightInput));
    }
}
