package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link LevenshteinDistance#apply(SimilarityInput, SimilarityInput)} rejects a
 * {@code null} input.
 *
 * <p>The default {@link LevenshteinDistance} instance uses the unlimited algorithm (no threshold).
 * Per its contract, computing the distance against a {@code null} input must fail fast with an
 * {@link IllegalArgumentException}. The test repeats this check for every supported
 * {@link SimilarityInput} implementation type supplied by
 * {@link SimilarityInputTest#similarityInputs()}.</p>
 */
public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringNullInt {

    /** Default instance: no threshold, so the unlimited Levenshtein algorithm is used. */
    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void applyThrowsWhenSecondInputIsNull(final Class<?> similarityInputType) {
        // A valid, non-null "left" input paired with a null "right" input.
        @SuppressWarnings("rawtypes")
        final SimilarityInput nonNullInput = SimilarityInputTest.build(similarityInputType, "a");
        @SuppressWarnings("rawtypes")
        final SimilarityInput nullInput = SimilarityInputTest.build(similarityInputType, null);

        assertThrows(IllegalArgumentException.class,
                () -> UNLIMITED_DISTANCE.apply(nonNullInput, nullInput));
    }
}
