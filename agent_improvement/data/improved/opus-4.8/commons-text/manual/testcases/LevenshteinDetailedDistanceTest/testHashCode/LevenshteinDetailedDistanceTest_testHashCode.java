package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link LevenshteinResults#hashCode()} computed from an actual
 * {@link LevenshteinDetailedDistance#apply(CharSequence, CharSequence)} call
 * matches the hash code of a results object built from the expected counts.
 *
 * <p>Each {@link LevenshteinResults} bundles four values, in this order:
 * total distance, insert count, delete count and substitute count.</p>
 */
public class LevenshteinDetailedDistanceTest_testHashCode {

    /** Default (unlimited) distance instance shared by every assertion below. */
    private static final LevenshteinDetailedDistance DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    void hashCodeMatchesExpectedResultsForEachCase() {
        // Deleting all 7 characters of "aaapppp" to reach "": 7 deletes.
        assertHashCodeMatches("aaapppp", "", new LevenshteinResults(7, 0, 7, 0));

        // "frog" -> "fog": delete the single 'r'.
        assertHashCodeMatches("frog", "fog", new LevenshteinResults(1, 0, 1, 0));

        // "elephant" -> "hippo": 3 deletes and 4 substitutions, distance 7.
        assertHashCodeMatches("elephant", "hippo", new LevenshteinResults(7, 0, 3, 4));
    }

    /**
     * Asserts that applying the distance to {@code left} and {@code right}
     * yields a results object whose hash code equals that of {@code expected}.
     */
    private static void assertHashCodeMatches(final String left, final String right, final LevenshteinResults expected) {
        final LevenshteinResults actual = DISTANCE.apply(left, right);
        assertEquals(expected.hashCode(), actual.hashCode());
    }
}
