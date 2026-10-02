package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies the unlimited (no-threshold) Levenshtein distance computed by
 * {@link LevenshteinDistance#apply(SimilarityInput, SimilarityInput)}.
 *
 * <p>The same set of expected distances is exercised against every
 * {@link SimilarityInput} implementation supplied by
 * {@code SimilarityInputTest#similarityInputs()}, ensuring the algorithm is
 * independent of the concrete input type.</p>
 */
public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringString {

    /** The default instance computes distances without any threshold limit. */
    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringString(final Class<?> inputType) {
        // Two empty strings are identical: no edits required.
        assertDistance(inputType, 0, "", "");
        // A single insertion turns "" into "a".
        assertDistance(inputType, 1, "", "a");
        // Deleting every character of a 7-character string yields "".
        assertDistance(inputType, 7, "aaapppp", "");
        // Deleting the 'r' turns "frog" into "fog".
        assertDistance(inputType, 1, "frog", "fog");
        // No characters are shared, so all 3 must be substituted.
        assertDistance(inputType, 3, "fly", "ant");
        // Distance is symmetric: "elephant" <-> "hippo" costs 7 either way.
        assertDistance(inputType, 7, "elephant", "hippo");
        assertDistance(inputType, 7, "hippo", "elephant");
        // Completely disjoint strings of length 5 and 8 cost 8.
        assertDistance(inputType, 8, "hippo", "zzzzzzzz");
        assertDistance(inputType, 8, "zzzzzzzz", "hippo");
        // A single substitution ('e' -> 'a') turns "hello" into "hallo".
        assertDistance(inputType, 1, "hello", "hallo");
    }

    /**
     * Asserts that the unlimited Levenshtein distance between {@code left} and
     * {@code right}, both wrapped in the given {@link SimilarityInput} type,
     * equals {@code expected}.
     *
     * @param inputType the {@link SimilarityInput} implementation to wrap the operands in.
     * @param expected  the expected edit distance.
     * @param left      the first operand.
     * @param right     the second operand.
     */
    private static void assertDistance(final Class<?> inputType, final int expected,
            final String left, final String right) {
        assertEquals(expected, UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(inputType, left),
                SimilarityInputTest.build(inputType, right)));
    }
}
