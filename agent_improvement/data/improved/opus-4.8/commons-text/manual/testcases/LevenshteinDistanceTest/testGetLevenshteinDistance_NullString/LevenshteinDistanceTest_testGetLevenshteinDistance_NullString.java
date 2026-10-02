package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link LevenshteinDistance#apply(SimilarityInput, SimilarityInput)} rejects a
 * {@code null} input by throwing an {@link IllegalArgumentException}.
 *
 * <p>The check is run once per supported {@link SimilarityInput} implementation type, which are
 * supplied by {@link SimilarityInputTest#similarityInputs()}.</p>
 */
public class LevenshteinDistanceTest_testGetLevenshteinDistance_NullString {

    /** Default instance: computes the unbounded Levenshtein distance (no threshold). */
    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void applyThrowsWhenRightInputIsNull(final Class<?> similarityInputType) {
        // The left input is the valid sequence "a"; the right input wraps a null value.
        // Both inputs are built lazily inside the executable so they share one captured
        // element type E, which apply(SimilarityInput<E>, SimilarityInput<E>) requires.
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(similarityInputType, "a"),
                SimilarityInputTest.build(similarityInputType, null)));
    }
}
