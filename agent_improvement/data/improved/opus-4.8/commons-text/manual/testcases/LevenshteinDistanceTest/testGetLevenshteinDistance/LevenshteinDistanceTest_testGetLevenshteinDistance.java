package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link LevenshteinDistance} using the default (unbounded) instance.
 *
 * <p>The default instance has no threshold, so it always returns the exact
 * edit distance between the two inputs.</p>
 *
 * <p>Each {@code SimilarityInput} implementation contributed by
 * {@link SimilarityInputTest#similarityInputs()} is exercised against the same
 * set of (left, right) input pairs to confirm the distance is independent of
 * the concrete input type.</p>
 */
public class LevenshteinDistanceTest_testGetLevenshteinDistance {

    /** Default instance: no threshold, so distances are computed exactly. */
    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance(final Class<?> cls) {
        // Identical empty strings: no edits required.
        assertDistance(cls, 0, "", "");
        // Inserting a single character.
        assertDistance(cls, 1, "", "a");
        // Deleting every character of a 7-character string.
        assertDistance(cls, 7, "aaapppp", "");
        // Removing a single character ("frog" -> "fog").
        assertDistance(cls, 1, "frog", "fog");
        // Fully disjoint 3-character strings: every character must change.
        assertDistance(cls, 3, "fly", "ant");
        // Distance is symmetric, so "elephant"/"hippo" matches "hippo"/"elephant".
        assertDistance(cls, 7, "elephant", "hippo");
        assertDistance(cls, 7, "hippo", "elephant");
        // Replacing a 5-character string and extending it to 8 characters.
        assertDistance(cls, 8, "hippo", "zzzzzzzz");
        assertDistance(cls, 8, "zzzzzzzz", "hippo");
        // A single substitution ("hello" -> "hallo").
        assertDistance(cls, 1, "hello", "hallo");
    }

    /**
     * Asserts that the Levenshtein distance between {@code left} and {@code right},
     * each wrapped in the given {@code SimilarityInput} type, equals {@code expected}.
     *
     * @param cls      the concrete {@code SimilarityInput} implementation to use.
     * @param expected the expected edit distance.
     * @param left     the first input string.
     * @param right    the second input string.
     */
    private static void assertDistance(final Class<?> cls, final int expected, final String left, final String right) {
        assertEquals(expected,
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, left), SimilarityInputTest.build(cls, right)));
    }
}
