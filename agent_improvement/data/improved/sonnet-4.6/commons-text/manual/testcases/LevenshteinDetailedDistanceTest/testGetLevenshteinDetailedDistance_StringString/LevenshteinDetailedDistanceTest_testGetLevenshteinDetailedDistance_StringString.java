package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringString {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    /** Builds a SimilarityInput from a raw string for the given input type class. */
    @SuppressWarnings("unchecked")
    private <E> SimilarityInput<E> input(final Class<?> cls, final String s) {
        return (SimilarityInput<E>) SimilarityInputTest.build(cls, s);
    }

    /**
     * Asserts all four Levenshtein result counters in one call.
     *
     * @param result        the result to verify
     * @param distance      expected total edit distance
     * @param insertCount   expected number of insertions
     * @param deleteCount   expected number of deletions
     * @param substituteCount expected number of substitutions
     */
    private void assertResult(final LevenshteinResults result,
                              final int distance, final int insertCount,
                              final int deleteCount, final int substituteCount) {
        assertEquals(distance,      result.getDistance());
        assertEquals(insertCount,   result.getInsertCount());
        assertEquals(deleteCount,   result.getDeleteCount());
        assertEquals(substituteCount, result.getSubstituteCount());
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDetailedDistance_StringString(final Class<?> cls) {

        // Two empty strings: no edits required
        assertResult(
            UNLIMITED_DISTANCE.apply(input(cls, ""), input(cls, "")),
            0, 0, 0, 0);

        // Empty left, one-char right: one insertion needed
        assertResult(
            UNLIMITED_DISTANCE.apply(input(cls, ""), input(cls, "a")),
            1, 1, 0, 0);

        // Seven-char left, empty right: seven deletions needed
        assertResult(
            UNLIMITED_DISTANCE.apply(input(cls, "aaapppp"), input(cls, "")),
            7, 0, 7, 0);

        // "frog" -> "fog": delete one character ('r')
        assertResult(
            UNLIMITED_DISTANCE.apply(input(cls, "frog"), input(cls, "fog")),
            1, 0, 1, 0);

        // "fly" -> "ant": all three characters substituted
        assertResult(
            UNLIMITED_DISTANCE.apply(input(cls, "fly"), input(cls, "ant")),
            3, 0, 0, 3);

        // "elephant" (8 chars) -> "hippo" (5 chars): 4 substitutions + 3 deletions
        assertResult(
            UNLIMITED_DISTANCE.apply(input(cls, "elephant"), input(cls, "hippo")),
            7, 0, 3, 4);

        // "hippo" (5 chars) -> "elephant" (8 chars): 4 substitutions + 3 insertions
        assertResult(
            UNLIMITED_DISTANCE.apply(input(cls, "hippo"), input(cls, "elephant")),
            7, 3, 0, 4);

        // "hippo" (5 chars) -> "zzzzzzzz" (8 chars): 5 substitutions + 3 insertions
        assertResult(
            UNLIMITED_DISTANCE.apply(input(cls, "hippo"), input(cls, "zzzzzzzz")),
            8, 3, 0, 5);

        // "zzzzzzzz" (8 chars) -> "hippo" (5 chars): 5 substitutions + 3 deletions
        assertResult(
            UNLIMITED_DISTANCE.apply(input(cls, "zzzzzzzz"), input(cls, "hippo")),
            8, 0, 3, 5);

        // "hello" -> "hallo": one substitution ('e' -> 'a')
        assertResult(
            UNLIMITED_DISTANCE.apply(input(cls, "hello"), input(cls, "hallo")),
            1, 0, 0, 1);
    }
}
