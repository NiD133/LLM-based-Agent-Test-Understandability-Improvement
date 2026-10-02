package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DamerauLevenshteinDistanceTest_testGetThresholdIsCorrect {

    @Test
    void testGetThresholdIsCorrect() {
        final int expectedThreshold = 10;
        final DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(expectedThreshold);
        assertEquals(expectedThreshold, distance.getThreshold());
    }
}
