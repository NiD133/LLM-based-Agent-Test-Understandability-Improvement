package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the {@link LevenshteinResults} produced by the default (unlimited)
 * {@link LevenshteinDetailedDistance} renders the expected {@code toString()} output.
 *
 * <p>The default instance counts the insert, delete and substitute operations needed
 * to turn the first argument into the second. Each case below pairs an input with the
 * detailed result it should yield, expressed via the
 * {@code LevenshteinResults(distance, insertCount, deleteCount, substituteCount)} constructor.</p>
 */
public class LevenshteinDetailedDistanceTest_testToString {

    private final LevenshteinDetailedDistance unlimitedDistance = LevenshteinDetailedDistance.getDefaultInstance();

    /**
     * Asserts that applying the distance to {@code left} and {@code right} yields a result
     * whose {@code toString()} matches that of the {@code expected} result.
     */
    private void assertResultToString(final String left, final String right, final LevenshteinResults expected) {
        final LevenshteinResults actual = unlimitedDistance.apply(left, right);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testToString() {
        // "fly" -> "ant": all three characters are substituted.
        assertResultToString("fly", "ant", new LevenshteinResults(3, 0, 0, 3));

        // "hippo" -> "elephant": 3 inserts and 4 substitutes (distance 7).
        assertResultToString("hippo", "elephant", new LevenshteinResults(7, 3, 0, 4));

        // "" -> "a": a single insert.
        assertResultToString("", "a", new LevenshteinResults(1, 1, 0, 0));
    }
}
