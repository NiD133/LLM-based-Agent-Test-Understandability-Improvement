package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testGetThreshold {

    @Test
    void testGetThreshold_returnsZeroWhenThresholdIsSetToZero() {
        final LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(0);
        assertEquals(0, distance.getThreshold());
    }

    @Test
    void testGetThreshold_returnsPositiveThresholdWhenSet() {
        final LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(5);
        assertEquals(5, distance.getThreshold());
    }

    @Test
    void testGetThreshold_returnsNullForDefaultUnlimitedInstance() {
        final LevenshteinDetailedDistance unlimitedDistance = LevenshteinDetailedDistance.getDefaultInstance();
        assertNull(unlimitedDistance.getThreshold());
    }
}
