package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LevenshteinDetailedDistance#apply(CharSequence, CharSequence)}
 * rejects a {@code null} input.
 */
public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringNull {

    /** Default (unlimited) distance instance used by the test below. */
    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE =
            LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    void applyWithNullLeftInputThrowsIllegalArgumentException() {
        // The "left" argument is null while the "right" argument is a valid string.
        assertThrows(IllegalArgumentException.class,
                () -> UNLIMITED_DISTANCE.apply(null, "a"));
    }
}
