package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link LevenshteinDetailedDistance#apply(CharSequence, CharSequence)}
 * rejects a {@code null} input.
 */
public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringNullInt {

    /** The default (unlimited-threshold) distance instance under test. */
    private static final LevenshteinDetailedDistance DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    void applyWithNullRightInputThrowsIllegalArgumentException() {
        // The right CharSequence is null, which is not allowed.
        assertThrows(IllegalArgumentException.class, () -> DISTANCE.apply("a", null));
    }
}
