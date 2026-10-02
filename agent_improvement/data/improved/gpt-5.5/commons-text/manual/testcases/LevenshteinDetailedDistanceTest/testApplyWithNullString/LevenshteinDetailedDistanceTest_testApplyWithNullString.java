package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testApplyWithNullString {

    @Test
    void testApplyWithNullString() {
        final LevenshteinDetailedDistance zeroThresholdDistance = new LevenshteinDetailedDistance(0);
        final String left = null;
        final String right = null;

        assertThrows(IllegalArgumentException.class, () -> zeroThresholdDistance.apply(left, right));
    }
}
