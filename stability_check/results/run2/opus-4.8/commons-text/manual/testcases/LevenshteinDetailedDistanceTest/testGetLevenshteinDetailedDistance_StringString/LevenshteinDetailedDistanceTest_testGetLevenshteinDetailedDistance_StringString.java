package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies the unlimited (no threshold) Levenshtein detailed distance.
 *
 * <p>Each case checks not only the overall edit distance but also the exact
 * breakdown into insert, delete and substitute operations required to turn the
 * {@code left} input into the {@code right} input. The test is run for every
 * {@link SimilarityInput} implementation supplied by
 * {@link SimilarityInputTest#similarityInputs()} to confirm the algorithm
 * behaves identically regardless of the input type.</p>
 */
public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringString {

    /** The default instance performs an unlimited (no threshold) comparison. */
    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDetailedDistance_StringString(final Class<?> cls) {
        // Two empty inputs are already identical: no edits at all.
        assertEditCounts(cls, "", "", 0, 0, 0, 0);
        // Growing "" into "a" needs a single insertion.
        assertEditCounts(cls, "", "a", 1, 1, 0, 0);
        // Reducing "aaapppp" to "" deletes every character.
        assertEditCounts(cls, "aaapppp", "", 7, 0, 7, 0);
        // "frog" -> "fog" drops the extra 'r' via one deletion.
        assertEditCounts(cls, "frog", "fog", 1, 0, 1, 0);
        // "fly" -> "ant" replaces all three characters.
        assertEditCounts(cls, "fly", "ant", 3, 0, 0, 3);
        // "elephant" -> "hippo": three deletions plus four substitutions.
        assertEditCounts(cls, "elephant", "hippo", 7, 0, 3, 4);
        // The reverse direction swaps deletions for insertions.
        assertEditCounts(cls, "hippo", "elephant", 7, 3, 0, 4);
        // "hippo" -> "zzzzzzzz": three insertions plus five substitutions.
        assertEditCounts(cls, "hippo", "zzzzzzzz", 8, 3, 0, 5);
        // The reverse direction swaps insertions for deletions.
        assertEditCounts(cls, "zzzzzzzz", "hippo", 8, 0, 3, 5);
        // "hello" -> "hallo" only substitutes the single differing vowel.
        assertEditCounts(cls, "hello", "hallo", 1, 0, 0, 1);
    }

    /**
     * Applies the unlimited distance to the two inputs (built as instances of {@code cls})
     * and asserts the resulting total distance and its insert/delete/substitute breakdown.
     */
    private static void assertEditCounts(final Class<?> cls, final String left, final String right,
            final int expectedDistance, final int expectedInserts, final int expectedDeletes, final int expectedSubstitutes) {
        final LevenshteinResults result =
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, left), SimilarityInputTest.build(cls, right));
        assertEquals(expectedDistance, result.getDistance(), "distance");
        assertEquals(expectedInserts, result.getInsertCount(), "insert count");
        assertEquals(expectedDeletes, result.getDeleteCount(), "delete count");
        assertEquals(expectedSubstitutes, result.getSubstituteCount(), "substitute count");
    }
}
