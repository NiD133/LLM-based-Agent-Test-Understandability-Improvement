package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LevenshteinDetailedDistance#apply(CharSequence, CharSequence)}
 * rejects a {@code null} left operand.
 */
public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_NullStringInt {

    /** The default (unlimited, no-threshold) distance instance under test. */
    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE =
            LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    void applyThrowsWhenLeftOperandIsNull() {
        // The left CharSequence is null while the right one ("a") is valid;
        // apply must reject the null input rather than compute a distance.
        assertThrows(IllegalArgumentException.class,
                () -> UNLIMITED_DISTANCE.apply(null, "a"));
    }
}
