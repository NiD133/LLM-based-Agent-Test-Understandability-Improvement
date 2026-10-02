package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DamerauLevenshteinDistanceTest_testGetThresholdIsCorrect {

    private static final int CONFIGURED_THRESHOLD = 10;

    @Test
    void testGetThresholdIsCorrect() {
        final DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(CONFIGURED_THRESHOLD);

        assertEquals(CONFIGURED_THRESHOLD, distance.getThreshold());
    }
}
