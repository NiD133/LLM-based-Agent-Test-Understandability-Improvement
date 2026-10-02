package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testApplyWithNullString {

    /**
     * {@code apply} must reject null inputs by throwing {@link IllegalArgumentException},
     * regardless of the configured threshold (here the threshold is 0).
     */
    @Test
    void testApplyWithNullString() {
        final LevenshteinDetailedDistance distanceWithZeroThreshold = new LevenshteinDetailedDistance(0);

        assertThrows(IllegalArgumentException.class,
                () -> distanceWithZeroThreshold.apply((String) null, (String) null));
    }
}
