package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link LevenshteinDetailedDistance#getThreshold()}.
 */
public class LevenshteinDetailedDistanceTest_testGetThreshold {

    @Test
    void getThresholdReturnsTheValuePassedToTheConstructor() {
        // Given a distance configured with a threshold of 0...
        final int configuredThreshold = 0;
        final LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(configuredThreshold);

        // ...getThreshold() reports back that same threshold.
        assertEquals(configuredThreshold, distance.getThreshold());
    }
}
