package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link WordUtils#abbreviate(String, int, int, String)} rejects an
 * argument combination where the {@code upper} bound is smaller than the {@code lower}
 * bound. Such a range is invalid because the upper bound must never fall below the
 * lower bound, so the method is expected to fail fast with an
 * {@link IllegalArgumentException}.
 */
public class WordUtilsTest_testAbbreviateUpperLessThanLowerValues {

    /** Text to abbreviate; its exact content is irrelevant because validation fails first. */
    private static final String INPUT = "0123456789";

    /** Lower bound for the abbreviation length. */
    private static final int LOWER_BOUND = 5;

    /** Upper bound, deliberately set below {@link #LOWER_BOUND} to trigger validation failure. */
    private static final int UPPER_BOUND = 2;

    /** Suffix appended after truncation; empty here since validation rejects the call first. */
    private static final String APPEND_TO_END = "";

    @Test
    void abbreviateRejectsUpperBoundBelowLowerBound() {
        assertThrows(IllegalArgumentException.class,
            () -> WordUtils.abbreviate(INPUT, LOWER_BOUND, UPPER_BOUND, APPEND_TO_END));
    }
}
