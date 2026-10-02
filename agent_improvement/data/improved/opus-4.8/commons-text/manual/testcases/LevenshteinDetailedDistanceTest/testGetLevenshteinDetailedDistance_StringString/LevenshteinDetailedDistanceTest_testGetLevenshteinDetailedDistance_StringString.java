package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies the unlimited (no-threshold) detailed Levenshtein distance, which reports not just the
 * total edit distance but also how it breaks down into insert, delete and substitute operations.
 *
 * <p>The test is parameterized over the different {@link SimilarityInput} backing types supplied by
 * {@link SimilarityInputTest#similarityInputs()}, so each scenario is exercised against every
 * supported input representation.</p>
 */
public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringString {

    /** Instance without a threshold, so distances are always computed in full. */
    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDetailedDistance_StringString(final Class<?> cls) {
        // Two empty strings: no edits at all.
        assertDetailedDistance(cls, "", "", 0, 0, 0, 0);

        // Empty -> single character: one insert.
        assertDetailedDistance(cls, "", "a", 1, 1, 0, 0);

        // Non-empty -> empty: every character is deleted.
        assertDetailedDistance(cls, "aaapppp", "", 7, 0, 7, 0);

        // "frog" -> "fog": the leading 'r' is deleted.
        assertDetailedDistance(cls, "frog", "fog", 1, 0, 1, 0);

        // Same length, no shared characters: every character is substituted.
        assertDetailedDistance(cls, "fly", "ant", 3, 0, 0, 3);

        // Longer -> shorter: a mix of deletes and substitutes.
        assertDetailedDistance(cls, "elephant", "hippo", 7, 0, 3, 4);

        // Reverse direction: deletes become inserts.
        assertDetailedDistance(cls, "hippo", "elephant", 7, 3, 0, 4);

        // "hippo" -> "zzzzzzzz": substitute the overlap, insert the rest.
        assertDetailedDistance(cls, "hippo", "zzzzzzzz", 8, 3, 0, 5);

        // Reverse direction: inserts become deletes.
        assertDetailedDistance(cls, "zzzzzzzz", "hippo", 8, 0, 3, 5);

        // "hello" -> "hallo": a single substitution ('e' -> 'a').
        assertDetailedDistance(cls, "hello", "hallo", 1, 0, 0, 1);
    }

    /**
     * Applies the unlimited detailed distance to {@code left} and {@code right} (built as instances of
     * {@code cls}) and asserts the total distance together with the individual operation counts.
     *
     * @param cls                the {@link SimilarityInput} backing type to build the inputs as
     * @param left               the source character sequence
     * @param right              the target character sequence
     * @param expectedDistance   the expected total edit distance
     * @param expectedInserts    the expected number of insert operations
     * @param expectedDeletes    the expected number of delete operations
     * @param expectedSubstitutes the expected number of substitute operations
     */
    private static void assertDetailedDistance(final Class<?> cls, final String left, final String right,
            final int expectedDistance, final int expectedInserts, final int expectedDeletes, final int expectedSubstitutes) {
        final LevenshteinResults result = UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, left), SimilarityInputTest.build(cls, right));
        assertEquals(expectedDistance, result.getDistance(), "total distance");
        assertEquals(expectedInserts, result.getInsertCount(), "insert count");
        assertEquals(expectedDeletes, result.getDeleteCount(), "delete count");
        assertEquals(expectedSubstitutes, result.getSubstituteCount(), "substitute count");
    }
}
