package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DamerauLevenshteinDistanceTest_testGetThresholdIsCorrect {

    @Test
    void testGetThresholdIsCorrect() {
        // The threshold passed to the constructor should be returned unchanged by getThreshold().
        final int threshold = 10;
        final DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(threshold);

        assertEquals(threshold, distance.getThreshold());
    }
}
