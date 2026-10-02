package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link LevenshteinDetailedDistance#apply(CharSequence, CharSequence)} when the distance is
 * bounded by a non-null {@code threshold} (the {@code (String, String, int)} scenario).
 *
 * <p>
 * Each case constructs {@code new LevenshteinDetailedDistance(threshold)}, applies it to a
 * {@code left}/{@code right} pair, and asserts the resulting distance together with its breakdown
 * into insert / delete / substitute counts. When the true edit distance exceeds the threshold the
 * algorithm gives up and reports a distance of {@code -1} with all operation counts at {@code 0}.
 * </p>
 */
public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringStringInt {

    /** Threshold meaning "effectively unbounded" for the inputs used below. */
    private static final int UNLIMITED = Integer.MAX_VALUE;

    /** Sentinel distance returned when the true distance exceeds the threshold. */
    private static final int OVER_THRESHOLD = -1;

    /**
     * One test case: a bounded comparison and the breakdown it is expected to produce.
     *
     * @param threshold  maximum distance the algorithm is allowed to compute.
     * @param left       first input string.
     * @param right      second input string.
     * @param distance   expected distance, or {@link #OVER_THRESHOLD} when the threshold is exceeded.
     * @param insert     expected number of insertions.
     * @param delete     expected number of deletions.
     * @param substitute expected number of substitutions.
     */
    static Arguments testCase(final int threshold, final String left, final String right,
            final int distance, final int insert, final int delete, final int substitute) {
        return Arguments.of(threshold, left, right, distance, insert, delete, substitute);
    }

    static Stream<Arguments> boundedDistanceCases() {
        return Stream.of(
                // Identical / empty inputs: distance 0, no operations.
                testCase(0, "", "", 0, 0, 0, 0),
                testCase(0, "aa", "aa", 0, 0, 0, 0),
                testCase(2, "aa", "aa", 0, 0, 0, 0),

                // Deleting every character of "aaapppp" down to "": within threshold.
                testCase(8, "aaapppp", "", 7, 0, 7, 0),
                testCase(7, "aaapppp", "", 7, 0, 7, 0),
                // Same comparison, but the threshold is one too small.
                testCase(6, "aaapppp", "", OVER_THRESHOLD, 0, 0, 0),

                // Single substitution required, but threshold 0 forbids any operation.
                testCase(0, "b", "a", OVER_THRESHOLD, 0, 0, 0),
                testCase(0, "a", "b", OVER_THRESHOLD, 0, 0, 0),

                // Three substitutions: needs threshold 3, fails at 2.
                testCase(2, "aaa", "bbb", OVER_THRESHOLD, 0, 0, 0),
                testCase(3, "aaa", "bbb", 3, 0, 0, 3),

                // Mixed delete + substitute breakdowns.
                testCase(10, "aaaaaa", "b", 6, 0, 5, 1),
                testCase(8, "aaapppp", "b", 7, 0, 6, 1),
                testCase(7, "aaapppp", "b", 7, 0, 6, 1),
                testCase(6, "aaapppp", "b", OVER_THRESHOLD, 0, 0, 0),

                // Mixed insert + substitute breakdowns ("a" -> "bbb").
                testCase(4, "a", "bbb", 3, 2, 0, 1),
                testCase(3, "a", "bbb", 3, 2, 0, 1),
                testCase(2, "a", "bbb", OVER_THRESHOLD, 0, 0, 0),
                testCase(1, "a", "bbb", OVER_THRESHOLD, 0, 0, 0),
                testCase(2, "bbb", "a", OVER_THRESHOLD, 0, 0, 0),
                testCase(1, "bbb", "a", OVER_THRESHOLD, 0, 0, 0),

                // Length difference alone exceeds a threshold of 1.
                testCase(1, "12345", "1234567", OVER_THRESHOLD, 0, 0, 0),
                testCase(1, "1234567", "12345", OVER_THRESHOLD, 0, 0, 0),

                // Word-like examples with assorted operation breakdowns.
                testCase(1, "frog", "fog", 1, 0, 1, 0),
                testCase(3, "fly", "ant", 3, 0, 0, 3),
                testCase(7, "elephant", "hippo", 7, 0, 3, 4),
                testCase(6, "elephant", "hippo", OVER_THRESHOLD, 0, 0, 0),
                testCase(7, "hippo", "elephant", 7, 3, 0, 4),
                // Intentionally repeated to mirror the original test's coverage.
                testCase(7, "hippo", "elephant", 7, 3, 0, 4),
                testCase(6, "hippo", "elephant", OVER_THRESHOLD, 0, 0, 0),
                testCase(8, "hippo", "zzzzzzzz", 8, 3, 0, 5),
                testCase(8, "zzzzzzzz", "hippo", 8, 0, 3, 5),
                testCase(1, "hello", "hallo", 1, 0, 0, 1),

                // Same word-like examples again, this time effectively unbounded.
                testCase(UNLIMITED, "frog", "fog", 1, 0, 1, 0),
                testCase(UNLIMITED, "fly", "ant", 3, 0, 0, 3),
                testCase(UNLIMITED, "elephant", "hippo", 7, 0, 3, 4),
                testCase(UNLIMITED, "hippo", "elephant", 7, 3, 0, 4),
                testCase(UNLIMITED, "hippo", "zzzzzzzz", 8, 3, 0, 5),
                testCase(UNLIMITED, "zzzzzzzz", "hippo", 8, 0, 3, 5),
                testCase(UNLIMITED, "hello", "hallo", 1, 0, 0, 1));
    }

    @ParameterizedTest(name = "[{index}] threshold={0}, \"{1}\" -> \"{2}\"")
    @MethodSource("boundedDistanceCases")
    void testGetLevenshteinDetailedDistance_StringStringInt(final int threshold, final String left,
            final String right, final int expectedDistance, final int expectedInsert,
            final int expectedDelete, final int expectedSubstitute) {
        final LevenshteinResults result = new LevenshteinDetailedDistance(threshold).apply(left, right);

        assertEquals(expectedDistance, result.getDistance(), "distance");
        assertEquals(expectedInsert, result.getInsertCount(), "insert count");
        assertEquals(expectedDelete, result.getDeleteCount(), "delete count");
        assertEquals(expectedSubstitute, result.getSubstituteCount(), "substitute count");
    }
}
