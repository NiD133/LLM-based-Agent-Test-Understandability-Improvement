package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests the threshold-limited Levenshtein distance, i.e. {@code new LevenshteinDistance(threshold).apply(left, right)}.
 *
 * <p>When the actual edit distance is within the threshold the distance is returned; otherwise the
 * computation returns {@code -1} to signal "further apart than the threshold allows".</p>
 *
 * <p>The test is parameterized over every {@link SimilarityInput} representation supported by
 * {@link SimilarityInputTest}, so each case is exercised through every input type.</p>
 */
public class LevenshteinDistanceTest_testGetLevenshteinDistance_StringStringInt {

    /** Sentinel returned by {@code apply} when the distance exceeds the configured threshold. */
    private static final int OVER_THRESHOLD = -1;

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringStringInt(final Class<?> inputType) {

        // --- empty strings: distance equals the length of the non-empty string ---
        assertDistance(inputType, 0, 0, "", "");
        assertDistance(inputType, 7, 8, "aaapppp", "");          // distance 7, below threshold
        assertDistance(inputType, 7, 7, "aaapppp", "");          // distance 7, equal to threshold
        assertDistance(inputType, OVER_THRESHOLD, 6, "aaapppp", ""); // distance 7, above threshold

        // --- unequal single characters with a zero threshold ---
        assertDistance(inputType, OVER_THRESHOLD, 0, "b", "a");
        assertDistance(inputType, OVER_THRESHOLD, 0, "a", "b");

        // --- equal strings: distance is always 0 ---
        assertDistance(inputType, 0, 0, "aa", "aa");
        assertDistance(inputType, 0, 2, "aa", "aa");

        // --- same length, all characters differ ---
        assertDistance(inputType, OVER_THRESHOLD, 2, "aaa", "bbb"); // distance 3, above threshold
        assertDistance(inputType, 3, 3, "aaa", "bbb");              // distance 3, equal to threshold

        // --- large length difference relative to the threshold ("big stripe") ---
        assertDistance(inputType, 6, 10, "aaaaaa", "b");

        // --- distance strictly less than the threshold ---
        assertDistance(inputType, 7, 8, "aaapppp", "b");
        assertDistance(inputType, 3, 4, "a", "bbb");

        // --- distance exactly equal to the threshold ---
        assertDistance(inputType, 7, 7, "aaapppp", "b");
        assertDistance(inputType, 3, 3, "a", "bbb");

        // --- distance greater than the threshold ---
        assertDistance(inputType, OVER_THRESHOLD, 2, "a", "bbb");
        assertDistance(inputType, OVER_THRESHOLD, 2, "bbb", "a");
        assertDistance(inputType, OVER_THRESHOLD, 6, "aaapppp", "b");

        // --- stripe runs off the matrix, strings not similar ---
        assertDistance(inputType, OVER_THRESHOLD, 1, "a", "bbb");
        assertDistance(inputType, OVER_THRESHOLD, 1, "bbb", "a");

        // --- stripe runs off the matrix, strings are similar ---
        assertDistance(inputType, OVER_THRESHOLD, 1, "12345", "1234567");
        assertDistance(inputType, OVER_THRESHOLD, 1, "1234567", "12345");

        // --- classic getLevenshteinDistance cases with a finite threshold ---
        assertDistance(inputType, 1, 1, "frog", "fog");
        assertDistance(inputType, 3, 3, "fly", "ant");
        assertDistance(inputType, 7, 7, "elephant", "hippo");
        assertDistance(inputType, OVER_THRESHOLD, 6, "elephant", "hippo");
        assertDistance(inputType, 7, 7, "hippo", "elephant");
        assertDistance(inputType, OVER_THRESHOLD, 6, "hippo", "elephant");
        assertDistance(inputType, 8, 8, "hippo", "zzzzzzzz");
        assertDistance(inputType, 8, 8, "zzzzzzzz", "hippo");
        assertDistance(inputType, 1, 1, "hello", "hallo");

        // --- same classic cases with an effectively unlimited threshold ---
        assertDistance(inputType, 1, Integer.MAX_VALUE, "frog", "fog");
        assertDistance(inputType, 3, Integer.MAX_VALUE, "fly", "ant");
        assertDistance(inputType, 7, Integer.MAX_VALUE, "elephant", "hippo");
        assertDistance(inputType, 7, Integer.MAX_VALUE, "hippo", "elephant");
        assertDistance(inputType, 8, Integer.MAX_VALUE, "hippo", "zzzzzzzz");
        assertDistance(inputType, 8, Integer.MAX_VALUE, "zzzzzzzz", "hippo");
        assertDistance(inputType, 1, Integer.MAX_VALUE, "hello", "hallo");

        // --- transposition still counts as two edits, which exceeds a threshold of 1 ---
        assertDistance(inputType, OVER_THRESHOLD, 1, "abc", "acb");
    }

    /**
     * Asserts that the threshold-limited Levenshtein distance between {@code left} and {@code right}
     * equals {@code expectedDistance}, building both inputs as the given {@code inputType}.
     *
     * @param inputType        the {@link SimilarityInput} representation to build the inputs as.
     * @param expectedDistance the expected distance, or {@link #OVER_THRESHOLD} when the threshold is exceeded.
     * @param threshold        the maximum distance the algorithm will compute.
     * @param left             the first string.
     * @param right            the second string.
     */
    private static void assertDistance(final Class<?> inputType, final int expectedDistance,
            final int threshold, final String left, final String right) {
        assertEquals(expectedDistance, new LevenshteinDistance(threshold).apply(
                SimilarityInputTest.build(inputType, left),
                SimilarityInputTest.build(inputType, right)));
    }
}
