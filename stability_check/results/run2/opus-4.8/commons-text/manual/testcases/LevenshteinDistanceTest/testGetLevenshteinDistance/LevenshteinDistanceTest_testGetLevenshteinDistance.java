package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests the unlimited (no-threshold) Levenshtein distance calculation.
 *
 * <p>The test is parameterized over the different {@link SimilarityInput} input
 * types supplied by {@link SimilarityInputTest#similarityInputs()}, verifying that
 * the same expected distances are produced regardless of the underlying input type.</p>
 */
public class LevenshteinDistanceTest_testGetLevenshteinDistance {

    /** The distance instance without a threshold, so distances are computed exactly. */
    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance(final Class<?> inputType) {
        // Two empty inputs require no edits.
        assertDistanceEquals(0, inputType, "", "");

        // A single insertion or deletion costs one edit.
        assertDistanceEquals(1, inputType, "", "a");
        assertDistanceEquals(7, inputType, "aaapppp", "");

        // Deleting one character ("r") turns "frog" into "fog".
        assertDistanceEquals(1, inputType, "frog", "fog");

        // Every character differs, so all three must be substituted.
        assertDistanceEquals(3, inputType, "fly", "ant");

        // Distance is symmetric: swapping the arguments yields the same result.
        assertDistanceEquals(7, inputType, "elephant", "hippo");
        assertDistanceEquals(7, inputType, "hippo", "elephant");
        assertDistanceEquals(8, inputType, "hippo", "zzzzzzzz");
        assertDistanceEquals(8, inputType, "zzzzzzzz", "hippo");

        // Substituting a single character ("e" -> "a") turns "hello" into "hallo".
        assertDistanceEquals(1, inputType, "hello", "hallo");
    }

    /**
     * Asserts that the unlimited Levenshtein distance between {@code left} and
     * {@code right}, wrapped in the given {@link SimilarityInput} type, equals
     * the expected value.
     *
     * @param expectedDistance the expected edit distance.
     * @param inputType        the {@link SimilarityInput} implementation to wrap the strings in.
     * @param left             the first string.
     * @param right            the second string.
     */
    private static void assertDistanceEquals(final int expectedDistance, final Class<?> inputType,
            final String left, final String right) {
        assertEquals(expectedDistance,
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(inputType, left),
                        SimilarityInputTest.build(inputType, right)));
    }
}
