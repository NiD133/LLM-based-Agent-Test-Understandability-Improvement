package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link LevenshteinDistance#apply(SimilarityInput, SimilarityInput)} rejects a
 * {@code null} input by throwing an {@link IllegalArgumentException}.
 *
 * <p>The test is parameterized over every {@link SimilarityInput} implementation supplied by
 * {@link SimilarityInputTest#similarityInputs()}, so the null-handling contract is checked for
 * each supported input type.</p>
 */
public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringNull {

    /** Default (unbounded) Levenshtein distance instance under test. */
    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringNull(final Class<?> inputType) {
        // Build a left operand backed by null and a right operand backed by "a", both wrapped
        // in the SimilarityInput implementation under test. Applying the algorithm to a
        // null-backed input must be rejected.
        assertThrows(IllegalArgumentException.class,
                () -> UNLIMITED_DISTANCE.apply(
                        SimilarityInputTest.build(inputType, null),
                        SimilarityInputTest.build(inputType, "a")));
    }
}
