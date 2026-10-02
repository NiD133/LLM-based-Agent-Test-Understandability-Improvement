package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LevenshteinDetailedDistance#apply(CharSequence, CharSequence)}
 * rejects a {@code null} argument.
 */
public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_NullString {

    /** The default (unlimited-threshold) instance under test. */
    private static final LevenshteinDetailedDistance DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    void applyThrowsWhenRightArgumentIsNull() {
        // The "right" CharSequence is null, so apply must reject the input.
        assertThrows(IllegalArgumentException.class, () -> DISTANCE.apply("a", null));
    }
}
