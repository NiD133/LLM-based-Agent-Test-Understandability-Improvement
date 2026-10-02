package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testApplyThrowsIllegalArgumentExceptionAndCreatesLevenshteinDetailedDistanceTakingInteger {

    /**
     * Verifies that {@code apply} rejects a {@code null} input.
     *
     * <p>The distance is configured with a threshold of {@code 0}, which routes
     * the call through the threshold-limited comparison. Regardless of the
     * threshold, passing a {@code null} sequence (here the {@code right} argument)
     * must raise an {@link IllegalArgumentException} because both inputs are
     * required to be non-null.</p>
     */
    @Test
    void applyWithNullRightInputThrowsIllegalArgumentException() {
        final LevenshteinDetailedDistance distanceWithZeroThreshold = new LevenshteinDetailedDistance(0);
        final CharSequence emptyLeftInput = new TextStringBuilder();
        final CharSequence nullRightInput = null;

        assertThrows(IllegalArgumentException.class,
                () -> distanceWithZeroThreshold.apply(emptyLeftInput, nullRightInput));
    }
}
